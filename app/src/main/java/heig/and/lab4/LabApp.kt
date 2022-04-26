package heig.and.lab4

import android.app.Application
import heig.and.lab4.database.NoteDatabase
import heig.and.lab4.database.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LabApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val repository by lazy {
        val database = NoteDatabase.getDatabase(this)
        NoteRepository(database.noteDAO(), applicationScope)
    }
}