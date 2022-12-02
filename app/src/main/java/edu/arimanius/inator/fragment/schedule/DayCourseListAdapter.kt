package edu.arimanius.inator.fragment.schedule

import edu.arimanius.inator.data.entity.GroupSchedule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.entity.Course

class DayCourseListAdapter(val context: Context) :
    RecyclerView.Adapter<DayCourseListAdapter.DayCourseViewHolder>() {

    private var courseList = emptyList<Pair<Course, GroupSchedule>>()

    class DayCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayCourseViewHolder {
        return DayCourseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.course_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DayCourseViewHolder, position: Int) {
        val currentItem = courseList[position]

        holder.itemView.findViewById<TextView>(R.id.tv_course_name).text = currentItem.first.name
        holder.itemView.findViewById<TextView>(R.id.tv_time).text = currentItem.second.let {
            "از " +
                    "${it.start}\n" +
                    "تا " +
                    "${it.end}"
        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    fun setData(courses: List<Pair<Course, GroupSchedule>>) {
        courseList = courses
        notifyDataSetChanged()
    }
}