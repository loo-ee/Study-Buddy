package com.louie.studybuddy.models.tasks

import com.fasterxml.jackson.annotation.JsonProperty
import com.louie.studybuddy.models.auth.User
import java.io.Serializable
import java.time.LocalDate

class UserTask(): Serializable {
    @JsonProperty("id")
    private var id: Long = 0

    @JsonProperty("task")
    private lateinit var task: Task

    @JsonProperty("owner")
    private lateinit var owner: User

    @JsonProperty("buddy")
    private lateinit var buddy: User

    @JsonProperty("is_done")
    private var isDone: Boolean = false

    @JsonProperty("date_finished")
    private var dateFinished: LocalDate? = null

    constructor(
        id: Long,
        task: Task,
        owner: User,
        buddy: User,
        isDone: Boolean,
        dateFinished: LocalDate?
    ): this() {
        this.id = id
        this.task = task
        this.owner = owner
        this.buddy = buddy
        this.isDone = isDone
        this.dateFinished = dateFinished
    }

    fun setId(id: Long): UserTask {
        this.id = id
        return this
    }

    fun setTask(task: Task): UserTask {
        this.task = task
        return this
    }

    fun setOwner(owner: User): UserTask {
        this.owner = owner
        return this
    }

    fun setBuddy(buddy: User): UserTask {
        this.buddy = buddy
        return this
    }

    fun setIsDone(isDone: Boolean): UserTask {
        this.isDone = isDone
        return this
    }

    fun setDateFinished(dateFinished: LocalDate?): UserTask {
        this.dateFinished = dateFinished
        return this
    }

    fun getId(): Long {
        return id
    }

    fun getTask(): Task {
        return task
    }

    fun getOwner(): User {
        return owner
    }

    fun getBuddy(): User {
        return buddy
    }

    fun isDone(): Boolean {
        return isDone
    }

    fun getDateFinished(): LocalDate? {
        return dateFinished
    }

    override fun toString(): String {
        return this.task.toString() + " of " + this.owner
    }
}