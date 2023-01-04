package org.krulvis.rs.repositories

import org.krulvis.rs.models.ProgressReport
import org.krulvis.rs.models.User
import org.springframework.data.repository.CrudRepository

interface ProggyRepository : CrudRepository<ProgressReport, String> {
    
    fun findByUser(user: User): List<ProgressReport>
}