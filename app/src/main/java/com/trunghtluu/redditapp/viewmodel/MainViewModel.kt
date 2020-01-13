package com.trunghtluu.redditapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trunghtluu.redditapp.model.Child
import com.trunghtluu.redditapp.model.Reddit
import com.trunghtluu.redditapp.network.RedditRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val redditRetrofit = RedditRetrofit()
    private val resultLiveData = MutableLiveData<Reddit>()

    fun getSubreddit(subreddit: String) {
        redditRetrofit.getSubreddit(subreddit).enqueue(object : Callback<Reddit> {
            override fun onResponse(call: Call<Reddit>, response: Response<Reddit>) {
                Log.d("TAG_X", "pass")
                resultLiveData.setValue(response.body())
            }

            override fun onFailure(call: Call<Reddit>, t: Throwable) {
                Log.d("TAB_X", "Fail")

            }
        })
    }

    fun getResultLiveData(): MutableLiveData<Reddit> {
        return resultLiveData
    }
}