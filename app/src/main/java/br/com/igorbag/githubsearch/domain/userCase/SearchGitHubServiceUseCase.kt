package br.com.igorbag.githubsearch.domain.userCase

import br.com.igorbag.githubsearch.data.repository.GitHubServiceRepository
class SearchGitHubServiceUseCase(
    private val gitHubServiceRepository: GitHubServiceRepository
) {
    operator fun invoke(user:String) = gitHubServiceRepository.getAllRepositoriesByUser(user)
}