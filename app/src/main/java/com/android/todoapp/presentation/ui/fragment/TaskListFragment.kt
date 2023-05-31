package com.android.todoapp.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.todoapp.R
import com.android.todoapp.ToDoApplication
import com.android.todoapp.data.model.domain.DomainTask
import com.android.todoapp.databinding.FragmentTaskListBinding
import com.android.todoapp.presentation.di.DaggerFeatureComponent
import com.android.todoapp.presentation.ui.adapter.AdapterCallback
import com.android.todoapp.presentation.ui.adapter.TaskItemListAdapter
import com.android.todoapp.presentation.ui.dialog.WarningDialogFragment
import com.android.todoapp.presentation.viewmodel.TaskViewModel
import com.android.todoapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class TaskListFragment : Fragment(), View.OnClickListener, AdapterCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
    }

    private var navController: NavController? = null

    private var adapter: TaskItemListAdapter = TaskItemListAdapter(this, emptyList())

    private lateinit var binding: FragmentTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFeatureComponent
            .factory()
            .create((requireActivity().application as ToDoApplication).appComponent)
            .inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.rvTaskList.adapter = adapter
        binding.rvTaskList.layoutManager = LinearLayoutManager(requireContext())

        binding.fabAddTask.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        taskViewModel.taskListLiveData.observe(viewLifecycleOwner) { data ->
            adapter.updateData(data)
            setEmptyView(data.isEmpty())
        }

    }

    private fun setEmptyView(enable: Boolean) {
        binding.rvTaskList.visibility = if (enable) View.GONE else View.VISIBLE
        binding.tvEmptyView.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        when (v) {
            binding.fabAddTask -> {
                navController?.let { it.navigate(R.id.action_taskListFragment_to_addTaskFragment) }
            }
        }
    }

    override fun onItemClicked(item: DomainTask) {
        WarningDialogFragment { taskViewModel.removeTask(item) }
            .show(parentFragmentManager, "WarningDialog")
    }

    override fun onUpdateTask(item: DomainTask) {
        taskViewModel.upsertTask(item)
    }
}