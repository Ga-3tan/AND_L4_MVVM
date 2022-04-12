package heig.and.lab4.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import heig.and.lab4.models.Note
import heig.and.lab4.models.Schedule

@Database(
    entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDAO(): NoteDAO

    companion object {
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notedatabase.db"
                ).build()
                INSTANCE!!
            }
        }
    }
}

// TODO REPOSITORY (slide 42)