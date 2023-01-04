package org.krulvis.rs.repositories

import org.krulvis.rs.models.Script
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface ScriptRepository : CrudRepository<Script, String> {

    fun findByName(name: String): Optional<Script>
}