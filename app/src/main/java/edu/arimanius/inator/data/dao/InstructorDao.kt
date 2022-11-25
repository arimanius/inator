package edu.arimanius.inator.data.dao

import androidx.room.Dao
import edu.arimanius.inator.data.entity.Instructor

@Dao
interface InstructorDao: InsertableDao<Instructor>
