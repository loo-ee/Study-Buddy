package com.louie.studybuddy.pages.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.auth0.jwt.JWT
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.louie.studybuddy.R
import com.louie.studybuddy.models.Token
import com.louie.studybuddy.models.User
import com.louie.studybuddy.pages.login.LoginPage
import com.louie.studybuddy.storage.FileHandler
import com.louie.studybuddy.storage.ServerData
import com.louie.studybuddy.storage.Session
import org.json.JSONObject
import java.io.IOException

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        try {
            getUser(this)
        } catch (e: IOException) {
            Toast.makeText(this, "Session Expired", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }

    fun redirectMyTasks(view: View) {
//        val intent = Intent(view.context, )
    }

    private fun getUser(context: Context) {
        if (Session.getLoggedInUser() != null) {
            return
        }

        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        val content = FileHandler.readFromFile(context, "auth")
        val token = objectMapper.readValue(content, Token::class.java)
        val decodedJWT = JWT.decode(token.getAccess())
        val userEmail = decodedJWT.getClaim("email").toString()

        val requestQueue = Volley.newRequestQueue(context)
        val request = object: JsonObjectRequest(
            Request.Method.GET,
            ServerData.serverURI + "/auth/get-user/?user_email=" + userEmail,
            null,
            fun(response: JSONObject) {
                try {
                    val responseBody = response.toString()
                    val foundUser = objectMapper.readValue(responseBody, User::class.java)
                    Session.setLoggedInUser(foundUser)
                } catch (e: JsonProcessingException) {
                    Log.d("exception_caught", e.toString());
                }
            },
            fun(_: VolleyError) {

            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${token.getAccess()}"
                return headers
            }
        }

        requestQueue.add(request)
    }
}