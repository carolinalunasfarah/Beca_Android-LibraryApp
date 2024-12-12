package com.example.libraryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.databinding.DialogAddBookBinding
import com.example.libraryapp.databinding.FragmentBookingListBinding
import com.example.libraryapp.presentation.adapter.BookAdapter
import com.example.libraryapp.presentation.viewmodel.BookListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "bookId"

class BookingListFragment : Fragment(R.layout.fragment_booking_list) {

    private var _binding: FragmentBookingListBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter

    private val viewModel: BookListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = binding.addButton
        setupRecyclerView()

        button.setOnClickListener {
            showAddBookDialog()
        }

        viewModel.books.observe(viewLifecycleOwner) { books ->
            bookAdapter.submitList(books)
            binding.emptyView.isVisible = books.isEmpty()
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

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter { book ->
            val bundle = Bundle().apply {
                putInt(ARG_PARAM1, book.id)
            }
            findNavController().navigate(
                R.id.action_bookingListFragment_to_bookingDetailFragment,
                bundle
            )
        }
        binding.recyclerView.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun showAddBookDialog() {
        val dialogBinding = DialogAddBookBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add New Book")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
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

                    //CALL VIEW MODEL ADD BOOK
                    viewModel.addBook(title, author, year, description, isAvailable)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    //REFRESH after updating a book to display correctly coming back to the list
    override fun onResume() {
        super.onResume()
        viewModel.loadBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}