package org.krulvis.rs.controllers

import org.krulvis.rs.services.ScriptService
import org.krulvis.rs.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/scripts")
class ScriptController(
    val scriptService: ScriptService,
    val userService: UserService
) {

    @GetMapping("/")
    fun index() = "Hello To Script Service!"

    @GetMapping("/{script}/access/{user}")
    fun access(
        @PathVariable("script") scriptId: String,
        @PathVariable("user") username: String,
    ): Boolean {
        val script = scriptService.findOrCreate(scriptId)
        val user = userService.findOrCreate(username)
        println("Checking if username=$username has access to scriptId=${scriptId} with users: [${script.users.joinToString()}]")
        return script.users.any { it.id == user.id }
    }

}