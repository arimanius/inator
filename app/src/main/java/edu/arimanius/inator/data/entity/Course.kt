package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "courses",
    primaryKeys = ["courseId", "semesterId"],
    foreignKeys = [
        ForeignKey(
            entity = Semester::class,
            parentColumns = ["id"],
            childColumns = ["semesterId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Department::class,
            parentColumns = ["id"],
            childColumns = ["departmentId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class Course(
    val courseId: Int,
    val semesterId: Int,
    val departmentId: Int,
    val name: String,
    val units: Int,
)
