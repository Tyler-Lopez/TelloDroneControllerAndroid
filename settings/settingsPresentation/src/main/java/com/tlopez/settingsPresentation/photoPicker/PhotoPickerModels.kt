package com.tlopez.settingsPresentation.photoPicker

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface PhotoPickerViewEvent : ViewEvent {
    object ClickedNavigateUp : PhotoPickerViewEvent
}

sealed interface PhotoPickerViewState : ViewState {

}