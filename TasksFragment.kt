package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.coroutines.InternalCoroutinesApi

class TasksFragment : Fragment() , RecyclerViewAdapter.OnTaskClickListener{
    lateinit var list : List<Task>
    private  val TAG = "TasksFragment"

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        // Inflate the layout for this fragment

        view.rvTasks.layoutManager= LinearLayoutManager(context)
        val adapter = RecyclerViewAdapter(listOf<Task>(), this)
        view.rvTasks.adapter = adapter

        val uid = (activity as Tasks).auth.uid
        val myRef= Firebase.database.reference.child("Tasks").child(uid!!)

        view.floatingActionButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onCreate: Button is pressed")
            //var intentToaddTasks = Intent(this, addTask::class.java)
            //startActivity(intentToaddTasks)
            findNavController().navigate(R.id.action_tasksFragment_to_addDataFragment)

        })

        var mTasksViewModel =(activity as Tasks).mTasksViewModel
        mTasksViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            list = it
            Log.d(TAG, "onCreate: it is $it")
            adapter.tasks = it
            myRef.setValue(it)
            adapter.notifyDataSetChanged()
        })
        return view
    }

    override fun onTaskClick(position: Int) {
        val action = TasksFragmentDirections.actionTasksFragmentToUpdateFragment(list[position])
        findNavController().navigate(action)
    }
}