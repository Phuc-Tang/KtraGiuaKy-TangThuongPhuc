package com.example.student_management.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.student_management.model.Student_Entity

@Database(entities = [Student_Entity::class], version=1)
abstract class DB_Student: RoomDatabase(){
    abstract fun studentDao():Student
    companion object{
        private var INSTANCE: DB_Student?= null
        private val DB_NAME = "student_db"
        fun getDatabase(context: Context): DB_Student {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DB_Student::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}