package edu.arimanius.inator.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.inator.data.entity.ProgramGroup

@Dao
interface ProgramGroupDao : InsertableDao<ProgramGroup> {
    @Query("SELECT * FROM program_groups WHERE programId = :programId")
    suspend fun getProgramGroups(programId: Int): List<ProgramGroup>
}
