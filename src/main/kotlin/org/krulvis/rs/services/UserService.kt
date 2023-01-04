package org.krulvis.rs.services

import org.krulvis.rs.models.User
import org.krulvis.rs.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(
    val repository: UserRepository,
    @Autowired
    val scriptService: ScriptService
) {

    val logger = Logger.getLogger(javaClass.simpleName)

    fun findOrCreate(name: String): User {
        val repoUser = repository.findByName(name)
        if (repoUser.isPresent) {
            return repoUser.get()
        }
        val newUser = User()
        newUser.name = name
        logger.info("Could not find script for name=$name, creating new entry: $newUser")
        return repository.save(newUser)
    }

    fun removeUser(name: String) {
        repository.findByName(name).ifPresent { user ->
            user.scripts.forEach { script ->
                scriptService.removeUserFromScript(script, user)
            }
            repository.delete(user)
        }
    }
}