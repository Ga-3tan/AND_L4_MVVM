package heig.and.lab4.database

import heig.and.lab4.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepository(private val noteDAO: NoteDAO, private val scope: CoroutineScope) {

    val allNotes = noteDAO.getALlNotes()
    val notesCount = noteDAO.getNotesCount()

    fun insertNotes(vararg notes: Note) {
        scope.launch(Dispatchers.IO) {
            noteDAO.insertAll(*notes)
        }
    }

    fun insertNote(note: Note) {
        scope.launch(Dispatchers.IO) {
            noteDAO.insert(note)
        }
    }

    fun deleteAllNotes() {
        scope.launch {
            noteDAO.deleteAllNotes()
        }
    }
}