package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.util.Date

@Entity(
    tableName = "group_schedules",
    foreignKeys = [
        ForeignKey(
            entity = Group::class,
            parentColumns = ["groupId", "courseId", "semesterId"],
            childColumns = ["groupId", "courseId", "semesterId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["groupId", "courseId", "semesterId"]),
    ],
)
data class GroupSchedule (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val groupId: Int,
    val courseId: Int,
    val semesterId: Int,
    val dayOfWeek: DayOfWeek,
    val start: Double,
    val end: Double,
)
