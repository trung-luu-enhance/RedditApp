package com.trunghtluu.redditapp.network

import com.trunghtluu.redditapp.model.Reddit
import com.trunghtluu.redditapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditRetrofit {

    private val redditService: RedditService

    init {
        redditService = createRedditService(getInstance())
    }

    private fun getInstance() : Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun createRedditService(retrofit: Retrofit) : RedditService {
        return retrofit.create(RedditService::class.java)
    }

    fun getSubreddit(subreddit: String): Call<Reddit> {
        return redditService.getSubreddit(subreddit)
    }

}