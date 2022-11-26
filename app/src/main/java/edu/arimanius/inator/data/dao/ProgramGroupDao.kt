package edu.arimanius.inator.data.dao

import androidx.room.Dao
import edu.arimanius.inator.data.entity.ProgramGroup

@Dao
interface ProgramGroupDao: InsertableDao<ProgramGroup>
