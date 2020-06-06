package com.example.notesapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
class Note(
    @PrimaryKey(autoGenerate = true) val noteId:Int =0,
    @ColumnInfo(name = "body") var body: String
)



