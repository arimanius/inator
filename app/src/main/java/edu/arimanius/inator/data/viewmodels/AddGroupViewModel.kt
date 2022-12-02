package edu.arimanius.inator.data.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.dao.GroupDao
import edu.arimanius.inator.data.dao.GroupScheduleDao
import edu.arimanius.inator.data.dao.GroupWithInstructor
import edu.arimanius.inator.data.dao.ProgramGroupDao
import edu.arimanius.inator.data.entity.Group
import edu.arimanius.inator.data.entity.GroupSchedule
import edu.arimanius.inator.data.entity.ProgramGroup

class AddGroupViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val programGroupDao: ProgramGroupDao
    private val groupScheduleDao: GroupScheduleDao
    private val groupDao: GroupDao

    init {
        programGroupDao = InatorDatabase.getInstance(application).programGroupDao()
        groupScheduleDao = InatorDatabase.getInstance(application).groupScheduleDao()
        groupDao = InatorDatabase.getInstance(application).groupDao()
    }

    suspend fun addGroupToProgram(programId: Int, groupId: Int, courseId: Int, semesterId: Int) {
        val programGroups = programGroupDao.getProgramGroups(programId)
        val newSchedules = groupScheduleDao.getGroupSchedules(groupId, courseId, semesterId)
        val newGroup = groupDao.getGroup(groupId, courseId, semesterId)
        if (programGroupDao.groupExistsInProgram(programId, groupId, courseId, semesterId)) {
            Toast.makeText(
                getApplication(),
                "این گروه در برنامه شما وجود دارد",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        validateSchedules(programGroups, newSchedules)
        validateFinalExam(programGroups, newGroup)
        programGroupDao.insert(ProgramGroup(programId, groupId, courseId, semesterId))
    }

    private suspend fun validateFinalExam(
        programGroups: List<ProgramGroup>,
        newGroup: Group
    ) {
        for (programGroup in programGroups) {
            val oldGroup = groupDao.getGroup(
                programGroup.groupId,
                programGroup.courseId,
                programGroup.semesterId,
            )
            if (newGroup.finalExamDate == oldGroup.finalExamDate) {
                Toast.makeText(
                    getApplication(),
                    "زمان امتحان نهایی گروه های انتخابی شما با هم تداخل دارد",
                    Toast.LENGTH_LONG
                ).show()
                break
            }
        }
    }

    private suspend fun validateSchedules(
        programGroups: List<ProgramGroup>,
        newSchedules: List<GroupSchedule>
    ) {
        outer@ for (programGroup in programGroups) {
            val oldSchedules = groupScheduleDao.getGroupSchedules(
                programGroup.groupId,
                programGroup.courseId,
                programGroup.semesterId,
            )
            for (newSchedule in newSchedules) {
                for (oldSchedule in oldSchedules) {
                    if (newSchedule.dayOfWeek == oldSchedule.dayOfWeek &&
                        (newSchedule.start in oldSchedule.start..oldSchedule.end ||
                                newSchedule.end in oldSchedule.start..oldSchedule.end)
                    ) {
                        Toast.makeText(
                            getApplication(),
                            "در این زمان، گروه دیگری در برنامه شما وجود دارد",
                            Toast.LENGTH_LONG
                        ).show()
                        break@outer
                    }
                }
            }
        }
    }

    fun getGroups(
        courseId: Int,
        semesterId: Int
    ): LiveData<List<GroupWithInstructor>> {
        return groupDao.getGroupsWithInstructor(courseId, semesterId)
    }

    suspend fun getGroupSchedules(
        groupId: Int,
        courseId: Int,
        semesterId: Int
    ): List<GroupSchedule> {
        return groupScheduleDao.getGroupSchedules(groupId, courseId, semesterId)
    }
}
