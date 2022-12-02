package edu.arimanius.inator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import edu.arimanius.inator.data.entity.Group
import edu.arimanius.inator.data.entity.Instructor

data class GroupWithInstructor(
    @Embedded
    val group: Group,
    @Relation(
        parentColumn = "instructorId",
        entityColumn = "id",
    )
    val instructor: Instructor,
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
                "WHERE courseId = :courseId " +
                "AND semesterId = :semesterId"
    )
    fun getGroupsWithInstructor(
        courseId: Int,
        semesterId: Int
    ): LiveData<List<GroupWithInstructor>>
}
