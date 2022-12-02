package edu.arimanius.inator.data.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import edu.arimanius.inator.data.InatorDatabase
import edu.arimanius.inator.data.dao.ProgramDao
import edu.arimanius.inator.data.entity.Program

class AddProgramViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val programDao: ProgramDao

    init {
        programDao = InatorDatabase.getInstance(application).programDao()
    }

    suspend fun addProgram(name: String, semesterId: Int) {
        try {
            programDao.insert(Program(0, name, semesterId))
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(
                getApplication(),
                "برنامه ای با این نام در این ترم وجود دارد",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
