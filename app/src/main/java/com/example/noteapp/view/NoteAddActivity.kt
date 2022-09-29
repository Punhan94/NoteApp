package com.example.noteapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.databinding.ActivityNoteAddBinding

class NoteAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Add Note"

        binding.cancelButton.setOnClickListener {
            Toast.makeText(this, "Nothing save", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.saveButton.setOnClickListener {
            saveNote()
        }

    }

    fun saveNote(){
        val noteTitle = binding.editTextNoteTitle.text.toString()
        val noteDescription = binding.editTextNoteDescription.text.toString()

        val intent = Intent()
        intent.putExtra("title", noteTitle)
        intent.putExtra("description", noteDescription)
        setResult(RESULT_OK,intent)
        finish()

    }


}