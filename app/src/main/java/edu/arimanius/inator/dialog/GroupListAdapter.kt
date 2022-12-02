package edu.arimanius.inator.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.dao.GroupWithInstructor
import edu.arimanius.inator.data.entity.GroupSchedule
import edu.arimanius.inator.data.viewmodels.AddGroupViewModel
import edu.arimanius.inator.data.viewmodels.ProgramWeeklyScheduleViewModel
import edu.arimanius.inator.util.toPersian
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupListAdapter(
    private val addGroupViewModel: AddGroupViewModel,
    private val programWeeklyScheduleViewModel: ProgramWeeklyScheduleViewModel,
) : RecyclerView.Adapter<GroupListAdapter.GroupViewHolder>() {

    private var groupList = emptyList<Pair<GroupWithInstructor, List<GroupSchedule>>>()

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.course_group_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentItem = groupList[position].first
        holder.itemView.findViewById<TextView>(R.id.tv_group_id).text =
            currentItem.group.groupId.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_instructor_name).text =
            currentItem.instructor.fullName
        holder.itemView.findViewById<TextView>(R.id.tv_capacity).text =
            currentItem.group.capacity.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_exam_time).text =
            currentItem.group.finalExamDate
        holder.itemView.findViewById<TextView>(R.id.tv_info).text = currentItem.group.info
        holder.itemView.findViewById<TextView>(R.id.tv_class_time).text = groupList[position]
            .second
            .map {
                "${it.dayOfWeek.toPersian()} " +
                        "از " +
                        "${it.start} " +
                        "تا " +
                        "${it.end}"
            }
            .reduce { acc, s -> "$acc\n$s" }

        holder.itemView.findViewById<Button>(R.id.btn_add).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                addGroupViewModel.addGroupToProgram(
                    programWeeklyScheduleViewModel.selectedProgramId,
                    currentItem.group.groupId,
                    currentItem.group.courseId,
                    currentItem.group.semesterId
                )
                if (it is Button) {
                    it.visibility = View.GONE
                    holder.itemView.findViewById<Button>(R.id.btn_delete).visibility = View.VISIBLE
                }
            }
        }

        holder.itemView.findViewById<Button>(R.id.btn_delete).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                addGroupViewModel.deleteGroupFromProgram(
                    programWeeklyScheduleViewModel.selectedProgramId,
                    currentItem.group.groupId,
                    currentItem.group.courseId,
                    currentItem.group.semesterId
                )
                if (it is Button) {
                    it.visibility = View.GONE
                    holder.itemView.findViewById<Button>(R.id.btn_add).visibility = View.VISIBLE
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            if (addGroupViewModel.groupExistsInProgram(
                    programWeeklyScheduleViewModel.selectedProgramId,
                    currentItem.group.groupId,
                    currentItem.group.courseId,
                    currentItem.group.semesterId
                )
            ) {
                holder.itemView.findViewById<Button>(R.id.btn_add).visibility = View.GONE
                holder.itemView.findViewById<Button>(R.id.btn_delete).visibility = View.VISIBLE
            } else {
                holder.itemView.findViewById<Button>(R.id.btn_add).visibility = View.VISIBLE
                holder.itemView.findViewById<Button>(R.id.btn_delete).visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    fun setData(groups: List<Pair<GroupWithInstructor, List<GroupSchedule>>>) {
        groupList = groups
        notifyDataSetChanged()
    }
}