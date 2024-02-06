package com.louie.studybuddy.storage

import com.louie.studybuddy.models.User

object Session {
    private var loggedInUser: User? = null

    fun setLoggedInUser(user: User?) {
        this.loggedInUser = user
    }

    fun getLoggedInUser(): User? {
        return this.loggedInUser
    }
}