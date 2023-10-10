package br.com.igorbag.githubsearch.core

import br.com.igorbag.githubsearch.domain.model.Repository

sealed class ViewState {
    data class SetLoading(val isLoading: Boolean) : ViewState()
    data class SetRepositoryList(val listRepository: List<Repository> = emptyList()) : ViewState()
    data class LoadFailure(val messageError: String = String()) : ViewState()
}