package br.com.igorbag.githubsearch.core.di

import br.com.igorbag.githubsearch.data.dataSource.remote.GitHubServiceDataSource
import br.com.igorbag.githubsearch.data.dataSource.remote.GitHubServiceDataSourceImp
import br.com.igorbag.githubsearch.data.remote.GitHubServiceRetrofit
import br.com.igorbag.githubsearch.data.repository.GitHubServiceRepository
import br.com.igorbag.githubsearch.data.repository.GitHubServiceRepositoryImp
import br.com.igorbag.githubsearch.domain.userCase.GitHubServiceUseCaseInteractor
import br.com.igorbag.githubsearch.domain.userCase.GitHubServiceUseCaseInteractorImp
import br.com.igorbag.githubsearch.domain.userCase.SearchGitHubServiceUseCase
import br.com.igorbag.githubsearch.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory {  GitHubServiceRetrofit().getInstanceRetrofit() }
    factory<GitHubServiceDataSource> { GitHubServiceDataSourceImp(gitHubService = get()) }
    factory<GitHubServiceRepository> { GitHubServiceRepositoryImp( gitHubServiceDataSource= get()) }
    factory { SearchGitHubServiceUseCase(gitHubServiceRepository = get()) }
    factory<GitHubServiceUseCaseInteractor> { GitHubServiceUseCaseInteractorImp( searchGitHubServiceUseCase = get() )}

    viewModel {
        MainViewModel(gitHubServiceUseCaseInteractor =get())
    }

}