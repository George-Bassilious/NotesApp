package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_note_list_item.view.*

class Note_Recycler_Adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var notes:List<Note> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_note_list_item ,parent,false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NoteViewHolder -> {
                holder.bind(notes.get(position))
            }
        }
    }

    fun submitList(noteList: List<Note>){
        notes=noteList
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}


    class NoteViewHolder constructor(
        itemView:  View
    ):RecyclerView.ViewHolder(itemView){
        val note_preview= itemView.note_body_preview

        fun bind(note: Note){
            note_preview.setText(note.preview)
        }

    }