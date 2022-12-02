package edu.arimanius.inator.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.dao.GroupWithInstructor

class GroupListAdapter: RecyclerView.Adapter<GroupListAdapter.GroupViewHolder>() {

    private var groupList = emptyList<GroupWithInstructor>()

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.course_group_row, parent, false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentItem = groupList[position]
        holder.itemView.findViewById<TextView>(R.id.tv_group_id).text = currentItem.group.groupId.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_instructor_name).text = currentItem.instructor.fullName
        holder.itemView.findViewById<TextView>(R.id.tv_capacity).text = currentItem.group.capacity.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_exam_time).text = currentItem.group.finalExamDate
        holder.itemView.findViewById<TextView>(R.id.tv_info).text = currentItem.group.info
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    fun setData(groups: List<GroupWithInstructor>) {
        groupList = groups
        notifyDataSetChanged()
    }
}