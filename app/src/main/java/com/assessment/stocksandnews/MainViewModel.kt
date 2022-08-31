package com.assessment.stocksandnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.stocksandnews.network.News
import com.assessment.stocksandnews.network.NewsApi
import com.assessment.stocksandnews.network.StocksApi
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

enum class RequestStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel() {
    private val _status = MutableLiveData<RequestStatus>()
    val status: LiveData<RequestStatus> = _status

    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news

    private val _stocks = MutableLiveData<Response<ResponseBody>>()
    val stocks: LiveData<Response<ResponseBody>> = _stocks

    fun getNews() {
        viewModelScope.launch {
            _status.value = RequestStatus.LOADING
            try {
                _news.value = NewsApi.retrofitService.getNews()
                _status.value = RequestStatus.DONE
            } catch (e: Exception) {
                //println(e.message)
                _status.value = RequestStatus.ERROR
            }
        }
    }

    fun downloadStocks() {
        viewModelScope.launch {
            _stocks.value = StocksApi.retrofitService.downloadFile()
        }
    }
}