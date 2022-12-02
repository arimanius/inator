package edu.arimanius.inator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.Department

@Dao
interface DepartmentDao: InsertableDao<Department> {
    @Query("SELECT * FROM departments")
    fun getAllDepartments(): LiveData<List<Department>>

    @Query("SELECT * FROM departments WHERE name = :name")
    suspend fun getDepartmentByName(name: String): Department
}
