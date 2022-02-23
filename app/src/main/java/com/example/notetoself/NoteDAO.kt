package com.example.notetoself

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import java.sql.Date

@Dao
interface NoteDAO {
    @Insert(onConflict = REPLACE)
    fun insert(note : NoteEntity)

    @Query("SELECT * FROM note")
    fun getAll() : List<NoteEntity>

    @Delete
    fun delete(Note : NoteEntity)

    @Update
    fun update(Note: NoteEntity)
}