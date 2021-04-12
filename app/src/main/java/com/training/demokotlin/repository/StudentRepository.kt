package com.training.demokotlin.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.training.demokotlin.dao.StudentDao
import com.training.demokotlin.entity.Student
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class StudentRepository(private val studentStudentDao: StudentDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allStudent: Flow<List<Student>> = studentStudentDao.getAllStudent()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(student: Student) {
        studentStudentDao.insertStudent(student)
    }

    @WorkerThread
    suspend fun update(student: Student) {
        studentStudentDao.updateStudent(student)
        Log.d("TAG", "update: $student")
    }

    @WorkerThread
    suspend fun deleteOne(student: Student) {
        studentStudentDao.deleteOne(student)
    }
}