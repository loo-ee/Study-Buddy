package com.louie.studybuddy.global_fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewDebug
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.louie.studybuddy.R
import com.louie.studybuddy.models.auth.Token
import com.louie.studybuddy.models.auth.User
import com.louie.studybuddy.models.tasks.Task
import com.louie.studybuddy.models.tasks.UserTask
import com.louie.studybuddy.pages.my_tasks.TaskPage
import com.louie.studybuddy.storage.ServerData
import com.louie.studybuddy.storage.Session
import org.json.JSONObject

class TaskFragment(): Fragment() {
    private val customLayout =  ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    private lateinit var view: View
    private lateinit var args: Bundle
    private lateinit var userTask: UserTask
    private lateinit var token: Token

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.view = inflater.inflate(R.layout.fragment_task, container, false)
        this.args = requireArguments()
        this.token = Session.getToken(requireContext())
        this.userTask = args.getSerializable("userTask", UserTask::class.java)!!

        try {
            this.view.setOnClickListener{_: View ->
                val taskArgs = Bundle()
                taskArgs.putSerializable("userTask", this.args.getSerializable("userTask", UserTask::class.java))

                val intent = Intent(requireContext(), TaskPage::class.java)
                intent.putExtras(taskArgs)
                startActivity(intent)
            }
        } catch (e: Exception) {
            Log.d("exception_caught", e.toString())
        }
        addTaskToPage(requireContext())

        return this.view
    }

    private fun addTaskToPage(context: Context) {
        val task = this.userTask.getTask()
        val buddy = this.userTask.getBuddy()
        val activity = context as AppCompatActivity
        val nameTextView = TextView(activity).apply {
            text = task.getTitle()
            textSize = 30f
            setTextColor(ContextCompat.getColor(activity, R.color.cambridge_dark))
            layoutParams = this@TaskFragment.customLayout
        }
        val ageTextView = TextView(activity).apply {
            text = buddy.getFirstName()
            textSize = 20f
            setTextColor(ContextCompat.getColor(activity, R.color.cambridge_light))
            layoutParams = this@TaskFragment.customLayout
        }

        (view as ViewGroup).addView(nameTextView)
        (view as ViewGroup).addView(ageTextView)
    }
}