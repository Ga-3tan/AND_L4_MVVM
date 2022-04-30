package heig.and.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private val notesViewModel: NotesViewModel by viewModels()

    /**
     * Binds the action bar buttons with view model actions
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.main_menu_sorting_button_creation_date -> { notesViewModel.sortNotesByCreationDate(); true }
            R.id.main_menu_sorting_button_ETA -> { notesViewModel.sortNotesByETA(); true }
            R.id.main_menu_sorting_button_generate -> { notesViewModel.generateANote(); true }
            R.id.main_menu_sorting_button_delete_all -> { notesViewModel.deleteAllNotes(); true }
            R.id.main_menu_large_sorting_button_creation_date -> { notesViewModel.sortNotesByCreationDate(); true }
            R.id.main_menu_large_sorting_button_ETA -> { notesViewModel.sortNotesByETA(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}