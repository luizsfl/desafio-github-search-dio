package br.com.igorbag.githubsearch.domain.model

data class Repository(
    val name: String,
    val htmlUrl: String,
    val owner: Owner,
    val stargazersCount: Long,
    val watchers_count: Long,
    val forks_count: Long
)