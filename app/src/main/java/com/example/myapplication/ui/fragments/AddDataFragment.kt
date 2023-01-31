package com.example.myapplication.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MyDateObject
import com.example.myapplication.R
import com.example.myapplication.Task
import com.example.myapplication.ui.activity.TasksActivity
import kotlinx.android.synthetic.main.fragment_add_data.*
import kotlinx.android.synthetic.main.fragment_add_data.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

class AddDataFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var v: View
    lateinit var date: MyDateObject


    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_data, container, false)

        v.buttonDatePicker.setOnClickListener {
            showDatePicker()
        }
        /*view.ET_TaskDate.setOnClickListener {
            showDatePicker()
        }*/
        setHasOptionsMenu(true)
        return v
    }

    @InternalCoroutinesApi
    private fun addTaskToDatabase() {
        val mTasksViewModel = (activity as TasksActivity).mTasksViewModel
        mTasksViewModel.addTask(
            Task(
                0,
                ET_TaskName.text.toString(),
                buttonDatePicker.text.toString(),
                description = ET_description.text.toString()
            )
        )
        // finish()
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this.requireContext(),
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DATE),
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.date = MyDateObject(year, month + 1, dayOfMonth)
        showTimePicker()
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            this.requireContext(),
            this,
            Calendar.getInstance().get(Calendar.HOUR),
            Calendar.getInstance().get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        date.hourOfDay = hourOfDay
        date.minute = minute
        v.buttonDatePicker.text = date.toString()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_for_addtask, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.updateOrAdd) {
            if (v.buttonDatePicker.text.toString() == "")
                Toast.makeText(context, "Select Date First", Toast.LENGTH_SHORT).show()
            else if (v.ET_TaskName.text.toString() == "")
                Toast.makeText(context, "Add Task Name", Toast.LENGTH_SHORT).show()
            else {
                addTaskToDatabase()
                findNavController().navigate(R.id.action_addDataFragment_to_tasksFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}