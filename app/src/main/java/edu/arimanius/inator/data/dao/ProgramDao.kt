package edu.arimanius.inator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.Program

@Dao
interface ProgramDao : InsertableDao<Program> {
    @Query("SELECT * FROM programs")
    fun getAllPrograms(): LiveData<List<Program>>
}
