package com.example.imperative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
class DetailsViewModel @Inject constructor(private val repository: TVShowRepository):ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val tvShowDetails = MutableLiveData<TVShowDetails>()


    /* *
    * Retrofit related
    * */

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    fun apiTVShowDetails(q:Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.apiTVShowDetails(q)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val resp = response.body()
                    tvShowDetails.postValue(resp)
                }else{
                    onError("Error: ${response.message()}")
                }
            }
        }
    }
}
