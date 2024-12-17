package com.example.pagingthreenew

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pagingthreenew.data.api.MovieRepository
import com.example.pagingthreenew.models.Movie
import com.example.pagingthreenew.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val _movies = MutableLiveData<Resources<PagingData<Movie>>>()
    val movies: LiveData<Resources<PagingData<Movie>>> = _movies
    var newsPage = 1

    init {
        loadMovies("in")
    }

    private fun loadMovies(filmCode: String) {
        _movies.value = Resources.Loading()

        viewModelScope.launch {
            try {
                repository.getPopularMovies().collect { pagingData ->
                    _movies.postValue(Resources.Success(pagingData))
                }
            } catch (e: Exception) {
                _movies.postValue(Resources.Error("Ошибка загрузки фильмов"))
            }
        }
    }
}