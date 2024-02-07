package com.louie.studybuddy.models.tasks

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDate

class Task(): Serializable {
    @JsonProperty("id")
    private var id: Long = 0

    @JsonProperty("title")
    private lateinit var title: String

    @JsonProperty("description")
    private lateinit var description: String

    @JsonProperty("date_created")
    private lateinit var dateCreated: LocalDate

    constructor(
        id: Long,
        title: String,
        description: String,
        dateCreated: LocalDate
    ) : this() {
        this.id = id
        this.title = title
        this.description = description
        this.dateCreated = dateCreated
    }

    fun setId(id: Long): Task {
        this.id = id
        return this
    }

    fun setTitle(title: String): Task {
        this.title = title
        return this
    }

    fun setDescription(description: String): Task {
        this.description = description
        return this
    }

    fun setDateCreated(dateCreated: LocalDate): Task {
        this.dateCreated = dateCreated
        return this
    }

    fun getId(): Long {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
    }

    fun getDateCreated(): LocalDate {
        return dateCreated
    }

    override fun toString(): String {
        return this.title
    }
}