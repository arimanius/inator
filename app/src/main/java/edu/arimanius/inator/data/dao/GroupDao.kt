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
                "INNER JOIN group_schedules " +
                "ON groups.groupId = group_schedules.groupId " +
                "AND groups.courseId = group_schedules.courseId " +
                "AND groups.semesterId = group_schedules.semesterId " +
                "WHERE courseId = :courseId AND semesterId = :semesterId"
    )
    suspend fun getGroupsWithInstructorAndSchedules(
        courseId: Int,
        semesterId: Int
    ): LiveData<List<GroupWithInstructorAndSchedules>>
}
