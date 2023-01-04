package org.krulvis.rs.repositories

import org.krulvis.rs.models.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, String> {

    fun findByName(name: String): Optional<User>
}