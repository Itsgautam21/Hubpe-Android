package com.ladecentro.presentation.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_text

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun OutlinedTextFieldCompose(
    value: String = "",
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    label: String,
    supportingText: String = "",
    textLimit: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (value: String) -> Unit
) {
    var text by remember { mutableStateOf(value) }
    var focus by remember { mutableStateOf(false) }
    val focusRequest = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val colours = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = light_text,
        focusedLabelColor = light_text,
        unfocusedLabelColor = light_text,
        unfocusedBorderColor = light_text
    )

    BasicTextField(
        value = text,
        onValueChange = {
            if (it.length <= textLimit) {
                text = it
                onValueChange(text)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequest)
            .onFocusChanged {
                focus = it.hasFocus
            },
        interactionSource = interactionSource,
        textStyle = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamilyHind,
        ),
        singleLine = singleLine,
        enabled = enabled,
        keyboardOptions = keyboardOptions
    ) { field ->
        DecorationBox(
            value = text,
            visualTransformation = VisualTransformation.None,
            innerTextField = field,
            singleLine = singleLine,
            enabled = enabled,
            label = {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = fontFamilyHind
                )
            },
            interactionSource = interactionSource,
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
            trailingIcon = {
                if (focus && text.isNotBlank()) {
                    IconButton(onClick = {
                        text = ""
                        onValueChange(text)
                    }) {
                        Icon(
                            painter = painterResource(id = drawable.icons_clear),
                            contentDescription = "clear icon",
                            tint = if (isError) MaterialTheme.colorScheme.error else light_text
                        )
                    }
                }
            },
            colors = colours,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = supportingText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fontFamilyHind,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        ) {
            OutlinedTextFieldDefaults.ContainerBox(
                enabled = enabled,
                isError = isError,
                interactionSource,
                colors = colours,
                focusedBorderThickness = 2.dp,
                unfocusedBorderThickness = 1.dp,
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}