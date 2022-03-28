package com.example.student_management.database

import android.R
import androidx.room.*
import com.example.student_management.model.Student_Entity

@Dao
interface Student {
@Query("select * from student ORDER BY id DESC")
suspend fun GetAll():List<Student_Entity>
@Query("SELECT * FROM student WHERE name LIKE :title")
suspend fun findNoteByTitle(title: String): Student_Entity
@Insert
suspend fun insert(student : Student_Entity)
@Delete
suspend fun delete(student: Student_Entity)
@Update
suspend fun update(student: Student_Entity)
}