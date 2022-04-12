package heig.and.lab4.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import heig.and.lab4.models.Note

@Dao
interface NoteDAO {

    @Insert
    fun insert(note: Note): Long

    @Query("DELETE FROM Note")
    fun deleteAllNotes()
}