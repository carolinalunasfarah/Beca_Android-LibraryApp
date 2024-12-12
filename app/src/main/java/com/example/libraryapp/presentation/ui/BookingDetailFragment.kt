package com.example.libraryapp.presentation.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryapp.R
import com.example.libraryapp.databinding.DialogAddBookBinding
import com.example.libraryapp.databinding.FragmentBookingDetailBinding
import com.example.libraryapp.presentation.viewmodel.BookDetailViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class BookingDetailFragment : Fragment(R.layout.fragment_booking_detail) {

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

        val button = binding.updateButton
        val bookId = arguments?.getInt("bookId") ?: return

        button.setOnClickListener {
            showUpdateDialog()
        }

        viewModel.loadBook(bookId)

        viewModel.book.observe(viewLifecycleOwner) { book ->
            book?.let {
                binding.titleText.text = it.title
                binding.authorText.text = it.author
                binding.yearText.text = it.year.toString()
                binding.descriptionText.text = it.description

                val isAvailable = it.isAvailable
                binding.availabilityChip.apply {
                    text = if (isAvailable) "Available" else "Checked Out"
                    chipBackgroundColor = ColorStateList.valueOf(
                        requireContext().getColor(
                            if (isAvailable) R.color.available else R.color.checked_out
                        )
                    )
                    contentDescription = getString(
                        if (isAvailable) R.string.chip_available else R.string.chip_checked_out
                    )
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    //UPDATE
    private fun showUpdateDialog() {
        val dialogBinding = DialogAddBookBinding.inflate(layoutInflater)

        //SELECTED BOOK ACTUAL DATA
        val currentBook = viewModel.book.value ?: return
        dialogBinding.titleInput.setText(currentBook.title)
        dialogBinding.authorInput.setText(currentBook.author)
        dialogBinding.yearInput.setText(currentBook.year.toString())
        dialogBinding.descriptionInput.setText(currentBook.description)
        dialogBinding.availableCheckbox.isChecked = currentBook.isAvailable

        //CREATE DIALOG SET TO UPDATE
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update Book")
            .setView(dialogBinding.root)
            .setPositiveButton("Update") { _, _ ->
                with(dialogBinding) {
                    val title = titleInput.text.toString().trim()
                    val author = authorInput.text.toString().trim()
                    val year = yearInput.text.toString().toIntOrNull()
                    val description = descriptionInput.text.toString().trim()
                    val isAvailable = availableCheckbox.isChecked

                    //VALIDATIONS
                    if (title.isEmpty() || author.isEmpty() || year == null || description.isEmpty()) {
                        Snackbar.make(
                            binding.root,
                            "All fields are required and year must be valid.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        return@setPositiveButton
                    }

                    //UPDATE BOOK
                    val updatedBook = currentBook.copy(
                        title = title,
                        author = author,
                        year = year,
                        description = description,
                        isAvailable = isAvailable
                    )
                    viewModel.updateBook(updatedBook)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
