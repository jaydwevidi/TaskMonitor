package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val TAG = "MainActivity"
    private var storedVerificationId = ""


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth
        val currentUser: FirebaseUser? = auth.currentUser
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Task Monitor"
        progressBar.visibility = View.VISIBLE
        ET_OTP_code.isEnabled = false
        supportActionBar?.hide()
        var codeSent = false
        //var mTasksViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        if (currentUser != null) {
            userLoggedIn()
        } else {
            view5.visibility = View.GONE
            progressBar.visibility = View.INVISIBLE
            view2.isVisible = true
            view3.isVisible = true
            view.isVisible = true
            ET_PhoneNumber.isVisible = true
            ET_OTP_code.isVisible = true
            button.isVisible = true
            TV_forgotPassword.isVisible = true


        }
        //auth.createUserWithEmailAndPassword("jayraj1999@gmail.com","jayraj01")

        button.setOnClickListener {
            if (codeSent) {
                ET_OTP_code.isEnabled = false
                progressBar.visibility = View.VISIBLE
                Log.d(TAG, "onCreate: Verifying Code...")
                val credential = PhoneAuthProvider.getCredential(
                    storedVerificationId,
                    ET_OTP_code.text.toString()
                )
                signInWithCredentials(credential)

            } else {
                progressBar.visibility = View.VISIBLE
                ET_PhoneNumber.isEnabled = false
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+${ET_PhoneNumber.text}",
                    60,
                    TimeUnit.SECONDS,
                    this,
                    callbacks
                )
            }
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                progressBar.visibility = View.VISIBLE
                ET_PhoneNumber.isEnabled = false
                signInWithCredentials(p0)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, "onVerificationFailed: ")
                if (e is FirebaseAuthInvalidCredentialsException)
                    Log.d(TAG, "onVerificationFailed: FirebaseAuthInvalidCredentialsException")
                else if (e is FirebaseTooManyRequestsException)
                    Log.d(TAG, "onVerificationFailed: FirebaseTooManyRequestsException")
                Toast.makeText(this@MainActivity, "Verification Failed", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                Log.d(TAG, "onCodeSent: to Phone Number ${ET_PhoneNumber.text}")
                Log.d(TAG, "onCodeSent: credentials $p0")
                storedVerificationId = p0
                progressBar.visibility = View.INVISIBLE
                ET_OTP_code.isEnabled = true
                codeSent = true
                button.text = "Verify Code"
                super.onCodeSent(p0, p1)
            }

        }

        /*postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue()
                Log.e(TAG, "onDataChange: $post")
                var xs= mutableListOf<Task>()
                for (snaps in dataSnapshot.children){
                    Log.d(TAG, "onDataChange: ${snaps.value}")
                    var x:Task = Task(
                        id = Integer.parseInt(snaps.child("id").value.toString()),
                        name = snaps.child("name").value.toString(),
                        date = snaps.child("date").value.toString(),
                        time = snaps.child("time").value.toString(),
                        description = snaps.child("description").value.toString()
                    )
                    xs.add(x)
                }
                mTasksViewModel.addTaskList(xs.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }*/
    }

    private fun signInWithCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    userLoggedIn()
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

    private fun userLoggedIn() {
        var user = auth.currentUser
        if (user != null) {
            var uid = user.uid
            val myRef = Firebase.database.reference

            val uidRef = myRef.child("Users").child(uid)
            uidRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue()
                    Log.d(TAG, "Users/$uid Value is: $value")
                    progressBar.visibility = View.INVISIBLE
                    if (value == null) {
                        var intentToRegister = Intent(this@MainActivity, Register::class.java)
                        intentToRegister.putExtra("uid", uid)
                        startActivity(intentToRegister)
                        finish()
                    } else {
                        openTasksActivity()
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })

        }

    }

    private fun openTasksActivity() {
        var intentToOpenTasks = Intent(this, Tasks::class.java)
        startActivity(intentToOpenTasks)
    }
}