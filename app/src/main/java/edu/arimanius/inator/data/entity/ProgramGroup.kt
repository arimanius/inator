package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "program_groups",
    primaryKeys = ["programId", "groupId", "courseId", "semesterId"],
    foreignKeys = [
        ForeignKey(
            entity = Program::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Group::class,
            parentColumns = ["groupId", "courseId", "semesterId"],
            childColumns = ["groupId", "courseId", "semesterId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["programId"]),
        Index(value = ["groupId", "courseId", "semesterId"]),
    ],
)
data class ProgramGroup(
    val programId: Int,
    val groupId: Int,
    val courseId: Int,
    val semesterId: Int,
)
