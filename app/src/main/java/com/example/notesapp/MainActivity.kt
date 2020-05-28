package com.example.notesapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteAdapter: Note_Recycler_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        addDataSet()

    }




    private fun addDataSet(){
        val data = dataSource.createDataSet()
        noteAdapter.submitList(data)
    }


    private fun initRecyclerView(){


        list_recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
        noteAdapter= Note_Recycler_Adapter()
        list_recycler_view.adapter= noteAdapter

        /*list_recycler_view.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            noteAdapter= Note_Recycler_Adapter()
            adapter= noteAdapter


        }
        */

    }



}
