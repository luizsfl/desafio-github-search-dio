package br.com.igorbag.githubsearch.data.dataSource.remote

import br.com.igorbag.githubsearch.data.model.RepositoryResponse
import br.com.igorbag.githubsearch.data.remote.GitHubService

interface GitHubServiceDataSource {
    suspend fun getAllRepositoriesByUser(user:String): List<RepositoryResponse>
}

class GitHubServiceDataSourceImp(
    private val gitHubService: GitHubService,
):GitHubServiceDataSource {
    override suspend fun getAllRepositoriesByUser(user: String) = gitHubService.getAllRepositoriesByUser(user)

}