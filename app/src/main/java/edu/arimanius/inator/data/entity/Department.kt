package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "departments")
data class Department (
    @PrimaryKey
    val id: Int,
    val name: String,
)
