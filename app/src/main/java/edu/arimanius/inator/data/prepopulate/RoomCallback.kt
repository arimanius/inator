package edu.arimanius.inator.data.prepopulate

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.arimanius.inator.R
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader
import java.time.DayOfWeek

class RoomCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val database = InatorDatabase.getInstance(context)

        CoroutineScope(Dispatchers.IO).launch {
            prepopulate(database, 14011)
        }
    }

    private suspend fun prepopulate(db: InatorDatabase, semester: Int) {
        val semesterDao = db.semesterDao()
        semesterDao.insert(Semester(semester))

        val programDao = db.programDao()
        programDao.insert(Program(0, "پیش فرض", semester))

        val departments = listOf(
            Triple(R.raw.computer, "مهندسی کامپویتر", 38),
            Triple(R.raw.math, "علوم ریاضی", 3),
            Triple(R.raw.physics, "فیزیک", 5),
        )

        for (department in departments) {
            prepopulateDepartment(
                db,
                semester,
                department.first,
                department.third,
                department.second
            )
        }
    }

    private suspend fun prepopulateDepartment(
        db: InatorDatabase,
        semester: Int,
        departmentFileId: Int,
        departmentId: Int,
        departmentName: String,
    ) {
        val departmentDao = db.departmentDao()
        val courseDao = db.courseDao()
        val groupDao = db.groupDao()
        val instructorDao = db.instructorDao()
        val groupScheduleDao = db.groupScheduleDao()

        departmentDao.insert(Department(departmentId, departmentName))

        val courses = loadJSONArray(context, departmentFileId)
        if (courses != null) {
            for (i in 0 until courses.length()) {
                val course = courses.getJSONObject(i)

                val info = course.getString("info")
                val courseId = course.getString("course_id")
                val courseNumber = course.getString("course_number").toInt()
                val name = course.getString("name")
                val units = course.getInt("units")
                val capacity = course.getInt("capacity")
                val instructor = course.getString("instructor")
                val classTimes = JSONArray(course.getString("class_times"))
                val id = course.getString("id")
                val examTime = course.getString("exam_time")

                val groupId = courseId.split("-")[1].toInt()

                if (courseNumber == -1) continue

                try {
                    courseDao.insert(
                        Course(
                            courseId = courseNumber,
                            semesterId = semester,
                            departmentId = departmentId,
                            name = name,
                            units = units,
                        )
                    )
                } catch (_: SQLiteConstraintException) {
                } catch (e: Exception) {
                    Log.e("RoomCallback", "Error inserting course $courseId", e)
                    continue
                }

                try {
                    instructorDao.insert(Instructor(
                        id = 0,
                        fullName = instructor,
                    ))
                } catch (_: SQLiteConstraintException) {
                } catch (e: Exception) {
                    Log.e("RoomCallback", "Error inserting instructor $instructor", e)
                    continue
                }

                val instructorId = instructorDao.getByFullName(instructor)!!.id

                groupDao.insert(
                    Group(
                        groupId = groupId,
                        courseId = courseNumber,
                        semesterId = semester,
                        instructorId = instructorId,
                        capacity = capacity,
                        info = info,
                        finalExamDate = examTime
                    )
                )

                for (i in 0 until classTimes.length()) {
                    val classTime = classTimes.getJSONObject(i)
                    val day = classTime.getInt("day")
                    val start = classTime.getDouble("start")
                    val end = classTime.getDouble("end")

                    groupScheduleDao.insert(GroupSchedule(
                        id = 0,
                        groupId = groupId,
                        courseId = courseNumber,
                        semesterId = semester,
                        dayOfWeek = DayOfWeek.of((day + 5) % 7 + 1),
                        start = start,
                        end = end,
                    ))
                }
            }
        }
    }

    private fun loadJSONArray(context: Context, id: Int): JSONArray? {
        val inputStream = context.resources.openRawResource(id)
        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}