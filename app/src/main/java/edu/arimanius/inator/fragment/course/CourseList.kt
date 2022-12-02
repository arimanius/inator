package edu.arimanius.inator.fragment.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.viewmodels.CourseViewModel

class CourseList : Fragment() {

    private lateinit var courseViewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course_list, container, false)

        val adapter = CourseListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_courses)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        courseViewModel = ViewModelProvider(this)[CourseViewModel::class.java]
        courseViewModel.allCourses.observe(viewLifecycleOwner) { courses ->
            adapter.setData(courses)
        }

        return view
    }
}