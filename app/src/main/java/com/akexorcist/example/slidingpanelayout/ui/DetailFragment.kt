package com.akexorcist.example.slidingpanelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.akexorcist.example.slidingpanelayout.R
import com.akexorcist.example.slidingpanelayout.databinding.FragmentDetailBinding
import com.akexorcist.example.slidingpanelayout.util.ShortenDrawableRequestListener
import com.akexorcist.example.slidingpanelayout.util.toDate
import com.akexorcist.example.slidingpanelayout.vo.Book
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DetailFragment : Fragment() {
    private val viewModel: BookViewModel by activityViewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentBookLiveData.observe(viewLifecycleOwner, bookObserver)
        binding.buttonSelectBook.setOnClickListener { openBooksPane() }
        showEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.currentBookLiveData.removeObserver(bookObserver)
        _binding = null
    }

    private val bookObserver = Observer<Book> { book ->
        updateBookDetail(book)
        showContent()
    }

    private val thumbnailRequestListener = object : ShortenDrawableRequestListener() {
        override fun onLoadCompleted(isSuccess: Boolean) {
            hideThumbnailLoading()
        }
    }

    private fun updateBookDetail(book: Book) {
        binding.textViewIsbn.text = book.isbn
        binding.textViewTitle.text = book.title
        binding.textViewAuthors.text = book.authors?.joinToString() ?: ""
        binding.textViewPageCount.text = getString(R.string.page_count, book.pageCount)
        binding.textViewPublishedDate.text = book.publishedDate?.date?.toDate() ?: ""
        binding.textViewDescription.text = when {
            book.longDescription != null -> getString(R.string.book_description, book.longDescription)
            book.shortDescription != null -> getString(R.string.book_description, book.shortDescription)
            else -> getString(R.string.no_book_description)
        }
        binding.textViewCategories.text = book.categories?.joinToString() ?: ""
        showThumbnailLoading()
        Glide.with(this)
            .load(book.thumbnailUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_broken_image)
            .addListener(thumbnailRequestListener)
            .into(binding.imageViewThumbnail)
    }

    private fun openBooksPane() {
        viewModel.openBooksPane()
    }

    private fun showThumbnailLoading() {
        binding.progressBarThumbnail.visibility = View.VISIBLE
    }

    private fun hideThumbnailLoading() {
        binding.progressBarThumbnail.visibility = View.GONE
    }

    private fun showContent() {
        binding.layoutContent.visibility = View.VISIBLE
        binding.layoutEmpty.visibility = View.GONE
    }

    private fun showEmpty() {
        binding.layoutContent.visibility = View.GONE
        binding.layoutEmpty.visibility = View.VISIBLE
    }
}
