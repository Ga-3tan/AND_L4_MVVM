package heig.and.lab4

import android.app.Application
import android.content.res.Resources
import heig.and.lab4.database.NoteDatabase
import heig.and.lab4.database.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
class LabApp : Application() {

    companion object {
        lateinit var instance: Application
        lateinit var resourses: Resources
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
    }

    private val applicationScope = CoroutineScope(SupervisorJob())

    val repository by lazy {
        val database = NoteDatabase.getDatabase(this)
        NoteRepository(database.noteDAO(), applicationScope)
    }
}