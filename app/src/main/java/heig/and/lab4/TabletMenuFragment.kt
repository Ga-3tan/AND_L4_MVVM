package heig.and.lab4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
class TabletMenuFragment : Fragment() {

    // UI Elements
    private lateinit var noteCounter: TextView
    private lateinit var generateButton: Button
    private lateinit var deleteButton: Button

    // The view model
    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as LabApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tablet_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieves UI elements
        noteCounter = view.findViewById(R.id.tablet_menu_amount)
        generateButton = view.findViewById(R.id.tablet_menu_button_generate)
        deleteButton = view.findViewById(R.id.tablet_menu_button_delete)

        // Observes the nb of notes counter
        notesViewModel.countNotes.observe(viewLifecycleOwner) { counter ->
            counter.let { noteCounter.text = it.toString() }
        }

        // Adds event listeners on buttons
        generateButton.setOnClickListener {
            notesViewModel.generateANote()
        }

        deleteButton.setOnClickListener {
            notesViewModel.deleteAllNotes()
        }
    }
}