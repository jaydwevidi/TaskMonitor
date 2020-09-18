package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.coroutines.InternalCoroutinesApi

class TasksFragment : Fragment(), RecyclerViewAdapter.OnTaskClickListener {
    lateinit var list: List<Task>
    private val TAG = "TasksFragment"
    val adapter = RecyclerViewAdapter(mutableListOf<Task>(), this)


    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        // Inflate the layout for this fragment
        var mTasksViewModel = (activity as Tasks).mTasksViewModel
        view.rvTasks.layoutManager = LinearLayoutManager(context)

        view.rvTasks.adapter = adapter
        adapter.notifyDataSetChanged()
        val uid = (activity as Tasks).auth.uid
        val myRef = Firebase.database.reference.child("Tasks").child(uid!!)

        view.floatingActionButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onCreate: Button is pressed")
            //var intentToaddTasks = Intent(this, addTask::class.java)
            //startActivity(intentToaddTasks)
            findNavController().navigate(R.id.action_tasksFragment_to_addDataFragment)

        })


        mTasksViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            list = it
            Log.d(TAG, "onCreate: it is $it")
            adapter.tasks = it.toMutableList()
            adapter.taskListAll = it.toMutableList()
            myRef.setValue(it)
            adapter.notifyDataSetChanged()
        })
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment, menu)
        Log.d(TAG, "onCreateOptionsMenu: called")
        val menuItem = menu.findItem(R.id.search_button)
        val searchView = menuItem?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.myFilter.filter(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var mTasksViewModel = (activity as Tasks).mTasksViewModel

        if (item.itemId == R.id.add_sample_data) {
            mTasksViewModel.addTaskList(SampleData().sampleTaskList)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onTaskClick(position: Int) {
        val action = TasksFragmentDirections.actionTasksFragmentToUpdateFragment(list[position])
        findNavController().navigate(action)
    }
}