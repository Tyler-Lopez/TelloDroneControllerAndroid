package com.tlopez.tello_controller.presentation.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.tlopez.tello_controller.presentation.theme.Typography

@Composable
fun SingleLineOutlinedTextField(
    enabled: Boolean,
    errorMessage: String?,
    onValueChange: (String) -> Unit,
    textFieldType: TextFieldType,
    value: String,
    onKeyboardClosed: (() -> Unit)? = null
) {
    OutlinedTextField(
        enabled = enabled,
        isError = errorMessage != null,
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardClosed?.invoke() },
            onNext = { onKeyboardClosed?.invoke() }
        ),
        keyboardOptions = textFieldType.keyboardOptions,
        label = { Text(textFieldType.label) },
        onValueChange = onValueChange,
        value = value,
        placeholder = {
            textFieldType.placeholder?.let {
                Text(it)
            }
        },
        singleLine = true,
        trailingIcon = { (textFieldType as? TextFieldType.Password)?.TrailingIcon() },
        visualTransformation = (textFieldType as? TextFieldType.Password)?.visualTransformation
            ?: VisualTransformation.None,
    )
    errorMessage?.apply {
        Text(
            style = Typography.subtitle2,
            color = Color(139, 0, 0),
            textAlign = TextAlign.Center,
            text = this
        )
    }
}