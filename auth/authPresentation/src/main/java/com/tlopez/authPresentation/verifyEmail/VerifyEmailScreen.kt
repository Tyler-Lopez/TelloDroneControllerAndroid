package com.tlopez.authPresentation.verifyEmail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlopez.authPresentation.verifyEmail.VerifyEmailViewEvent.*
import com.tlopez.corePresentation.common.ActionButton
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.corePresentation.common.SingleLineOutlinedTextField
import com.tlopez.corePresentation.common.TextFieldType
import com.tlopez.corePresentation.theme.Typography

@Composable
fun VerifyEmailScreen(viewModel: VerifyEmailViewModel) {
    ScreenBackground {
        val focusManager = LocalFocusManager.current
        viewModel.viewState.collectAsState().value?.apply {
            Text(
                text = "Verify Email",
                textAlign = TextAlign.Center,
                style = Typography.h4
            )
            if (email != null) {
                Text("Successfully created account $username",
                    textAlign = TextAlign.Center)
                Text("We sent a verification email to $email",
                    textAlign = TextAlign.Center)
            } else {
                Text("This account has not yet been verified",
                textAlign = TextAlign.Center)
                Text("A verification email has been sent to the email associated with this account",
                    textAlign = TextAlign.Center)
            }
            Text(
                text = "Enter the code to continue",
                textAlign = TextAlign.Center
            )
            SingleLineOutlinedTextField(
                enabled = buttonsEnabled,
                errorMessage = errorMessageCode,
                value = textCode,
                onKeyboardClosed = {
                    focusManager.clearFocus()
                },
                onValueChange = {
                    viewModel.onEvent(TextChangedCode(it))
                },
                textFieldType = TextFieldType.VerificationCode
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ActionButton(
                    isLoading = !buttonsEnabled,
                    text = "Verify",
                ) { viewModel.onEventDebounced(ClickedVerify) }
                if (email == null) {
                    TextButton(
                        enabled = buttonsEnabled,
                        modifier = Modifier.defaultMinSize(minWidth = 164.dp),
                        onClick = { viewModel.onEventDebounced(ClickedResendCode) }
                    ) {
                        Text("Resend verification")
                    }
                }
            }
        }
    }
}