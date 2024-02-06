package com.louie.studybuddy.pages.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.louie.studybuddy.R
import com.louie.studybuddy.pages.home.HomePage
import com.louie.studybuddy.storage.FileHandler
import com.louie.studybuddy.storage.ServerData
import org.json.JSONObject
import java.io.IOException

class RegisterFieldsFrag: DialogFragment() {
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.DialogTheme))
        val inflater = requireActivity().layoutInflater

        this.view = inflater.inflate(R.layout.fragment_register, null)
        builder.setTitle("Register User")
            .setView(this.view)
            .setPositiveButton("Register",  fun(_, _) {
                val values = verifyFields()

                if (values != null) {
                    registerUser(values)
                }
                dismiss()
            })
            .setNegativeButton("Close", fun(_, _) {
                Toast.makeText(view.context, "Cancelled", Toast.LENGTH_SHORT).show()
                dismiss()
            })

        return builder.create()
    }

    private fun verifyFields(): HashMap<String, String>? {
        val values = HashMap<String, String>()

        val email = view.findViewById<EditText>(R.id.email_field).text.toString()
        val username = view.findViewById<EditText>(R.id.username_field).text.toString()
        val firstName = view.findViewById<EditText>(R.id.first_name_field).text.toString()
        val lastName = view.findViewById<EditText>(R.id.last_name_field).text.toString()
        val password = view.findViewById<EditText>(R.id.password_field).text.toString()
        val verifyPassword = view.findViewById<EditText>(R.id.password_verify_field).text.toString()

        if (email == "" || username == "" || firstName == "" ||
            lastName == "" || password == "" || verifyPassword == ""
        ) {
            return null
        }

        if (password != verifyPassword) {
            return null
        }

        values["email"] = email
        values["username"] = username
        values["first_name"] = firstName
        values["last_name"] = lastName
        values["password"] = password

        return values
    }

    private fun registerUser(body: HashMap<String, String>) {
        val requestQueue = Volley.newRequestQueue(view.context)
        val request = JsonObjectRequest(
            Request.Method.POST,
            ServerData.serverURI + "/auth/register/",
            JSONObject(body as Map<String, String>),
            {_: JSONObject ->
                Toast.makeText(view.context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                login(view.context, body["email"]!!, body["password"]!!)
            },
            {_: VolleyError ->
                Toast.makeText(view.context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }

    private fun login(context: Context, email: String, password: String) {
        val body = HashMap<String, String>()
        body["email"] = email
        body["password"] = password

        val requestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.POST,
            ServerData.serverURI + "/auth/token/",
            JSONObject(body as Map<String, String>),
            {response: JSONObject ->
                val intent = Intent(context, HomePage::class.java)

                try {
                    FileHandler.writeToFile(context, response.toString(), "auth")
                    context.startActivity(intent)
                } catch (e: IOException) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            },
            {error: VolleyError ->
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.d("exception_caught", error.networkResponse.statusCode.toString());
            }
        )

        requestQueue.add(request)
    }
}