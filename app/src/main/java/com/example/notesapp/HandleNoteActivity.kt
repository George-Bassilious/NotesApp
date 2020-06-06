package com.example.notesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_handlenote.*



class HandleNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BODY = "EXTRA_BODY"
        const val EXTRA_NOTEID = "EXTRA_NOTEID"
    }

    var noteId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handlenote)


        noteId=intent.getIntExtra("noteId",-1)
        if(noteId!=-1){
            val  data:String = intent.getStringExtra("body")
            textEditor_text.setText(data)
            handleNoteScreenInfo.setText(R.string.text_editNoteInfo)

            button_delete.visibility= View.VISIBLE
            button_cancel.visibility= View.INVISIBLE

        }
        else{
                 button_delete.visibility= View.INVISIBLE
                 button_cancel.visibility= View.VISIBLE
        }

        button_save.setOnClickListener {
            Log.d("teag","save")
            saveNote()
        }


        button_delete.setOnClickListener{
            deleteNote()
        }

        button_cancel.setOnClickListener{
            finish()
        }


    }

    private fun saveNote() {
        if (textEditor_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not add an empty note!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_BODY, textEditor_text.text.toString())
            putExtra(EXTRA_NOTEID, noteId)

        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun deleteNote(){
        if(noteId!=-1) {
            val data = Intent().apply {
                putExtra(EXTRA_NOTEID, noteId)
            }

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}



