package br.com.igorbag.githubsearch.data.model
data class RepositoryResponse(
    val name: String,
    val html_url: String,
    val owner: OwnerResponse,
    val stargazersCount: Long,
    val watchers_count: Long,
    val forks_count: Long
)