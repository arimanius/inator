package edu.arimanius.inator.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.dao.CourseDao
import edu.arimanius.inator.data.dao.GroupScheduleDao
import edu.arimanius.inator.data.dao.ProgramDao
import edu.arimanius.inator.data.dao.ProgramGroupDao
import edu.arimanius.inator.data.entity.Course
import edu.arimanius.inator.data.entity.GroupSchedule
import edu.arimanius.inator.data.entity.Program
import java.time.DayOfWeek

class ProgramWeeklyScheduleViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val programDao: ProgramDao
    private val programGroupDao: ProgramGroupDao
    private val courseDao: CourseDao
    private val groupScheduleDao: GroupScheduleDao

    val programs: LiveData<List<Program>>
    var selectedProgramId: Int = 1

    init {
        programDao = InatorDatabase.getInstance(application).programDao()
        programGroupDao = InatorDatabase.getInstance(application).programGroupDao()
        courseDao = InatorDatabase.getInstance(application).courseDao()
        groupScheduleDao = InatorDatabase.getInstance(application).groupScheduleDao()

        programs = programDao.getAllPrograms()
    }

    suspend fun getProgramSchedule(
        dayOfWeek: DayOfWeek
    ): List<Pair<Course, GroupSchedule>> {
        return programGroupDao.getProgramGroups(selectedProgramId).map { programGroup ->
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

    fun selectProgram(programName: String) {
        selectedProgramId = programs.value?.find { it.name == programName }?.id ?: 1
    }
}
