package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val content = binding.contentEditText.text.toString().trim()

            if (validateInput(title, content)) {
                val note = Note(
                    id = 0,
                    title = title,
                    content = content
                )
                db.insertNote(note)
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
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
