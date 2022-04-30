package heig.and.lab4

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import heig.and.lab4.models.Note
import heig.and.lab4.database.NoteRepository

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
class NotesViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _allNotesOption: MutableLiveData<Int> by lazy{MutableLiveData(-1)}

    // Switches the live data source when a sort option is chosen
    val allNotes = _allNotesOption.switchMap { sortOption ->
        when(sortOption){
            0 -> repository.allNotesCreationDate
            1 -> repository.allNotesETA
            else -> repository.allNotes
        }
    }

    val countNotes = repository.notesCount

    /**
     * Generates a new note
     */
    fun generateANote() {
        val note = Note.generateRandomNote()
        val schedule = Note.generateRandomSchedule()
        if (schedule != null) {
            Log.println(Log.INFO, "SCHEDULE", schedule.date.toString())
        }
        repository.insertNoteAndSchedule(note, schedule)
    }

    /**
     * Deletes all the stored notes
     */
    fun deleteAllNotes() {
        repository.deleteAllNotes()
        repository.deleteAllSchedules()
    }

    /**
     * Sorts the notes by their creation date
     */
    fun sortNotesByCreationDate() {
        _allNotesOption.value = 0
    }

    /**
     * Sorts the notes by their schedule due time (only displays notes that have schedules)
     */
    fun sortNotesByETA() {
        _allNotesOption.value = 1
    }
}

class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}