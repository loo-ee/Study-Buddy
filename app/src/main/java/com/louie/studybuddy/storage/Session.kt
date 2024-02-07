package com.louie.studybuddy.storage

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.louie.studybuddy.models.auth.Token
import com.louie.studybuddy.models.auth.User

object Session {
    private var loggedInUser: User? = null

    fun setLoggedInUser(user: User?) {
        this.loggedInUser = user
    }

    fun getLoggedInUser(): User? {
        return this.loggedInUser
    }

    fun getToken(context: Context): Token {
        val objectMapper = ObjectMapper()
        val content = FileHandler.readFromFile(context, "auth")
        return objectMapper.readValue(content, Token::class.java)
    }
}