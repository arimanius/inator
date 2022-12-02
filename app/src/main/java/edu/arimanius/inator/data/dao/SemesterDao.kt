package edu.arimanius.inator.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.Semester

@Dao
interface SemesterDao: InsertableDao<Semester> {
    @Query("SELECT * FROM semesters")
    fun getAll(): List<Semester>
}
