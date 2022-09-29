package com.example.noteapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.model.Note
import com.example.noteapp.view.MainActivity
import com.example.noteapp.view.UpdateActivity

class NoteAdapter(private val activity: MainActivity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var notes : List<Note> = ArrayList()

    class NoteViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewTitle:TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDesc:TextView = itemView.findViewById(R.id.textViewDescription)
        val noteItemCardView:CardView = itemView.findViewById(R.id.noteItemCardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate( R.layout.note_item, parent,false)

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentNote = notes[position]

        holder.apply {
            textViewTitle.text = currentNote.title
            textViewDesc.text = currentNote.description

            holder.noteItemCardView.setOnClickListener {
                val intent = Intent(activity, UpdateActivity::class.java)
                intent.putExtra("currentTitle",currentNote.title)
                intent.putExtra("currentDescription",currentNote.description)
                intent.putExtra("currentID",currentNote.id)
                activity.updateActivityResultLauncher.launch(intent)

            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNote(myNotes : List<Note>){
        this.notes = myNotes
        notifyDataSetChanged()
    }

    fun getNote(position: Int):Note{
        return notes[position]
    }

}