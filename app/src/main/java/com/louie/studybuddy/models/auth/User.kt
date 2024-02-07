package com.louie.studybuddy.models.auth

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDate

class User(): Serializable {
    @JsonProperty("id")
    private var id: Long = 0

    @JsonProperty("username")
    private lateinit var username: String

    @JsonProperty("first_name")
    private lateinit var firstName: String

    @JsonProperty("last_name")
    private lateinit var lastName: String

    @JsonProperty("email")
    private lateinit var email: String

    @JsonProperty("password")
    private lateinit var password: String

    @JsonProperty("last_login")
    private lateinit var lastLogin: LocalDate

    constructor(
        id: Long,
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        lastLogin: LocalDate
    ) : this() {
        this.id = id
        this.username = username
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.password = password
        this.lastLogin = lastLogin
    }

    fun id(id: Long): User {
        this.id = id
        return this
    }

    fun username(username: String): User {
        this.username = username
        return this
    }

    fun firstName(firstName: String): User {
        this.firstName = firstName
        return this
    }

    fun lastName(lastName: String): User {
        this.lastName = lastName
        return this
    }

    fun email(email: String): User {
        this.email = email
        return this
    }

    fun password(password: String): User {
        this.password = password
        return this
    }

    fun setLastLogin(lastLogin: LocalDate): User {
        this.lastLogin = lastLogin
        return this
    }

    fun getId(): Long {
        return id
    }

    fun getUsername(): String {
        return username
    }

    fun getFirstName(): String {
        return firstName
    }

    fun getLastName(): String {
        return lastName
    }

    fun getEmail(): String {
        return email
    }

    fun getPassword(): String {
        return password
    }

    fun getLastLogin(): LocalDate {
        return this.lastLogin
    }

    override fun toString(): String {
        return this.email
    }
}