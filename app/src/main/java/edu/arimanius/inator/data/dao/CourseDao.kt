package edu.arimanius.inator.data.dao

import androidx.room.Dao
import edu.arimanius.inator.data.entity.Course

@Dao
interface CourseDao: InsertableDao<Course>
