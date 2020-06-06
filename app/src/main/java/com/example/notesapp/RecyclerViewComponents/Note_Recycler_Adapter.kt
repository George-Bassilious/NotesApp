package com.example.notesapp.RecyclerViewComponents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.database.Note

class Note_Recycler_Adapter:RecyclerView.Adapter<Note_Recycler_Adapter.NoteHolder>(){

    private var notes:List<Note> = ArrayList()
    var onItemClick: ((Note)->Unit) ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {

        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.layout_notelistitem,parent,false)
        return NoteHolder(itemView)

    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]
        holder.textViewBody.text =currentNote.body
    }

    fun submitList(notes: List<Note>){
        this.notes= notes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return notes.size
    }


    inner class NoteHolder (itemView:  View):RecyclerView.ViewHolder(itemView){
        var textViewBody: TextView = itemView.findViewById(R.id.note_body_preview)

        init{
            itemView.setOnClickListener {
                onItemClick?.invoke(notes[adapterPosition])
            }
        }

    }
}