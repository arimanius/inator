package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "instructors",
    indices = [
        Index(value = ["fullName"], unique = true),
    ],
)
data class Instructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fullName: String,
)
