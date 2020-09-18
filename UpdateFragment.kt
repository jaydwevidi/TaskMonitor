package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

class UpdateFragment : Fragment(),DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{
    private lateinit var v:View
    private val args by navArgs<UpdateFragmentArgs>()
    private var date=""
    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_update, container, false)
        v.uET_TaskName.setText(args.taskObject.name)
        v.ubuttonDatePicker.text = args.taskObject.date
        v.uET_description.setText(args.taskObject.description)
        v.checkBoxDone.isChecked= args.taskObject.finished
        v.ubuttonDatePicker.setOnClickListener{
            showDatePicker()
        }

        val mTasksViewModel =(activity as Tasks).mTasksViewModel
        v.uaddTaskButton.setOnClickListener {
            addDataToDatabase()
            // finish()
            findNavController().navigate(R.id.action_updateFragment_to_tasksFragment)
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        v.uDeleteButton.setOnClickListener {
            mTasksViewModel.deleteTask(args.taskObject)
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            findNavController().navigate(R.id.action_updateFragment_to_tasksFragment)
        }
        return v
    }

    @InternalCoroutinesApi
    private fun addDataToDatabase(){
        val mTasksViewModel =(activity as Tasks).mTasksViewModel
        mTasksViewModel.updateTask(
            Task(args.taskObject.id,
                uET_TaskName.text.toString(),
                ubuttonDatePicker.text.toString(),
                description = uET_description.text.toString(),
                finished = checkBoxDone.isChecked
            )
        )
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
        v.ubuttonDatePicker.text = "$d $t"
    }
}