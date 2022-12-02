package edu.arimanius.inator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import edu.arimanius.inator.data.entity.*

@Dao
interface ProgramGroupDao : InsertableDao<ProgramGroup> {
    @Query("SELECT * FROM program_groups WHERE programId = :programId")
    suspend fun getProgramGroups(programId: Int): List<ProgramGroup>

    @Query(
        "SELECT EXISTS(" +
                "SELECT * FROM program_groups " +
                "WHERE programId = :programId " +
                "AND groupId = :groupId " +
                "AND courseId = :courseId " +
                "AND semesterId = :semesterId" +
                ")"
    )
    suspend fun groupExistsInProgram(
        programId: Int,
        groupId: Int,
        courseId: Int,
        semesterId: Int
    ): Boolean

    @Query("DELETE FROM program_groups WHERE programId = :programId AND groupId = :groupId AND courseId = :courseId AND semesterId = :semesterId")
    suspend fun delete(
        programId: Int,
        groupId: Int,
        courseId: Int,
        semesterId: Int
    )
}
