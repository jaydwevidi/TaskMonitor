package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add_data.*
import kotlinx.android.synthetic.main.fragment_add_data.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

class AddDataFragment : Fragment(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    lateinit var v:View
    var date:String = ""


    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_data, container, false)

        v.buttonDatePicker.setOnClickListener{
            showDatePicker()
        }
        /*view.ET_TaskDate.setOnClickListener {
            showDatePicker()
        }*/

        v.addTaskButton.setOnClickListener {
            if (v.buttonDatePicker.text.toString() == "")
                Toast.makeText(context, "Select Date First", Toast.LENGTH_SHORT).show()
            else if (v.ET_TaskName.text.toString() == "")
                Toast.makeText(context, "Add Task Name", Toast.LENGTH_SHORT).show()
            else {
                addTaskToDatabase()
                findNavController().navigate(R.id.action_addDataFragment_to_tasksFragment)
            }
        }

        return v
    }

    @InternalCoroutinesApi
    private fun addTaskToDatabase(){
        val mTasksViewModel =(activity as Tasks).mTasksViewModel
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

    private fun showDatePicker(){
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
       this.date = dateAsString(year,month,dayOfMonth)
        showTimePicker()
    }

    private fun showTimePicker(){
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
        displayDate(date, timeAsString(hourOfDay,minute))
    }

    private fun timeAsString( hourOfDay: Int, minute: Int):String{
        var time = ""
        if (hourOfDay < 10)
            time+="0"
        time = "$time$hourOfDay:"

        if (minute < 10)
            time+="0"
        time+=minute

        return time
    }

    private fun dateAsString(year: Int, month: Int, dayOfMonth: Int): String {
        var date="$year-"

        if(month <10)
            date+="0"
        date = "$date$month-"

        if(dayOfMonth <10)
            date+="0"

        return date+dayOfMonth

    }

    private fun displayDate(d:String,t:String){
        v.buttonDatePicker.text = "$d $t"
    }
}