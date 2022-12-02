package edu.arimanius.inator.data.dao

import androidx.room.Dao
import edu.arimanius.inator.data.entity.Program

@Dao
interface ProgramDao : InsertableDao<Program>
