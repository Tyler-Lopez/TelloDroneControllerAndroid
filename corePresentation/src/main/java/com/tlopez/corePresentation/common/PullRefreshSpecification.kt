package com.tlopez.corePresentation.common

data class PullRefreshSpecification(
    val isRefreshing: Boolean,
    val onPullRequest: () -> Unit
)