package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.onLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1. remove the item from the list
                listOfTasks.removeAt(position)
                //2. notify the adapter that our data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }
        //lets detect when the user clicks on the add button!
//        findViewById<Button>(R.id.button).setOnClickListener{
//            // code in here is going to be executed when a user clicks on a button
//            Log.i("Ujwal","User clicked on the ADD Button!!!")
//        }


//        listOfTasks.add("Do laundry")
//        listOfTasks.add("go for a walk")

        loadItems()
        //look up recycler view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        //setup the button and input filed so the user can enter a task and add it to the list.

        //get the reference to a button

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // and then set an onlick listener to it.
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. grab the text the user has inputted into @id/addTaskField
            val userinputtedTask = inputTextField.text.toString()

            //2. add the string to our list of tasks: listOfTasks
            listOfTasks.add(userinputtedTask)

            // notify the adapter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //3. Reset text field

            inputTextField.setText("")

            saveItems()
        }
    }


    //1. save the data the user has inputted
    //   by writing and reading from a file


    //2. create a method to get the file we need
    fun getDataFile() : File {

        // every line is going to represent a specific task in our list of tasks
        return File(filesDir,"data.txt")
    }
    // load the items by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }

    // save items by writing them into the data file
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch (ioException: IOException){
             ioException.printStackTrace()
        }

    }
}