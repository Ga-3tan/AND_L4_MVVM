package heig.and.lab4

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import heig.and.lab4.models.Note
import heig.and.lab4.database.NoteRepository

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes = repository.allNotes
    val countNotes = repository.notesCount

    fun generateANote() {
        val note = Note.generateRandomNote()
        val schedule = Note.generateRandomSchedule()
        if (schedule != null) {
            Log.println(Log.INFO, "SCHEDULE", schedule.date.toString())
        }
        repository.insertNoteAndSchedule(note, schedule)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
        repository.deleteAllSchedules()
    }
}

class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}