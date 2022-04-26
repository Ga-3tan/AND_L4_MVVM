package heig.and.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    val notesViewModel: NotesViewModel by viewModels()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.main_menu_sorting_button_creation_date -> { println("PRESSED: Creation date"); true }
            R.id.main_menu_sorting_button_ETA -> { println("PRESSED: ETA"); true }
            R.id.main_menu_sorting_button_generate -> { notesViewModel.generateANote(); true }
            R.id.main_menu_sorting_button_delete_all -> { notesViewModel.deleteAllNotes(); true }
            R.id.main_menu_large_sorting_button_creation_date -> { println("PRESSED: Large creation date"); true }
            R.id.main_menu_large_sorting_button_ETA -> { println("PRESSED: Large ETA"); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}