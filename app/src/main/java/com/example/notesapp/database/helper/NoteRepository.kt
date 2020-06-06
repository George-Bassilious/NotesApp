package com.example.notesapp.database.helper

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.notesapp.database.Note
import com.example.notesapp.database.NoteDao
import com.example.notesapp.database.NoteRoomDatabase


/*
This class helps connect the NoteDAO (database functions) with the recyclerviewModel using the functions
 from this class to invocate database functions
 */
class NoteRepository(application:Application) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

    init{
        val database: NoteRoomDatabase = NoteRoomDatabase.getInstance(
            application.applicationContext
        )!!
        noteDao= database.noteDao()
        allNotes= noteDao.getNotes()
    }

    fun getNoteById(id:Int): Note {
        return noteDao.getNoteById(id)
    }

    fun insert(note: Note){
        val insertNoteAsyncTask= InsertNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun delete(id:Int){
        val deleteNoteAsyncTask=
            DeleteNoteAsyncTask(
                noteDao
            ).execute(id)
    }

    fun update(id:Int,body:String){
        val updateNoteAsyncTask=
            UpdateNoteAsyncTask(
                noteDao
            ).execute(id.toString(),body)
    }
    fun getAllNotes():LiveData<List<Note>>{
        return allNotes
    }


    private class UpdateNoteAsyncTask(val noteDao: NoteDao) :AsyncTask<String, Unit, Unit>(){
        override fun doInBackground(vararg params: String) {
            val id:Int=params[0].toInt()
            noteDao.updateNoteBody(id,params[1])
        }
    }

    private class InsertNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>(){

        override fun doInBackground(vararg p0: Note?) {
            noteDao.insert(p0[0]!!)
        }
    }

    private class DeleteNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Int, Unit, Unit>(){

        override fun doInBackground(vararg p0: Int?) {
            val n: Note = noteDao.getNoteById(p0[0]!!)
            noteDao.deleteNote(n)
        }


    }







}