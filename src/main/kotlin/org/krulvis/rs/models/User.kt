package org.krulvis.rs.models

import jakarta.persistence.*


@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    var name: String,

    @ManyToMany(
        fetch = FetchType.EAGER,
        mappedBy = "users"
    )
    var scripts: MutableSet<Script>,

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "user"
    )
    var progReports: MutableSet<ProgressReport>
) {
    constructor() : this("", "", mutableSetOf(), mutableSetOf())
}