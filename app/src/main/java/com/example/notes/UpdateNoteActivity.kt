package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private  var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id",-1)
        if(noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.UpdateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString().trim()
            val newContent = binding.UpdateContentEditText.text.toString().trim()

            if (validateInput(newTitle, newContent)) {
                val updateNote = Note(noteId, newTitle, newContent)
                db.updateNote(updateNote)
                Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    private fun validateInput(title: String, content: String): Boolean {
        return when {
            title.isEmpty() -> {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
                false
            }
            content.isEmpty() -> {
                Toast.makeText(this, "Content is required", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}