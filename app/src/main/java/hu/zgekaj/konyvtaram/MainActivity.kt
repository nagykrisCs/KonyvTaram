package hu.zgekaj.konyvtaram

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.zgekaj.konyvtaram.data.AppDatabase
import hu.zgekaj.konyvtaram.data.Book
import hu.zgekaj.konyvtaram.ui.BookAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var adapter: BookAdapter
    private lateinit var counter: TextView
    private lateinit var emptyState: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = AppDatabase.getDatabase(this)
        counter = findViewById(R.id.tvSummary)
        emptyState = findViewById(R.id.tvEmpty)

        adapter = BookAdapter(
            onEdit = { showBookDialog(it) },
            onDelete = { confirmDelete(it) },
            onReadChanged = { book, checked ->
                lifecycleScope.launch { database.bookDao().update(book.copy(isRead = checked)) }
            }
        )
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener { showBookDialog() }

        lifecycleScope.launch {
            database.bookDao().getAll().collect { books ->
                adapter.submitList(books)
                val readCount = books.count { it.isRead }
                counter.text = getString(R.string.summary_format, books.size, readCount)
                emptyState.visibility = if (books.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            }
        }
    }

    private fun showBookDialog(book: Book? = null) {
        val view = layoutInflater.inflate(R.layout.dialog_book, null)
        val title = view.findViewById<EditText>(R.id.etTitle)
        val author = view.findViewById<EditText>(R.id.etAuthor)
        val year = view.findViewById<EditText>(R.id.etYear)
        val genre = view.findViewById<Spinner>(R.id.spinnerGenre)
        val genres = resources.getStringArray(R.array.genres)

        if (book != null) {
            title.setText(book.title)
            author.setText(book.author)
            year.setText(book.publicationYear.toString())
            genre.setSelection(genres.indexOf(book.genre).coerceAtLeast(0))
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle(if (book == null) R.string.add_book else R.string.edit_book)
            .setView(view)
            .setPositiveButton(R.string.save, null)
            .setNegativeButton(R.string.cancel, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val titleText = title.text.toString().trim()
                val authorText = author.text.toString().trim()
                val yearText = year.text.toString().trim()
                val parsedYear = yearText.toIntOrNull()
                when {
                    titleText.isEmpty() -> title.error = getString(R.string.required_field)
                    authorText.isEmpty() -> author.error = getString(R.string.required_field)
                    parsedYear == null || parsedYear !in 1..2100 -> year.error = getString(R.string.invalid_year)
                    else -> {
                        val updated = Book(
                            id = book?.id ?: 0,
                            title = titleText,
                            author = authorText,
                            genre = genres[genre.selectedItemPosition],
                            publicationYear = parsedYear,
                            isRead = book?.isRead ?: false
                        )
                        lifecycleScope.launch {
                            if (book == null) database.bookDao().insert(updated)
                            else database.bookDao().update(updated)
                        }
                        dialog.dismiss()
                    }
                }
            }
        }
        dialog.show()
    }

    private fun confirmDelete(book: Book) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_book)
            .setMessage(getString(R.string.delete_question, book.title))
            .setPositiveButton(R.string.delete) { _, _ ->
                lifecycleScope.launch { database.bookDao().delete(book) }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
