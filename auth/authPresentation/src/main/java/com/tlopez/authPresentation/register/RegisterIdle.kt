package com.tlopez.authPresentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlopez.core.architecture.EventReceiver
import com.tlopez.corePresentation.common.ActionButton
import com.tlopez.corePresentation.common.SingleLineOutlinedTextField
import com.tlopez.corePresentation.common.TextFieldType
import com.tlopez.authPresentation.register.RegisterViewEvent.*
import com.tlopez.corePresentation.theme.Typography

@Composable
fun RegisterIdle(
    buttonsEnabled: Boolean,
    errorMessageEmail: String?,
    errorMessageUsername: String?,
    errorMessagePassword: String?,
    passHidden: Boolean,
    textEmail: String,
    textUsername: String,
    textPassword: String,
    eventReceiver: EventReceiver<RegisterViewEvent>
) {
    
}