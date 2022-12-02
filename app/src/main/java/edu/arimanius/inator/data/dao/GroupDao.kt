package edu.arimanius.inator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import edu.arimanius.inator.data.entity.Group
import edu.arimanius.inator.data.entity.GroupSchedule
import edu.arimanius.inator.data.entity.Instructor

data class GroupWithInstructorAndSchedules(
    @Embedded
    val group: Group,
    @Embedded
    val instructor: Instructor,
    @Relation(
        parentColumn = "id",
        entityColumn = "instructorId",
    )
    val schedules: List<GroupSchedule>
)

@Dao
interface GroupDao : InsertableDao<Group> {
    @Query(
        "SELECT * FROM groups " +
                "WHERE groupId = :groupId " +
                "AND courseId = :courseId " +
                "AND semesterId = :semesterId"
    )
    suspend fun getGroup(groupId: Int, courseId: Int, semesterId: Int): Group

    @Query(
        "SELECT * FROM groups " +
                "INNER JOIN instructors ON groups.instructorId = instructors.id " +
                "INNER JOIN group_schedules as schedules " +
                "ON groups.groupId = schedules.groupId " +
                "AND groups.courseId = schedules.courseId " +
                "AND groups.semesterId = schedules.semesterId " +
                "WHERE groups.courseId = :courseId AND groups.semesterId = :semesterId"
    )
    fun getGroupsWithInstructorAndSchedules(
        courseId: Int,
        semesterId: Int
    ): LiveData<List<GroupWithInstructorAndSchedules>>
}
