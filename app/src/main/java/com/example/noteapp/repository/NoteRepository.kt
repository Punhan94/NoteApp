package com.example.noteapp.repository

import androidx.annotation.WorkerThread
import com.example.noteapp.model.Note
import com.example.noteapp.room.NoteDAO
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDAO) {

    val myAllNotes : Flow<List<Note>> = noteDAO.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note){
        noteDAO.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note){
        noteDAO.update(note)
    }

    @WorkerThread
    suspend fun delete(note: Note){
        noteDAO.delete(note)
    }

    @WorkerThread
    suspend fun deleteAllNotes(){
        noteDAO.deleteAllNotes()
    }
}