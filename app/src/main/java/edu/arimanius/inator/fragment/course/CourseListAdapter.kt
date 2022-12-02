package edu.arimanius.inator.fragment.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.entity.Course

class CourseListAdapter : RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>() {

    private var courseList = emptyList<Course>()

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.course_row, parent, false))
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.itemView.findViewById<TextView>(R.id.tv_course_name).text = currentItem.name
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    fun setData(courses: List<Course>) {
        courseList = courses
        notifyDataSetChanged()
    }
}