package br.com.igorbag.githubsearch.data.repository

import br.com.igorbag.githubsearch.data.dataSource.remote.GitHubServiceDataSource
import br.com.igorbag.githubsearch.data.model.RepositoryResponse
import br.com.igorbag.githubsearch.domain.mapper.toRemoteRepository
import br.com.igorbag.githubsearch.domain.model.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface GitHubServiceRepository {
    fun getAllRepositoriesByUser(user:String): Flow<List<Repository>>
}

class GitHubServiceRepositoryImp(
    private val gitHubServiceDataSource: GitHubServiceDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
):GitHubServiceRepository {
    override fun getAllRepositoriesByUser(user: String): Flow<List<Repository>> {
        return flow {
            val repositoryResponse = gitHubServiceDataSource.getAllRepositoriesByUser(user)

            val repository = repositoryResponse.toRemoteRepository()
            if (repository.isEmpty()) {
                emit(repository)
            } else {
                emit(repository)
            }

        }.flowOn(dispatcher)
    }

}