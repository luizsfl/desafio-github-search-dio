package br.com.igorbag.githubsearch.domain.mapper

import br.com.igorbag.githubsearch.data.model.OwnerResponse
import br.com.igorbag.githubsearch.domain.model.Owner
fun  OwnerResponse.toOwner() =
    Owner(
        login = this.login,
        avatarURL = this.avatar_url
    )