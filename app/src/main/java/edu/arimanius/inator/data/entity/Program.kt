package edu.arimanius.inator.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "programs",
    indices = [
        Index(value = ["name", "semesterId"], unique = true),
        Index(value = ["semesterId"]),
    ],
    foreignKeys = [
        ForeignKey(
            entity = Semester::class,
            parentColumns = ["id"],
            childColumns = ["semesterId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class Program(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val semesterId: Int,
)
