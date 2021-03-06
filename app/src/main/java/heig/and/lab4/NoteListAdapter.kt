package heig.and.lab4

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import heig.and.lab4.models.NoteAndSchedule
import heig.and.lab4.models.Type
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
class NoteListAdapter(_items: List<NoteAndSchedule> = listOf()) :
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    // Items in the list
    private var items = listOf<NoteAndSchedule>()
        set(value) {
            // Computes the differences between old and new list
            val diffCallback = NoteDiffCallback(items, value)
            val diffItems = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffItems.dispatchUpdatesTo(this)
        }

    init {
        items = _items
    }

    fun setNotes(newNotes: List<NoteAndSchedule>) {
        items = newNotes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    /**
     * Manages the cells inside the RecyclerView
     */
    inner class ViewHolder(_view: View) : RecyclerView.ViewHolder(_view) {
        private var view: View = _view

        // UI elements
        private val noteImage = view.findViewById<ImageView>(R.id.note_list_item_icon)
        private val noteTitle = view.findViewById<TextView>(R.id.note_list_item_title)
        private val noteContent = view.findViewById<TextView>(R.id.note_list_item_description)
        private val noteClockImage = view.findViewById<ImageView>(R.id.note_list_item_due_icon)
        private val noteSchedule = view.findViewById<TextView>(R.id.note_list_item_due_state)

        fun bind(noteAndSchedule: NoteAndSchedule) {
            // Set up the main part
            when (noteAndSchedule.note.type) {
                Type.FAMILY -> noteImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        view.context,
                        R.drawable.family
                    )
                )
                Type.SHOPPING -> noteImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        view.context,
                        R.drawable.shopping
                    )
                )
                Type.TODO -> noteImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        view.context,
                        R.drawable.todo
                    )
                )
                Type.WORK -> noteImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        view.context,
                        R.drawable.work
                    )
                )
                Type.NONE -> noteImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        view.context,
                        R.drawable.note
                    )
                )
            }

            noteTitle.text = noteAndSchedule.note.title
            noteContent.text = noteAndSchedule.note.text

            // Display the schedule part
            if (noteAndSchedule.schedule != null) {
                noteClockImage.visibility = View.VISIBLE
                noteSchedule.visibility = View.VISIBLE

                val currentDate = Calendar.getInstance().time

                val diff: Long = currentDate.time - noteAndSchedule.schedule.date.time.time
                val daysDiff = TimeUnit.MILLISECONDS.toDays(-diff)

                when {
                    diff > 0 -> {
                        noteClockImage.setColorFilter(Color.RED)
                        noteSchedule.text = LabApp.resourses.getString(R.string.late_schedule_state)
                    }
                    daysDiff < 30 -> {
                        val weeksDiff = (daysDiff / 7).toInt()
                        noteSchedule.text = LabApp.resourses.getQuantityString(
                            R.plurals.due_weeks_schedule_state,
                            weeksDiff,
                            weeksDiff // jsp pourquoi on doit mettre 2 fois mais c'est obligatoire
                        )
                    }
                    else -> {
                        val monthsDiff = (daysDiff / 30).toInt()
                        noteSchedule.text = LabApp.resourses.getQuantityString(
                            R.plurals.due_months_schedule_state,
                            monthsDiff,
                            monthsDiff // jsp pourquoi on doit mettre 2 fois mais c'est obligatoire
                        )
                    }
                }
            } else {
                noteClockImage.visibility = View.GONE
                noteSchedule.visibility = View.GONE
            }
        }
    }

    /**
     * Used to compute differences between an old list and a new list (to update the RecyclerView)
     */
    inner class NoteDiffCallback(
        private val oldList: List<NoteAndSchedule>,
        private val newList: List<NoteAndSchedule>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldNote = oldList[oldItemPosition].note
            val newNote = newList[newItemPosition].note
            val oldSchedule = oldList[oldItemPosition].schedule
            val newSchedule = newList[newItemPosition].schedule

            return oldNote.noteId == newNote.noteId &&
                    (oldSchedule == null && newSchedule == null || oldSchedule?.scheduleId == newSchedule?.scheduleId)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldNote = oldList[oldItemPosition].note
            val newNote = newList[newItemPosition].note
            val oldSchedule = oldList[oldItemPosition].schedule
            val newSchedule = newList[newItemPosition].schedule

            return oldNote::class == newNote::class && oldNote.title == newNote.title && newSchedule?.date == oldSchedule?.date
        }

    }
}