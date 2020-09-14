package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_account_settings.view.*

class AccountSettings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account_settings, container, false)


        val usNumber = Firebase.auth.currentUser?.phoneNumber
        view.TV_accountName.text = (activity as Tasks).userName
        view.TV_PhoneNumber.text = usNumber.toString()

        view.signOut.setOnClickListener{
            Firebase.auth.signOut()
            activity?.finish()

            //val intentLoggedOut = Intent(this, MainActivity::class.java)
            //intentLoggedOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            //finishAffinity()
            //startActivity(intentLoggedOut)
        }
        return view
    }


}