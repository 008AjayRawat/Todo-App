package com.android.todoapp.presentation.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.todoapp.ToDoApplication
import com.android.todoapp.data.model.domain.DomainTask
import com.android.todoapp.data.model.local.TaskStatus
import com.android.todoapp.databinding.FragmentAddTaskBinding
import com.android.todoapp.presentation.di.DaggerFeatureComponent
import com.android.todoapp.presentation.viewmodel.TaskViewModel
import com.android.todoapp.presentation.viewmodel.ViewModelFactory
import com.android.todoapp.utils.*
import java.util.*
import javax.inject.Inject


class AddTaskFragment : Fragment(), View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
    }

    private lateinit var binding: FragmentAddTaskBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFeatureComponent
            .factory()
            .create((requireActivity().application as ToDoApplication).appComponent)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        setOnClickListener()
        return binding.root
    }

    private fun setOnClickListener() {
        binding.edtTxtTime.setOnClickListener(this)
        binding.edtTxtAmPm.setOnClickListener(this)
        binding.edtTxtDate.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.edtTxtTime -> {
                getTimePickerDialog(requireContext(), this).show()
            }
            binding.edtTxtAmPm -> {
                val currentTag = binding.edtTxtAmPm.tag
                (if (currentTag == AM) PM else AM).let {
                    binding.edtTxtAmPm.setText(it)
                    binding.edtTxtAmPm.tag = it
                }
            }
            binding.edtTxtDate -> {
                getDatePickerDialog(requireContext(), Calendar.getInstance(), this).show()
            }
            binding.btnAdd -> {
                insertTask()
            }
            binding.btnCancel -> {
                closeFragment()
            }
        }
    }

    private fun insertTask() {
        val title = binding.edtTxtTitle.text.toString()
        val time = binding.edtTxtTime.text.toString()
        val date = binding.edtTxtDate.text.toString()
        val duration = binding.edtTxtAmPm.text.toString()


        if (title.isNullOrBlank() || time.isNullOrBlank() || date.isNullOrBlank() || duration.isNullOrBlank()) {
            return
        }

        //Save Task
        taskViewModel.upsertTask(
            DomainTask(
                title = title,
                endDate = mergeDateTime(convertStringToDate(date)!!, convertTimePairToDate(Pair(time, duration))!!),
                status = TaskStatus.NONE
            )
        )

        closeFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        convert24To12("$hourOfDay:$minute")?.let { convertedTime ->
            val (time, amPm) = convertedTime
            binding.edtTxtTime.setText(time)
            binding.edtTxtAmPm.setText(if (amPm.equals(AM, true)) AM else PM)
        }
    }

    private fun closeFragment() {
        //Close Fragment
        findNavController().navigateUp()
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        binding.edtTxtDate.setText("$dayOfMonth-${month + 1}-$year")
    }


}