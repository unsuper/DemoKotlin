package com.training.demokotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.training.demokotlin.databinding.ItemRcCustomBinding
import com.training.demokotlin.entity.Student
import com.training.demokotlin.repository.StudentViewModel


class StudentListAdapter(private val studentViewModel: StudentViewModel, private val clickListener: (student: Student) -> Unit) :
    ListAdapter<Student, StudentListAdapter.RcItemHolder>(STUDENT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcItemHolder = RcItemHolder(
        ItemRcCustomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RcItemHolder, position: Int) {
        val item = holder.itemBinding
        val currentItem = getItem(position)

        item.tvStudentName.text = currentItem.name
        item.tvStudentClass.text = currentItem.classname
        item.tvStudentPhone.text = currentItem.phone
        item.icDelete.setOnClickListener {
            studentViewModel.deleteStudent(currentItem)
        }
        item.root.setOnClickListener { clickListener.invoke(currentItem) }
    }

    class RcItemHolder(val itemBinding: ItemRcCustomBinding) : RecyclerView.ViewHolder(itemBinding.root)

    companion object {
        private val STUDENT_COMPARATOR = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}


