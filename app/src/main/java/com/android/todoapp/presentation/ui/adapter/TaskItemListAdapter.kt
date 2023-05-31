package com.android.todoapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.todoapp.data.model.domain.DomainTask
import com.android.todoapp.data.model.local.TaskStatus
import com.android.todoapp.databinding.AdapterItemTaskBinding
import com.android.todoapp.utils.*

class TaskItemListAdapter(private val callback: AdapterCallback, private var data: List<DomainTask>) : RecyclerView.Adapter<TaskItemListAdapter.TaskItemListViewHolder>() {

    fun updateData(newData: List<DomainTask>) {
        val diffResult = DiffUtil.calculateDiff(DomainTaskDiffCallback(data, newData))
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemListViewHolder {
        return TaskItemListViewHolder(
            AdapterItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskItemListViewHolder, position: Int) {
        holder.bind()
    }

    inner class TaskItemListViewHolder(private val binding: AdapterItemTaskBinding) : ViewHolder(binding.root) {
        fun bind() {
            val task = data[adapterPosition]

            val isPending = task.status == TaskStatus.PENDING || isDateInPast(task.endDate)
            val isCompleted = task.status == TaskStatus.COMPLETED

            binding.title = task.title
            if (isCompleted) {
                binding.tvTitle.setStrikeThroughAndBoldText(task.title)
            } else if (isPending) {
                binding.tvTitle.removeStrikeThroughAndBoldText()
                binding.tvTitle.setRedBoldText(task.title)
            } else {
                binding.tvTitle.removeStrikeThroughAndBoldText()
            }

            binding.time = formatDateToString(task.endDate)
            binding.isPending = isPending && isCompleted.not()
            binding.isChecked = isCompleted

            binding.cb.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    callback.onUpdateTask(task.copy(status = TaskStatus.COMPLETED))
                else
                    callback.onUpdateTask(task.copy(status = TaskStatus.NONE))
            }


            binding.ivCancel.setOnClickListener {
                callback.onItemClicked(task)
            }
            binding.executePendingBindings()
        }
    }


    private class DomainTaskDiffCallback(
        private val oldList: List<DomainTask>,
        private val newList: List<DomainTask>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldEntity = oldList[oldItemPosition]
            val newEntity = newList[newItemPosition]
            return oldEntity.id == newEntity.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldEntity = oldList[oldItemPosition]
            val newEntity = newList[newItemPosition]
            return oldEntity == newEntity
        }
    }

}

interface AdapterCallback {
    fun onItemClicked(item: DomainTask)

    fun onUpdateTask(item: DomainTask)
}