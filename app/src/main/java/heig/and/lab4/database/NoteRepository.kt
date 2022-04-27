package heig.and.lab4.database

import heig.and.lab4.models.Note
import heig.and.lab4.models.Schedule
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

    fun insertNoteAndSchedule(note: Note, schedule: Schedule?) {
        scope.launch(Dispatchers.IO) {
            val id = noteDAO.insert(note)
            schedule?.apply {
                ownerId = id
                noteDAO.insert(this)
            }
        }
    }

    fun insertSchedule(schedule: Schedule) {
        scope.launch(Dispatchers.IO) {
            noteDAO.insert(schedule)
        }
    }

    fun insertSchedules(vararg schedules: Schedule) {
        scope.launch(Dispatchers.IO) {
            noteDAO.insertAll(*schedules)
        }
    }

    fun deleteAllNotes() {
        scope.launch {
            noteDAO.deleteAllNotes()
        }
    }

    fun deleteAllSchedules() {
        scope.launch {
            noteDAO.deleteAllSchedule()
        }
    }
}