package edu.arimanius.inator.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.GroupSchedule

@Dao
interface GroupScheduleDao: InsertableDao<GroupSchedule> {
    @Query("SELECT * FROM group_schedules " +
            "WHERE groupId = :groupId " +
            "AND courseId = :courseId " +
            "AND semesterId = :semesterId")
    suspend fun getGroupSchedules(groupId: Int, courseId: Int, semesterId: Int): List<GroupSchedule>

}
