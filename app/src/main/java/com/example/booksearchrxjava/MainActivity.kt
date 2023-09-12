package com.example.booksearchrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksearchrxjava.adapter.BookListAdapter
import com.example.booksearchrxjava.databinding.ActivityMainBinding
import com.example.booksearchrxjava.network.BookListModel
import com.example.booksearchrxjava.util.NetworkResult
import com.example.booksearchrxjava.viewmodel.MainActivityViewModel
import com.example.booksearchrxjava.viewmodel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as MyApp).retrofitService)
    }
    private val mAdapter: BookListAdapter by lazy {
        BookListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSearchBox()
        initRecyclerView()

        viewModel.bookList.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data?.items?.isEmpty() == true) {
                        binding.ivNoData.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.VISIBLE
                        binding.rvBooks.visibility = View.GONE
                    } else {
                        binding.ivNoData.visibility = View.GONE
                        binding.tvNoData.visibility = View.GONE
                        binding.rvBooks.visibility = View.VISIBLE
                        mAdapter.submitList(it.data?.items)
                    }
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.ivNoData.visibility = View.GONE
                    binding.tvNoData.visibility = View.GONE
                    binding.rvBooks.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.ivNoData.visibility = View.GONE
                    binding.tvNoData.visibility = View.GONE
                    binding.rvBooks.visibility = View.GONE
                    it.message?.let { error -> Log.d("MainActivity", error) }
                }
            }
        }

    }

    private fun initSearchBox() {
        binding.etSearchBooks.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(searchWord: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.makeApiCall(searchWord.toString())
            }
        })
    }

    private fun initRecyclerView(){
        binding.rvBooks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }
}