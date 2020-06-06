package com.example.notesapp.RecyclerViewComponents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.database.Note
import com.example.notesapp.database.helper.NoteRepository

/*
this class i
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository =
        NoteRepository(application)
    private var allNotes: LiveData<List<Note>> =repository.getAllNotes()

   fun insert(note: Note){
       repository.insert(note)
   }
    fun delete(id:Int){
        repository.delete(id)
    }

    fun update(id:Int,body:String){
        repository.update(id,body)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }


}