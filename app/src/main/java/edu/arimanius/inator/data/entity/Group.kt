package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "groups",
    primaryKeys = ["groupId", "courseId", "semesterId"],
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["courseId", "semesterId"],
            childColumns = ["courseId", "semesterId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Instructor::class,
            parentColumns = ["id"],
            childColumns = ["instructorId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["courseId", "semesterId"]),
        Index(value = ["instructorId"]),
    ],
)
data class Group(
    val groupId: Int,
    val courseId: Int,
    val semesterId: Int,
    val instructorId: Int,
    val capacity: Int,
    val info: String,
    val finalExamDate: String?,
)
