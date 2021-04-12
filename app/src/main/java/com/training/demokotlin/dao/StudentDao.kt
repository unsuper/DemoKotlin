package com.training.demokotlin.dao

import androidx.room.*
import com.training.demokotlin.entity.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Query("SELECT * FROM student_table ORDER BY id ASC")
    fun getAllStudent(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: Student)

    @Update(entity = Student::class)
    suspend fun updateStudent(student: Student) : Int

    @Delete
    suspend fun deleteOne(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()
}