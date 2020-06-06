package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * from notes_table" )
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * from notes_table where noteId==:id")
    fun getNoteById(id: Int): Note

    @Query("UPDATE notes_table SET body=:newBody where noteId==:id")
    fun updateNoteBody(id:Int,newBody:String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun deleteNote(note: Note)


}