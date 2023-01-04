package org.krulvis.rs.models

import jakarta.persistence.*


@Entity
@Table(name = "scripts")
class Script(
    @Id
    val id: String,
    var name: String,

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "script"
    )
    val proggies: MutableSet<ProgressReport>,

    @ManyToMany(
        fetch = FetchType.EAGER,
    )
    val users: MutableSet<User>
) {
    constructor() : this("", "", mutableSetOf(), mutableSetOf())
}