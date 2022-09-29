package com.example.noteapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    var currentID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Update Note"

        getAndSetData()

        binding.saveButtonUpdate.setOnClickListener {
            updateNote()
        }

        binding.cancelButtonUpdate.setOnClickListener {
            Toast.makeText(this, "Nothing updated", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun updateNote(){
        val updatedTitle = binding.editTextNoteTitleUpdate.text.toString()
        val updatedDescription = binding.editTextNoteDescriptionUpdate.text.toString()

        val intent = Intent()
        intent.putExtra("updatedTitle",updatedTitle)
        intent.putExtra("updatedDescription",updatedDescription)

        if (currentID != -1){
            intent.putExtra("noteID",currentID)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun getAndSetData(){
        val currentTitle = intent.getStringExtra("currentTitle")
        val currentDescription = intent.getStringExtra("currentDescription")
        currentID = intent.getIntExtra("currentID",-1)

        binding.editTextNoteTitleUpdate.setText(currentTitle.toString())
        binding.editTextNoteDescriptionUpdate.setText(currentDescription.toString())
    }



}