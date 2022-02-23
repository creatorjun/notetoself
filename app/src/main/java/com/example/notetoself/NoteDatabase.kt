package com.example.notetoself

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NoteEntity::class), version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteDAO() : NoteDAO

    companion object{
        var INSTANCE : NoteDatabase? = null

        fun getInstance (context : Context) : NoteDatabase? {
            if(INSTANCE == null){
                synchronized(NoteDatabase::class){
                    INSTANCE =Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java,"note").fallbackToDestructiveMigration().build()
                }
            }

            return INSTANCE
        }
    }
}