package com.example.notesapp


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.RecyclerViewComponents.NoteViewModel
import com.example.notesapp.RecyclerViewComponents.Note_Recycler_Adapter
import com.example.notesapp.database.Note
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private val ADD_NOTE_REQUEST = 1
    private val EDIT_NOTE_REQUEST=2
    private val DELETE_NOTE_REQUEST = 3

    private lateinit var noteViewModel: NoteViewModel

    private val noteAdapter =
        Note_Recycler_Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this,
            Observer { noteList -> noteAdapter.submitList(noteList!!) })

        updateCountText(0)

        noteAdapter.onItemClick = {
            startActivityForResult(
                Intent(this, HandleNoteActivity::class.java)
                    .putExtra("body",it.body)
                    .putExtra("noteId",it.noteId),
                EDIT_NOTE_REQUEST
            )

        }

        button_addNewNote.setOnClickListener {
            startActivityForResult(
                Intent(this, HandleNoteActivity::class.java),
                ADD_NOTE_REQUEST
            )
        }

    }


    private fun initRecyclerView(){

        val recyclerView = findViewById<RecyclerView>(R.id.list_recycler_view)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter= noteAdapter
        }

    }

     private fun updateCountText(offset:Int){
         if(offset==0){
             text_noteCount.visibility= View.INVISIBLE
         }
         else{
             text_noteCount.visibility= View.VISIBLE
             val nrOfNotes= noteAdapter.getItemCount()+offset
             val nrOfNotesText = nrOfNotes.toString() +" notes"
             text_noteCount.text= nrOfNotesText
         }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( resultCode == Activity.RESULT_OK) {
            noteAdapter.notifyDataSetChanged()


            when (requestCode) {

                ADD_NOTE_REQUEST -> {
                    val newNote = Note(
                        body = data!!.getStringExtra(HandleNoteActivity.EXTRA_BODY)
                    )
                    noteViewModel.insert(newNote)
                    updateCountText(1)
                    Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
                }

                EDIT_NOTE_REQUEST -> {
                    val responseId = data!!.getIntExtra(HandleNoteActivity.EXTRA_NOTEID, -1)
                    val responseBody = data.getStringExtra(HandleNoteActivity.EXTRA_BODY)

                    if (responseBody != null) {
                        noteViewModel.update(responseId, responseBody)
                        Toast.makeText(this, "Note Succesfully edited!", Toast.LENGTH_SHORT).show()
                    } else {
                        noteViewModel.delete(responseId)
                        updateCountText(-1)
                        Toast.makeText(this, "Note Succesfully deleted!", Toast.LENGTH_SHORT).show()
                    }
                }
                DELETE_NOTE_REQUEST -> {
                    val responseId = data!!.getIntExtra(HandleNoteActivity.EXTRA_NOTEID, -1)
                    noteViewModel.delete(responseId)
                    updateCountText(-1)
                    Toast.makeText(this, "Note Succesfully deleted!", Toast.LENGTH_SHORT).show()

                }
                else -> {
                    Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
                }
            }


        }


    }
}

