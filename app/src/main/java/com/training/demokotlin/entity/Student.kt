package com.training.demokotlin.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true) var id: Int? = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "classname") var classname: String,
    @ColumnInfo(name = "phone") var phone: String,
)