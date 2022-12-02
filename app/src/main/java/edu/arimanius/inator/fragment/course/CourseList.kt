package edu.arimanius.inator.fragment.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.arimanius.inator.R
import edu.arimanius.inator.data.viewmodels.CourseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseList : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var courseViewModel: CourseViewModel
    private lateinit var adapter: CourseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course_list, container, false)

        adapter = CourseListAdapter(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_courses)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        courseViewModel = ViewModelProvider(this)[CourseViewModel::class.java]
        courseViewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.setData(courses)
        }

        view.findViewById<Button>(R.id.btn_weekly_schedule).setOnClickListener {
            // nav to weekly schedule
            findNavController().navigate(R.id.action_courseList_to_weeklySchedule)
        }

        val dropdown = view.findViewById<Spinner>(R.id.s_department)
        courseViewModel.departments.observe(viewLifecycleOwner) { departments ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                departments.map { it.name })
            dropdown.adapter = adapter
        }
        dropdown.onItemSelectedListener = this

        view.findViewById<FloatingActionButton>(R.id.fab_add_program).setOnClickListener {
            findNavController().navigate(R.id.action_courseList_to_addProgram)
        }

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position).let {
            CoroutineScope(Dispatchers.Main).launch {
                courseViewModel.courses = courseViewModel.getCoursesByDepartment(it as String)
                courseViewModel.courses.observe(viewLifecycleOwner) { courses ->
                    adapter.setData(courses)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}