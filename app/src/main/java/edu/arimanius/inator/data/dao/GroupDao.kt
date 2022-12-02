package edu.arimanius.inator.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.Group

@Dao
interface GroupDao: InsertableDao<Group> {
    @Query("SELECT * FROM groups " +
            "WHERE groupId = :groupId " +
            "AND courseId = :courseId " +
            "AND semesterId = :semesterId")
    suspend fun getGroup(groupId: Int, courseId: Int, semesterId: Int): Group
}
