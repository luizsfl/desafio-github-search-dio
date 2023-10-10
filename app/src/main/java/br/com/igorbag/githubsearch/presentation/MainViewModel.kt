package br.com.igorbag.githubsearch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igorbag.githubsearch.core.ViewState
import br.com.igorbag.githubsearch.domain.model.Repository
import br.com.igorbag.githubsearch.domain.userCase.GitHubServiceUseCaseInteractor
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class MainViewModel(
    private val gitHubServiceUseCaseInteractor: GitHubServiceUseCaseInteractor
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun getAllRepository(user:String) {
        viewModelScope.launch {
            gitHubServiceUseCaseInteractor.getAllRepositoriesByUser(user)
                .onStart { setLoading(isLoading = true) }
                .catch { setErro(textErro = it.message.orEmpty()) }
                .collect { setList(it) }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _viewState.value = ViewState.SetLoading(isLoading = isLoading)
    }

    private fun setErro(textErro: String) {
        setLoading(false)
        _viewState.value = ViewState.LoadFailure(textErro)
    }

    private fun setList(listRepository: List<Repository>) {
        setLoading(false)
        _viewState.value = ViewState.SetRepositoryList(listRepository)
    }
}