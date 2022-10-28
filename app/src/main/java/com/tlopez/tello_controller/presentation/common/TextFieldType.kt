package com.tlopez.tello_controller.presentation.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

sealed interface TextFieldType {
    val keyboardType: KeyboardType
    val label: String
    val placeholder: String?

    object Email : TextFieldType {
        override val keyboardType: KeyboardType = KeyboardType.Email
        override val label: String = "Email"
        override val placeholder: String = "example@gmail.com"
    }

    object Username : TextFieldType {
        override val keyboardType: KeyboardType = KeyboardType.Text
        override val label: String = "Username"
        override val placeholder: String? = null
    }

    data class Password(
        val isHidden: Boolean,
        val onIconClicked: () -> Unit
    ) : TextFieldType {
        override val keyboardType: KeyboardType = KeyboardType.Password
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