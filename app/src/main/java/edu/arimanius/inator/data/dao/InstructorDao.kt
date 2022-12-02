package edu.arimanius.inator.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.Instructor

@Dao
interface InstructorDao: InsertableDao<Instructor> {
    // Get instructor by full name
    @Query("SELECT * FROM instructors WHERE fullName = :fullName")
    fun getByFullName(fullName: String): Instructor?
}
