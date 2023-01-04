package org.krulvis.rs.models

import jakarta.persistence.*

@Entity
@Table(name = "proggies")
class ProgressReport(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    val runtime: Long,

    @ManyToOne(fetch = FetchType.EAGER)
    val script: Script,

    @ManyToOne(fetch = FetchType.EAGER)
    val user: User,
    val data: String
) {
    constructor() : this("", -1, Script(), User(), "")
}