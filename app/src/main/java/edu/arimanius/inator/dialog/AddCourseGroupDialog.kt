package edu.arimanius.inator.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.viewmodels.AddGroupViewModel

class AddCourseGroupDialog : DialogFragment() {
    private lateinit var addGroupViewModel: AddGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_group_dialog, container, false)

        val bundle = arguments
        val courseId = bundle?.getInt("courseId")
        val semesterId = bundle?.getInt("semesterId")

        val adapter = GroupListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_group)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        addGroupViewModel = ViewModelProvider(this)[AddGroupViewModel::class.java]
        addGroupViewModel.getGroups(courseId!!, semesterId!!).observe(viewLifecycleOwner) { groups ->
            adapter.setData(groups)
        }

        return view
    }
}
