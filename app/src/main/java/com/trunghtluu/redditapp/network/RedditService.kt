package com.trunghtluu.redditapp.network

import com.trunghtluu.redditapp.model.Reddit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {

    @GET("/r/{subreddit}/.json")
    abstract fun getSubreddit(
        @Path("subreddit") subreddit: String
    ) : Call<Reddit>

}