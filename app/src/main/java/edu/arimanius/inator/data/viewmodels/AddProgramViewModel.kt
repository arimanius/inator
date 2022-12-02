package edu.arimanius.inator.data.viewmodels

import android.app.Application
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
        programDao.insert(Program(0, name, semesterId))
    }
}
