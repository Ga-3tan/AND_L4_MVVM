package heig.and.lab4.database

import heig.and.lab4.models.Note
import heig.and.lab4.models.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
class NoteRepository(private val noteDAO: NoteDAO, private val scope: CoroutineScope) {

    val allNotes = noteDAO.getAllNotes()
    val notesCount = noteDAO.getNotesCount()
    val allNotesETA = noteDAO.getAllNotesByETA()
    val allNotesCreationDate = noteDAO.getALlNotesByCreationDate()

    /**
     * Inserts a list of notes
     */
    fun insertNotes(vararg notes: Note) {
        scope.launch(Dispatchers.IO) {
            noteDAO.insertAll(*notes)
        }
    }

    /**
     * Inserts a note and its associated schedule
     */
    fun insertNoteAndSchedule(note: Note, schedule: Schedule?) {
        scope.launch(Dispatchers.IO) {
            val id = noteDAO.insert(note)
            schedule?.apply {
                ownerId = id
                noteDAO.insert(this)
            }
        }
    }

    /**
     * Inserts a schedule
     */
    fun insertSchedule(schedule: Schedule) {
        scope.launch(Dispatchers.IO) {
            noteDAO.insert(schedule)
        }
    }

    /**
     * Inserts a list of schedules
     */
    fun insertSchedules(vararg schedules: Schedule) {
        scope.launch(Dispatchers.IO) {
            noteDAO.insertAll(*schedules)
        }
    }

    /**
     * Deletes all the notes
     */
    fun deleteAllNotes() {
        scope.launch {
            noteDAO.deleteAllNotes()
        }
    }

    /**
     * Deletes all the schedules
     */
    fun deleteAllSchedules() {
        scope.launch {
            noteDAO.deleteAllSchedule()
        }
    }
}