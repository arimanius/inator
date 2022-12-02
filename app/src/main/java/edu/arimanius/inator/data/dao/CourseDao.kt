package edu.arimanius.inator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.Course

@Dao
interface CourseDao: InsertableDao<Course> {
    @Query("SELECT * FROM courses")
    fun getAllCourses(): LiveData<List<Course>>
}
