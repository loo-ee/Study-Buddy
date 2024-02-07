package com.louie.studybuddy.pages.my_tasks

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.louie.studybuddy.R
import com.louie.studybuddy.models.tasks.UserTask
import com.louie.studybuddy.utils.DateParser

class TaskPage : AppCompatActivity() {
    private lateinit var userTask: UserTask

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_page)

        try {
            this.userTask = intent.getSerializableExtra("userTask", UserTask::class.java)!!
            val date = findViewById<TextView>(R.id.date)
            val taskDescription = findViewById<TextView>(R.id.taskDescription)
            val buddyName = findViewById<TextView>(R.id.buddy_name)
            val status = findViewById<TextView>(R.id.status)
            val task = this.userTask.getTask()
            val buddy = this.userTask.getBuddy()
            val statusText = if (userTask.isDone()) "Completed" else "Pending"
            val parsedDate = DateParser.parseDate(task.getDateCreated().toString())

            title = task.getTitle()
            taskDescription.text = task.getDescription()
            buddyName.text = buddy.getFirstName() + " " + buddy.getLastName()
            date.text = parsedDate
            status.text = statusText
        } catch (e: Exception) {
            Log.d("exception_caught", e.toString())
        }
    }
}