package edu.arimanius.inator.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.viewmodels.AddGroupViewModel
import kotlinx.coroutines.runBlocking

class AddCourseGroupDialog : DialogFragment() {
    private lateinit var addGroupViewModel: AddGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_group_dialog, container, false)
        addGroupViewModel = ViewModelProvider(this)[AddGroupViewModel::class.java]

        val bundle = arguments
        val courseId = bundle?.getInt("courseId")
        val semesterId = bundle?.getInt("semesterId")

        val adapter = GroupListAdapter(addGroupViewModel, 1)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_group)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        addGroupViewModel.getCourse(courseId!!, semesterId!!).observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.tv_course_name).text = it.name
            view.findViewById<TextView>(R.id.tv_course_unit).text = it.units.toString()

        }

        addGroupViewModel.getGroups(courseId!!, semesterId!!)
            .observe(viewLifecycleOwner) { groups ->
                val groupsWithSchedule = groups.map { group ->
                    group to runBlocking { addGroupViewModel.getGroupSchedules(group.group.groupId, courseId, semesterId) }
                }
                adapter.setData(groupsWithSchedule)
            }

        return view
    }
}
