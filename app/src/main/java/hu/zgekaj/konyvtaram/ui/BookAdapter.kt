package hu.zgekaj.konyvtaram.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.zgekaj.konyvtaram.R
import hu.zgekaj.konyvtaram.data.Book

class BookAdapter(
    private val onEdit: (Book) -> Unit,
    private val onDelete: (Book) -> Unit,
    private val onReadChanged: (Book, Boolean) -> Unit
) : ListAdapter<Book, BookAdapter.BookHolder>(BookDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) = holder.bind(getItem(position))

    inner class BookHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tvBookTitle)
        private val author: TextView = view.findViewById(R.id.tvBookAuthor)
        private val details: TextView = view.findViewById(R.id.tvBookDetails)
        private val read: CheckBox = view.findViewById(R.id.cbRead)
        private val edit: ImageButton = view.findViewById(R.id.btnEdit)
        private val delete: ImageButton = view.findViewById(R.id.btnDelete)

        fun bind(book: Book) {
            title.text = book.title
            author.text = book.author
            details.text = "${book.genre} • ${book.publicationYear}"
            read.setOnCheckedChangeListener(null)
            read.isChecked = book.isRead
            title.paintFlags = if (book.isRead) title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            itemView.setOnClickListener { onEdit(book) }
            edit.setOnClickListener { onEdit(book) }
            delete.setOnClickListener { onDelete(book) }
            read.setOnCheckedChangeListener { _, checked -> onReadChanged(book, checked) }
        }
    }

    private class BookDiff : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Book, newItem: Book) = oldItem == newItem
    }
}
