package com.tlopez.feedPresentation.home

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface HomeViewEvent : ViewEvent {
    object ClickedSettings : HomeViewEvent
}

sealed interface HomeViewState : ViewState {

}