package com.example.pagingthreenew

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingthreenew.adapter.MovieAdapter
import com.example.pagingthreenew.databinding.ActivityMainBinding
import com.example.pagingthreenew.utils.Resources
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        movieAdapter = MovieAdapter()
        binding.filmsAdapterRV.layoutManager = LinearLayoutManager(this)
        binding.filmsAdapterRV.adapter = movieAdapter
        binding.filmsAdapterRV.layoutManager = LinearLayoutManager(this)

        viewModel.movies.observe(this) { response ->
            when (response) {
                is Resources.Success -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        movieAdapter.submitData(lifecycle, it)
                    }
                }

                is Resources.Loading -> {
                    binding.pagProgressBar.visibility = View.VISIBLE
                }

                is Resources.Error -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}