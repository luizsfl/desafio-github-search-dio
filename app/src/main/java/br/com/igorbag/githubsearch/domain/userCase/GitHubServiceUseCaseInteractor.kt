package br.com.igorbag.githubsearch.domain.userCase

import br.com.igorbag.githubsearch.domain.model.Repository
import kotlinx.coroutines.flow.Flow

interface GitHubServiceUseCaseInteractor {
    fun getAllRepositoriesByUser(user:String): Flow<List<Repository>>

}

class GitHubServiceUseCaseInteractorImp(
    private val searchGitHubServiceUseCase: SearchGitHubServiceUseCase
) :GitHubServiceUseCaseInteractor{
    override fun getAllRepositoriesByUser(user: String) =
        searchGitHubServiceUseCase.invoke(user)
}