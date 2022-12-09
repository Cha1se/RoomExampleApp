package com.cha1se.roomexampleapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var container: ConstraintLayout
    private lateinit var taskInput: EditText
    private lateinit var addBtn: Button
    private lateinit var mainLay: ConstraintLayout
    private lateinit var tasksLay: LinearLayout

    private var db: AppDatabase? = null
    private var taskDao: TaskDao? = null
    private var taskText: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.container)
        taskInput = findViewById(R.id.taskInput)
        addBtn = findViewById(R.id.addButton)
        mainLay = findViewById(R.id.mainLayout)
        tasksLay = findViewById(R.id.tasksLayout)

        addBtn.setOnClickListener(View.OnClickListener {

            if (!taskInput.text.toString().equals("")) {
                var task: TextView = TextView(this)

                task.apply {
                    textSize = 16f
                    setTextColor(Color.WHITE)
                }

                task.text = ""

                Observable.fromCallable({

                    db = AppDatabase.getAppDatabase(context = this)
                    taskDao = db?.taskDao()

                    taskText = Task(task = taskInput.text.toString())

                    with(taskDao) {
                        this?.setTask(taskText!!)
                    }

                    Log.e("TAG", db?.taskDao()?.getTasks()?.last()!!.id.toString() + " - " + db?.taskDao()?.getTasks()?.last()!!.task)
                    task.text = db?.taskDao()?.getTasks()?.last()!!.task.toString()

                }).doOnError({Log.e("ERROR", it.message.toString())})
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        tasksLay.addView(task)
                        taskInput.editableText.clear()
                    }
                    .subscribe()

            } else {
                Toast.makeText(this, "Text field is empty", Toast.LENGTH_SHORT).show()
            }
        })



    }
}