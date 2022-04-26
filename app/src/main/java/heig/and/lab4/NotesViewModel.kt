package heig.and.lab4

import androidx.lifecycle.ViewModel
import heig.and.lab4.models.Note
import heig.and.lab4.database.NoteRepository

class NotesViewModel(private val repository: NoteRepository): ViewModel() {
    val allNotes = repository.allNotes
    val countNotes = repository.notesCount

    fun generateANote() {
        val note = Note.generateRandomNote()
        repository.insertNote(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}