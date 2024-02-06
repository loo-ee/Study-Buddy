package com.louie.studybuddy.models

import com.fasterxml.jackson.annotation.JsonProperty

class Token() {
    @JsonProperty("access")
    private lateinit var access: String

    @JsonProperty("refresh")
    private lateinit var refresh: String

    constructor(access: String, refresh: String) : this() {
        this.access = access
        this.refresh = refresh
    }

    fun access(access: String): Token {
        this.access = access
        return this
    }

    fun refresh(refresh: String): Token {
        this.refresh = refresh
        return this
    }

    fun getAccess(): String {
        return access
    }

    fun getRefresh(): String {
        return refresh
    }
}