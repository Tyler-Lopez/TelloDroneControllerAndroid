package com.tlopez.tello_controller.presentation.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

sealed interface TextFieldType {
    val keyboardOptions: KeyboardOptions
    val label: String
    val placeholder: String?

    object Email : TextFieldType {
        override val keyboardOptions: KeyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
        override val label: String = "Email"
        override val placeholder: String = "example@gmail.com"
    }

    object Username : TextFieldType {
        override val keyboardOptions: KeyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        override val label: String = "Username"
        override val placeholder: String? = null
    }


    object VerificationCode : TextFieldType {
        override val keyboardOptions: KeyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        )
        override val label: String = "Code"
        override val placeholder: String? = null
    }

    data class Password(
        val isHidden: Boolean,
        val onIconClicked: () -> Unit
    ) : TextFieldType {
        override val keyboardOptions: KeyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
        override val label: String = "Password"
        override val placeholder: String? = null

        @Composable
        fun TrailingIcon() {
            IconButton(onClick = onIconClicked) {
                Icon(
                    imageVector = if (isHidden) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = "Toggle Visibility"
                )
            }
        }

        val visualTransformation =
            if (isHidden) PasswordVisualTransformation() else VisualTransformation.None
    }
}