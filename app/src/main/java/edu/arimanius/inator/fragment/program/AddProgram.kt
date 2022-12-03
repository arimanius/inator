package edu.arimanius.inator.fragment.program

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.arimanius.inator.MainActivity
import edu.arimanius.inator.R
import edu.arimanius.inator.data.viewmodels.AddProgramViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddProgram : Fragment() {

    private lateinit var addProgramViewModel: AddProgramViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_program, container, false)

        addProgramViewModel = ViewModelProvider(activity as MainActivity)[AddProgramViewModel::class.java]

        view.findViewById<Button>(R.id.btn_add_program).setOnClickListener {
            insertProgram()
        }

        view.findViewById<Button>(R.id.btn_cancel_add_program).setOnClickListener {
            findNavController().navigate(R.id.action_addProgram_to_weeklySchedule)
        }

        return view
    }

    private fun insertProgram() {
        val name = view?.findViewById<EditText>(R.id.et_program_name)?.text.toString()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                addProgramViewModel.addProgram(name, 14011)
                findNavController().navigate(R.id.action_addProgram_to_weeklySchedule)
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(
                    requireContext(),
                    "برنامه ای با این نام در این ترم وجود دارد",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
