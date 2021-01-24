package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.InternalCoroutinesApi

class Tasks : AppCompatActivity() {
    var userName = ""
    lateinit var auth: FirebaseAuth

    @InternalCoroutinesApi
    lateinit var mTasksViewModel: TaskViewModel
    private val TAG = "Tasks"

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        mTasksViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        auth = Firebase.auth
        val uid = auth.uid
        val myRef = Firebase.database.reference.child("Users").child(uid!!)

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName = snapshot.child("name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigatinController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navigatinController)


    }
}