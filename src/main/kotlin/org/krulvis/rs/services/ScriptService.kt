package org.krulvis.rs.services

import org.krulvis.rs.models.Script
import org.krulvis.rs.models.User
import org.krulvis.rs.repositories.ScriptRepository
import org.springframework.stereotype.Service
import java.util.logging.Logger
import kotlin.jvm.optionals.getOrNull

@Service
class ScriptService(val repository: ScriptRepository) {

    val logger = Logger.getLogger(javaClass.simpleName)

    fun findOrCreate(id: String): Script {
        val repoScript = repository.findById(id)
        if (repoScript.isPresent) {
            return repoScript.get()
        }
        val newScript = Script(id, "", mutableSetOf(), mutableSetOf())
        logger.info("Could not find script for id=$id, name=${newScript.name}, creating new entry: $newScript")
        return repository.save(newScript)
    }

    fun addUserToScript(script: Script, user: User): Script {
        script.users.add(user)
        return repository.save(script)
    }

    fun removeUserFromScript(script: Script, user: User): Script {
        script.users.remove(user)
        return repository.save(script)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hasAccess(scriptId: String, username: String): Boolean {
        val script = repository.findById(scriptId).getOrNull() ?: return false
        return script.users.any { user -> user.name == username }
    }


    fun removeScript(id: String) {
        repository.findById(id).ifPresent {
            it.users.clear()
            repository.save(it)
            repository.delete(it)
        }
    }
}