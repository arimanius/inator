package edu.arimanius.inator.fragment.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.inator.R
import edu.arimanius.inator.data.viewmodels.ProgramWeeklyScheduleViewModel
import edu.arimanius.inator.util.toPersian
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class DayListAdapter(
    private val programWeeklyScheduleViewModel: ProgramWeeklyScheduleViewModel,
    private val context: Context,
) : RecyclerView.Adapter<DayListAdapter.DayViewHolder>() {

    private var dayList = listOf(
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
    )

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.day_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val currentItem = dayList[position]
        holder.itemView.findViewById<TextView>(R.id.tv_day_name).text = currentItem.toPersian()

        val adapter = DayCourseListAdapter(context)
        val recyclerView = holder.itemView.findViewById<RecyclerView>(R.id.rv_day)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.Main).launch {
            programWeeklyScheduleViewModel.getProgramSchedule(1, currentItem).let {
                adapter.setData(it)
            }
        }
    }


    override fun getItemCount(): Int {
        return dayList.size
    }
}