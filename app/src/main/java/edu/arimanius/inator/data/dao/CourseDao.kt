package edu.arimanius.inator.data.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.Course

@Dao
interface CourseDao: InsertableDao<Course> {
    @Query("SELECT * FROM courses")
    fun getAllCourses(): LiveData<List<Course>>

    @Query("SELECT * FROM courses WHERE departmentId = :departmentId")
    fun getCoursesByDepartment(departmentId: Int): LiveData<List<Course>>

    @Query("SELECT * FROM courses WHERE courseId = :courseId AND semesterId = :semesterId")
    fun getCourse(courseId: Int, semesterId: Int): LiveData<Course>

    @Query("SELECT * FROM courses WHERE courseId = :courseId AND semesterId = :semesterId")
    suspend fun suspendedGetCourse(courseId: Int, semesterId: Int): Course

}
