package edu.arimanius.inator.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.dao.CourseDao
import edu.arimanius.inator.data.dao.GroupScheduleDao
import edu.arimanius.inator.data.dao.ProgramGroupDao
import edu.arimanius.inator.data.entity.Course
import edu.arimanius.inator.data.entity.GroupSchedule
import java.time.DayOfWeek

class ProgramWeeklyScheduleViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val programGroupDao: ProgramGroupDao
    private val courseDao: CourseDao
    private val groupScheduleDao: GroupScheduleDao

    init {
        programGroupDao = InatorDatabase.getInstance(application).programGroupDao()
        courseDao = InatorDatabase.getInstance(application).courseDao()
        groupScheduleDao = InatorDatabase.getInstance(application).groupScheduleDao()
    }

    suspend fun getProgramSchedule(
        programId: Int,
        dayOfWeek: DayOfWeek
    ): List<Pair<Course, GroupSchedule>> {
        return programGroupDao.getProgramGroups(programId).map { programGroup ->
            courseDao.suspendedGetCourse(
                programGroup.courseId, programGroup.semesterId
            ) to groupScheduleDao.getGroupSchedules(
                programGroup.groupId,
                programGroup.courseId,
                programGroup.semesterId
            ).filter {
                it.dayOfWeek == dayOfWeek
            }
        }.filter { it.second.isNotEmpty() }
            .map { it.first to it.second.single() }
    }
}
