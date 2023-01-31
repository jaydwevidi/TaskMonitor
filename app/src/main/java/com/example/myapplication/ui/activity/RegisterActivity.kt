package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.utils.UserDetails
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        progressBar_Register.visibility = View.INVISIBLE

        val uid = intent.getStringExtra("uid")
        TV_TandC.movementMethod = LinkMovementMethod.getInstance()
        buttonRegister.isEnabled = false

        buttonRegister.setOnClickListener(View.OnClickListener {
            val userObject = UserDetails(
                ET_Name.text.toString(),
                ET_eMail.text.toString()
            )
            progressBar_Register.visibility = View.VISIBLE
            Firebase.database.reference.child("Users").child(uid!!).setValue(userObject)
            progressBar_Register.visibility = View.INVISIBLE
            openTasksActivity()
        })

        switchAgree.setOnCheckedChangeListener { dbuttonView, isChecked ->
            buttonRegister.isEnabled = isChecked
        }
    }

    private fun openTasksActivity() {
        var intentToOpenTasksActivity = Intent(this, TasksActivity::class.java)
        startActivity(intentToOpenTasksActivity)
        finish()
    }
}

