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
        val task = tasks[position]
        holder.name.text = task.name
        holder.date.text = task.date
        if (task.finished) {
            holder.image.setImageResource(R.drawable.ic_user_check_solid)
            holder.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.name.paintFlags = 0
            // I don't know what this is but app dose'nt work without it

            if (task.date <= MyDateObject().getCurrentDate())
                holder.image.setImageResource(R.drawable.ic_user_times_solid)
            else
                holder.image.setImageResource(R.drawable.person_task_incomplete)
        }
    }


    inner class MyViewHolder(view: View, val onTaskClickListner: OnTaskClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val name: TextView = view.TV_taskName
        val date: TextView = view.TV_taskDate
        val image: ImageView = view.imageView

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
