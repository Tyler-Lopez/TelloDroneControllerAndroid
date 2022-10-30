package com.tlopez.telloShare.presentation

import androidx.lifecycle.viewModelScope
import com.tlopez.telloShare.architecture.BaseViewModel
import com.tlopez.telloShare.domain.usecase.GetAuthenticatedUserUseCase
import com.tlopez.telloShare.presentation.MainViewState.Authenticated
import com.tlopez.telloShare.presentation.MainViewState.Unauthenticated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAuthenticatedUserUseCase: GetAuthenticatedUserUseCase
) : BaseViewModel<MainViewState, MainViewEvent>() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAuthenticatedUserUseCase()
                .getOrNull()
                .run {
                    when (this) {
                        null -> Unauthenticated
                        else -> {
                            println("hi man ${this.username} and ${this.email}")
                            Authenticated
                        }
                    }
                }
                .push()
        }
    }

    override fun onEvent(event: MainViewEvent) {
        TODO("Not yet implemented")
    }

}