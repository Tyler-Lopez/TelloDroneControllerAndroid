package com.tlopez.tello_controller.presentation

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.usecase.GetAuthenticatedUserUseCase
import com.tlopez.tello_controller.presentation.MainViewState.Authenticated
import com.tlopez.tello_controller.presentation.MainViewState.Unauthenticated
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
                        else -> Authenticated
                    }
                }
                .push()
        }
    }

    override fun onEvent(event: MainViewEvent) {
        TODO("Not yet implemented")
    }

}