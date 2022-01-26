package com.example.simpletodo

//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.RemoteViews
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//A bridge that tells the recycler view how to display the data we give it!
class TaskItemAdapter(
    private val listOfItems: List<String>,
    val longClickListener: onLongClickListener, val clickListener: onClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface onLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    interface onClickListener{
        fun onItemClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // 1. get the data model based on data position
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // store references to layouts in our layoutView
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
            }

            itemView.setOnLongClickListener {
                //Log.i("toDo","long clicked on item: " + adapterPosition)
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}