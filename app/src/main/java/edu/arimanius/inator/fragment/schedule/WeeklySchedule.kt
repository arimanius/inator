package edu.arimanius.inator.fragment.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.viewmodels.ProgramWeeklyScheduleViewModel

class WeeklySchedule : Fragment() {

    private lateinit var programWeeklyScheduleViewModel: ProgramWeeklyScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_schedule, container, false)

        programWeeklyScheduleViewModel = ViewModelProvider(this)[ProgramWeeklyScheduleViewModel::class.java]

        val adapter = DayListAdapter(programWeeklyScheduleViewModel, requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_week)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<Button>(R.id.btn_course_list).setOnClickListener {
            // nav to course list
            findNavController().navigate(R.id.action_weeklySchedule_to_courseList)
        }

        return view
    }
}