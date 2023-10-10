package br.com.igorbag.githubsearch.data.remote

import br.com.igorbag.githubsearch.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
open class GitHubServiceRetrofit() {

        private val REMOTE_API_URL = "https://api.github.com/"

    fun getInstanceRetrofit(): GitHubService {
        return Retrofit.Builder()
            .baseUrl(REMOTE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }
}