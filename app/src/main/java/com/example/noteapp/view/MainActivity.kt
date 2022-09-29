package com.example.noteapp.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.NoteApplication
import com.example.noteapp.R
import com.example.noteapp.adapters.NoteAdapter
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel
import com.example.noteapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel :NoteViewModel
    private lateinit var addActivityResultLauncher : ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.recyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this)
        recyclerView.adapter = noteAdapter

        registerActivityResult()

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.myAllNotes.observe(this, Observer { notes->
            noteAdapter.setNote(notes)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)

    }

    fun registerActivityResult(){
        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultAddNote->
                val resultCode = resultAddNote.resultCode
                val data = resultAddNote.data

                if (resultCode == RESULT_OK && data!= null){
                    val noteTitle = data.getStringExtra("title").toString()
                    val noteDescription = data.getStringExtra("description").toString()
                    val note = Note(noteTitle,noteDescription)
                    noteViewModel.insert(note)
                }

            }
            )
        updateActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultUpdateNote->
                val resultCode = resultUpdateNote.resultCode
                val data = resultUpdateNote.data

                if (resultCode == RESULT_OK && data != null){
                    val updatedTitle = data.getStringExtra("updatedTitle").toString()
                    val updatedDescription = data.getStringExtra("updatedDescription").toString()
                    val noteID = data.getIntExtra("noteID", -1)

                    val newNote = Note(updatedTitle,updatedDescription)
                    newNote.id = noteID

                    noteViewModel.update(newNote)

                }

            }
        )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.item_add_note->{
                val intent = Intent(this, NoteAddActivity::class.java)
                addActivityResultLauncher.launch(intent)
            }

            R.id.item_delete_all_note->{
                showDialogMessage()
            }
        }
        return true
    }

    private fun showDialogMessage(){
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Notes")
        dialogMessage.setMessage("If click Yes all notes will delete," +
                " if you want to delete specific note, please swipe left or right")
        dialogMessage.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            noteViewModel.deleteAllNotes()
        })
        dialogMessage.create().show()
    }
}