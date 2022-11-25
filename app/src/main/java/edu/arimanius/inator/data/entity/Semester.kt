package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semesters")
data class Semester (
    @PrimaryKey
    val id: Int,
)
