package edu.arimanius.inator.data.dao

import androidx.room.Dao
import edu.arimanius.inator.data.entity.Department

@Dao
interface DepartmentDao: InsertableDao<Department>
