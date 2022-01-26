package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    val KEY_ITEM_TEXT: String = "item_text"
    val KEY_ITEM_POSITION: String = "item_position"
    val EDIT_TEXT_CODE: Int = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityResultLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == EDIT_TEXT_CODE) {
                // There are no request codes
                val data: Intent = result.data!!

                val itemText = data.getStringExtra(KEY_ITEM_TEXT)
                val pos = data.extras?.getInt(KEY_ITEM_POSITION)
                Log.i("position","$itemText")

                //listOfTasks[pos!!] = itemText.toString()
                listOfTasks.set(pos!!, itemText!!)

                adapter.notifyDataSetChanged()
                saveItems()

            }
            else{
                Log.w("MainActivity", "Unknown Call to registerForActivityResult123")
            }
        }

        val onClickListener = object : TaskItemAdapter.onClickListener{
            override fun onItemClicked(position: Int) {
                //Log.i("single", "single Click is working")
                val i = Intent(this@MainActivity, EditActivity::class.java)


                //pass the data being edited
                i.putExtra(KEY_ITEM_TEXT, listOfTasks.get(position))
                i.putExtra(KEY_ITEM_POSITION, position.toString())

                activityResultLaunch.launch(i)
                //display the activity

            }

        }



        val onLongClickListener = object : TaskItemAdapter.onLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1. remove the item from the list
                listOfTasks.removeAt(position)
                //2. notify the adapter that our data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }



        loadItems()
        //look up recycler view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        //setup the button and input filed so the user can enter a task and add it to the list.








        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //get the reference to a button
        // and then set an onclick listener to it.
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

//    "Depricated"
//    val REQUEST_CODE: Int = 20
//
//    fun onClick(view: View) {
//        val i = Intent(this@MainActivity, EditActivity::class.java)
//        i.putExtra("mode", 2) // pass arbitrary data to launched activity
//        startActivityForResult(i, REQUEST_CODE)
//    }

    //val getEditedText = registerForActivityResult()
}