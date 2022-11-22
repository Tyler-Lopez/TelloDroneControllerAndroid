package com.tlopez.corePresentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenBackground(
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    pullRefreshSpecification: PullRefreshSpecification? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val pullRefreshState = pullRefreshSpecification?.let {
        rememberPullRefreshState(
            refreshing = it.isRefreshing,
            onRefresh = it.onPullRequest
        )
    }
    Box(
        modifier = Modifier.run {
            pullRefreshState?.let {
                pullRefresh(it)
            } ?: this
        }
    ) {
        Column(
            content = content,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = Arrangement.spacedBy(16.dp, verticalAlignment)
        )
        pullRefreshState?.let {
            PullRefreshIndicator(
                refreshing = pullRefreshSpecification.isRefreshing ?: false,
                state = it,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}