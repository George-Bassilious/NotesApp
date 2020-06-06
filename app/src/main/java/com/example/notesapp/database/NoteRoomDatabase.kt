package com.example.notesapp.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Note::class], version = 1,exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteRoomDatabase? = null

        fun getInstance(context: Context): NoteRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteRoomDatabase::class) {
                    INSTANCE = databaseBuilder(
                        context.getApplicationContext(),
                        NoteRoomDatabase::class.java, "notes_database.db"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return INSTANCE
        }

        private val roomCallback = object: RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(
                    INSTANCE
                ).execute()
            }
        }

    }
}
class PopulateDbAsyncTask(db: NoteRoomDatabase?): AsyncTask<Unit, Unit, Unit>(){
    private val noteDao = db?.noteDao()

    override fun doInBackground(vararg p0: Unit?){
        noteDao?.insert(Note(body = "testing"))
        noteDao?.insert(Note(body = "trying"))
        noteDao?.insert(Note(body = "believing"))
    }
}


