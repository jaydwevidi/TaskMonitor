package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.UpdateFragmentArgs
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

class UpdateFragment : Fragment(),DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{
    private lateinit var v:View
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var date:MyDateObject
    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        v = inflater.inflate(R.layout.fragment_update, container, false)
        v.uET_TaskName.setText(args.taskObject.name)
        v.ubuttonDatePicker.text = args.taskObject.date
        v.uET_description.setText(args.taskObject.description)
        v.checkBoxDone.isChecked= args.taskObject.finished
        v.ubuttonDatePicker.setOnClickListener{
            showDatePicker()
        }

        val mTasksViewModel =(activity as Tasks).mTasksViewModel
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
        this.date = MyDateObject(year, month+1, dayOfMonth)
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
        date.hourOfDay = hourOfDay
        date.minute = minute
        v.ubuttonDatePicker.text = date.toString()
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_and_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val mTasksViewModel = (activity as Tasks).mTasksViewModel
        if (item.itemId == R.id.deleteTaskMenuItem) {
            mTasksViewModel.deleteTask(args.taskObject)
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            findNavController().navigate(R.id.action_updateFragment_to_tasksFragment)
        }
        if (item.itemId == R.id.updateOrAdd) {
            addDataToDatabase()
            // finish()
            findNavController().navigate(R.id.action_updateFragment_to_tasksFragment)
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return super.onOptionsItemSelected(item)
    }
}