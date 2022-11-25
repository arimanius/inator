package edu.arimanius.inator.data.dao

import androidx.room.Dao
import edu.arimanius.inator.data.entity.Semester

@Dao
interface SemesterDao: InsertableDao<Semester>
