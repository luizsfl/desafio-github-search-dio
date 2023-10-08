package br.com.igorbag.githubsearch.domain

import com.google.gson.annotations.SerializedName

data class Repository(
    val name: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val owner: Owner,
    val stargazersCount: Long,
    val watchers_count: Long,
    val forks_count: Long,
    val language: String,
    val description: String
)