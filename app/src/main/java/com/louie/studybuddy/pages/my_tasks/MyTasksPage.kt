package com.louie.studybuddy.pages.my_tasks

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.louie.studybuddy.R
import com.louie.studybuddy.global_fragments.TaskFragment
import com.louie.studybuddy.models.auth.Token
import com.louie.studybuddy.models.tasks.UserTask
import com.louie.studybuddy.storage.ServerData
import com.louie.studybuddy.storage.Session
import org.json.JSONArray

class MyTasksPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tasks_page)
        getUserTasks(this)
    }

   private fun getUserTasks(context: Context) {
       val objectMapper = ObjectMapper()
           .registerModule(JavaTimeModule())
           .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
       val token = Session.getToken(context)
       val requestQueue = Volley.newRequestQueue(context)
       val request = object: JsonArrayRequest(
            Method.GET,
            ServerData.serverURI + "/tasks/get-user-own-tasks/?user_email=" + Session.getLoggedInUser()!!.getEmail(),
            null,
            { response: JSONArray ->
                val responseBody = response.toString()
                val userTasks: List<UserTask> = objectMapper.readValue(responseBody, object:
                    TypeReference<List<UserTask>>(){})
                val linearLayout = findViewById<LinearLayout>(R.id.tasks_container)
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()

                try {
                    userTasks.forEach { userTask: UserTask ->
                        val taskFragment = TaskFragment()
                        val args = Bundle()

                        args.putSerializable("userTask", userTask)
                        taskFragment.arguments = args
                        ft.add(linearLayout.id, taskFragment, userTask.getId().toString())
                    }

                    ft.commit()
                } catch (e: Exception) {
                    Log.d("volley", e.toString())
                }
            },
            {error: VolleyError ->
                Toast.makeText(context, "Could not load tasks", Toast.LENGTH_SHORT).show()
                Log.d("volley", error.toString())
            }
       ) {
           override fun getHeaders(): MutableMap<String, String> {
               val headers = HashMap<String, String>()
               headers["Authorization"] = "Bearer ${token.getAccess()}"
               return headers
           }
       }

       requestQueue.add(request)
   }
}