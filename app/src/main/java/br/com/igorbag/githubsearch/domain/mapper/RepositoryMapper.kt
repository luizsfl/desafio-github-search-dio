package br.com.igorbag.githubsearch.domain.mapper

import br.com.igorbag.githubsearch.data.model.RepositoryResponse
import br.com.igorbag.githubsearch.domain.model.Repository

fun List<RepositoryResponse>.toRemoteRepository() = map {
        Repository(
            name = it.name,
            htmlUrl = it.html_url,
            owner = it.owner.toOwner(),
            stargazersCount = it.stargazersCount,
            watchers_count = it.watchers_count,
            forks_count = it.forks_count
        )
    }

