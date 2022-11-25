package edu.arimanius.inator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.arimanius.inator.data.dao.*
import edu.arimanius.inator.data.entity.*
import edu.arimanius.inator.util.EnumConverter
import edu.arimanius.inator.util.TimestampConverter
import java.time.DayOfWeek

class DayOfWeekConverter : EnumConverter<DayOfWeek>()

@Database(
    entities = [
        Course::class,
        Department::class,
        Group::class,
        GroupSchedule::class,
        Instructor::class,
        Semester::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    TimestampConverter::class,
    DayOfWeekConverter::class,
)
abstract class InatorDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun groupDao(): GroupDao
    abstract fun groupScheduleDao(): GroupScheduleDao
    abstract fun instructorDao(): InstructorDao
    abstract fun semesterDao(): SemesterDao

    companion object {
        @Volatile
        private var instance: InatorDatabase? = null

        fun getInstance(context: Context): InatorDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    InatorDatabase::class.java,
                    "inator_database"
                ).build().also { instance = it }
            }
        }
    }
}