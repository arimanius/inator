package edu.arimanius.inator.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.dao.CourseDao
import edu.arimanius.inator.data.dao.DepartmentDao
import edu.arimanius.inator.data.entity.Course
import edu.arimanius.inator.data.entity.Department
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(
    application: Application
) : AndroidViewModel(application) {
    val departments: LiveData<List<Department>>
    var courses: LiveData<List<Course>>

    private val departmentDao: DepartmentDao
    private val courseDao: CourseDao

    init {
        departmentDao = InatorDatabase.getInstance(application).departmentDao()
        courseDao = InatorDatabase.getInstance(application).courseDao()

        departments = departmentDao.getAllDepartments()
        courses = courseDao.getAllCourses()
    }

    suspend fun getCoursesByDepartment(departmentName: String): LiveData<List<Course>> {
        val department = departmentDao.getDepartmentByName(departmentName)
        val courseList = courseDao.getCoursesByDepartment(department.id)
        return courseList
    }
}