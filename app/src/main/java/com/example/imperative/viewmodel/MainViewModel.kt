package com.example.imperative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imperative.model.TVShow
import com.example.imperative.model.TVShowDetails
import com.example.imperative.model.TVShowPopular
import com.example.imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TVShowRepository):ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()

    val tvShowsFromDB = MutableLiveData<List<TVShow>>()

    val tvShowPopular = MutableLiveData<TVShowPopular>()


    /* *
    * Retrofit related
    * */

    fun apiTVShowPopular(page:Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.apiTvShowPopular(page)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val resp = response.body()
                    tvShowPopular.postValue(resp)
                    /*var localShows = tvShowsFromApi.value
                    if (localShows == null) localShows = ArrayList()
                    val serverShows = resp!!.tv_shows
                    localShows.addAll(serverShows)*/
                    tvShowsFromApi.postValue(resp!!.tv_shows)
                    isLoading.value = false
                }else{
                    onError("Error: ${response.message()}")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    /* *
    * Room related
    * */

    fun getTVShowsFromDB(){
        viewModelScope.launch {
            val tvShows = repository.getTVShowFromDB()
            tvShowsFromDB.postValue(tvShows)
        }
    }

    fun insertTVShowsToDB(tvShow: TVShow){
        viewModelScope.launch {
            repository.insertTVShowToDB(tvShow)
        }
    }

    fun deleteTVShowsFromDB(){
        viewModelScope.launch {
          repository.deleteTVShowFromDB()
        }
    }
}
