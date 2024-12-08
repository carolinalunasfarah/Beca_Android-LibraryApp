package com.example.libraryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentBookingDetailBinding
import com.example.libraryapp.presentation.viewmodel.BookDetailViewModel
import com.google.android.material.snackbar.Snackbar

class BookingDetailFragment : Fragment(R.layout.fragment_booking_detail) {

    private var param1: Int? = null

    private var _binding: FragmentBookingDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = arguments?.getInt("bookId") ?: return

        viewModel.loadBook(bookId)

        viewModel.book.observe(viewLifecycleOwner) { book ->
            book?.let {
                binding.titleText.text = it.title
                binding.authorText.text = it.author
                binding.yearText.text = it.year.toString()
                binding.descriptionText.text = it.description
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }

    }
}
