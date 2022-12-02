package edu.arimanius.inator.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import edu.arimanius.inator.data.dao.CourseDao
import edu.arimanius.inator.data.entity.Course

class CourseViewModel(
    application: Application
): AndroidViewModel(application) {
    val allCourses: LiveData<List<Course>>
    private val courseDao: CourseDao

    init {
        courseDao = InatorDatabase.getInstance(application).courseDao()
        allCourses = courseDao.getAllCourses()
    }
}