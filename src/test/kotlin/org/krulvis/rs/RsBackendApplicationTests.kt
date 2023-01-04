package org.krulvis.rs

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.krulvis.rs.controllers.ScriptController
import org.krulvis.rs.services.ScriptService
import org.krulvis.rs.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.random.Random

fun randomName(length: Int): String {
    val charPool = ('a'..'z') + ('A'..'Z')
    val random = Random(42)
    return (1..length)
        .map {
            charPool[random.nextInt(0, charPool.size)]
        }
        .joinToString("")
}

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RsBackendApplicationTests(
    @Autowired val scriptService: ScriptService,
    @Autowired val userService: UserService,
    @Autowired val scriptController: ScriptController
) {

    val scriptUUID = UUID.randomUUID().toString()
    val scriptName = "TestScript"
    val username = randomName(25)


    @Test
    fun contextLoads() {
        assertThat(scriptController).isNotNull
        assertThat(scriptService).isNotNull
        assertThat(userService).isNotNull
    }

    @Test
    fun testCreateScript() {
        val script = scriptService.findOrCreate(scriptUUID)
        script.name = scriptName
        scriptService.repository.save(script)
        assert(script.name == scriptName)
    }

    @Test
    fun testCreateUser() {
        println("Creating user with username=$username")
        val user = userService.findOrCreate(username)
        assert(user.name == username)
    }

    @Test
    fun testAddScriptAccess() {
        val user = userService.findOrCreate(username)
        var script = scriptService.findOrCreate(scriptUUID)
        script.name = scriptName
        scriptService.repository.save(script)
        script = scriptService.addUserToScript(script, user)
        assert(scriptService.hasAccess(script.id, user.name))
    }

    @Test
    fun testScriptController() {
        val user = userService.findOrCreate(username)
        val script = scriptService.findOrCreate(scriptUUID)
        scriptService.addUserToScript(script, user)

        assert(scriptController.access(scriptUUID, username))
    }

    @AfterAll
    fun teardown() {
        scriptService.removeScript(scriptUUID)
        userService.removeUser(username)
    }

}
