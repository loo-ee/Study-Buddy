package com.louie.studybuddy.pages.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.louie.studybuddy.R
import com.louie.studybuddy.pages.home.HomePage
import com.louie.studybuddy.storage.FileHandler
import com.louie.studybuddy.storage.ServerData
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets

class LoginPage : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        title = "Login"
    }

    fun loginOrRegister(view: View) {
        try {
            val progressbar = findViewById<ProgressBar>(R.id.loading)
            val emailFIeld = findViewById<EditText>(R.id.email)
            val passwordField = findViewById<EditText>(R.id.password)

            this.email = emailFIeld.text.toString()
            this.password = passwordField.text.toString()

            Toast.makeText(view.context, "Redirecting...", Toast.LENGTH_SHORT).show()
            progressbar.visibility = View.VISIBLE
            login(this)
        } catch (e: Exception) {
            Toast.makeText(view.context, "Heheheh", Toast.LENGTH_SHORT).show()
        }
    }

    private fun login(context: Context) {
        val body = HashMap<String, String>()
        body["email"] = this.email
        body["password"] = this.password

        val requestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.POST,
            ServerData.serverURI + "/auth/token/",
            JSONObject(body as Map<String, String>),
            {  response: JSONObject ->
                FileHandler.writeToFile(context, response.toString(), "auth")
                val intent = Intent(context, HomePage::class.java)
                context.startActivity(intent)
            },
            {  _: VolleyError ->
                val progressbar = (context as Activity).findViewById<ProgressBar>(R.id.loading)
                progressbar.visibility = View.GONE
                validateEmail(context)
            }
        )

        requestQueue.add(request)
    }

    private fun validateEmail(context: Context) {
        val requestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET,
            ServerData.serverURI + "/auth/validate-email/?email=" + this.email,
            null,
            {_: JSONObject ->
                Toast.makeText(context, "Password incorrect", Toast.LENGTH_SHORT).show()
            },
            {error: VolleyError ->
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)

                try {
                    val data = JSONObject(responseBody)
                    val message = data.optString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    val newAccFrag = RegisterPromptFrag()
                    val args = Bundle()

                    args.putString("email", this.email)
                    args.putString("password", this.password)

                    newAccFrag.arguments = args
                    newAccFrag.show(supportFragmentManager, "Show Dialog")
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        )

        requestQueue.add(request)
    }
}