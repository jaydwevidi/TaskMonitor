package com.example.myapplication

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task.view.*
import java.util.*

open class RecyclerViewAdapter(
    var tasks: MutableList<Task>,
    var mOnTaskClickListener: OnTaskClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(),
    Filterable {

    var taskListAll: List<Task> = tasks.toList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task, parent, false)
        return MyViewHolder(view, mOnTaskClickListener)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task=tasks[position]
        holder.name.text = task.name
        holder.date.text = task.date
        if (task.finished) {
            holder.image.setImageResource(R.drawable.ic_user_check_solid)
            holder.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else if (task.date < getDate())
            holder.image.setImageResource(R.drawable.ic_user_times_solid)
    }

    private fun getDate(): String {
        val year=Calendar.getInstance().get(Calendar.YEAR)
        val month=Calendar.getInstance().get(Calendar.MONTH)
        val dayOfMonth=Calendar.getInstance().get(Calendar.DATE)

        var date="$year-"
        if(month <10)
            date+="0"
        date = "$date$month-"
        if(dayOfMonth <10)
            date+="0"
        date += dayOfMonth

        val hourOfDay =Calendar.getInstance().get(Calendar.HOUR)
        val minute = Calendar.getInstance().get(Calendar.MINUTE)

        var time = ""
        if (hourOfDay < 10)
            time+="0"
        time = "$time$hourOfDay:"

        if (minute < 10)
            time+="0"
        time+=minute

        return "$date $time"
    }

    inner class MyViewHolder(view: View,val onTaskClickListner: OnTaskClickListener):RecyclerView.ViewHolder(view),View.OnClickListener{
        val name: TextView =view.TV_taskName
        val date:TextView =view.TV_taskDate
        val image:ImageView=view.imageView

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onTaskClickListner.onTaskClick(adapterPosition)
        }
    }

    interface OnTaskClickListener {
        fun onTaskClick(position: Int)
    }

    override fun getFilter(): Filter {
        return myFilter
    }

    var myFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = mutableListOf<Task>()
            if (constraint.isNullOrEmpty())
                filteredList.addAll(taskListAll)
            else
                for (i in taskListAll) {
                    if (i.name.contains(constraint, true))
                        filteredList.add(i)
                }

            val x = FilterResults()
            x.values = filteredList
            return x
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            tasks.clear()
            tasks.addAll(results?.values as MutableList<Task>)
            notifyDataSetChanged()
        }

    }

}
