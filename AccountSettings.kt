package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_account_settings.view.*
import kotlinx.coroutines.InternalCoroutinesApi

class AccountSettings : Fragment() {

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account_settings, container, false)


        val usNumber = (activity as Tasks).auth.currentUser?.phoneNumber
        view.TV_accountName.text = (activity as Tasks).userName
        view.TV_PhoneNumber.text = usNumber.toString()

        view.signOut.setOnClickListener{
            (activity as Tasks).mTasksViewModel.deleteAll()
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