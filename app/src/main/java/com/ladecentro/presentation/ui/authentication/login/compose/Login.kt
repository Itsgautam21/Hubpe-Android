package com.ladecentro.presentation.ui.authentication.login.compose

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.common.Intents
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.dark_gray
import com.ladecentro.presentation.theme.fontFamilyFredoka
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.theme.white
import com.ladecentro.presentation.ui.authentication.login.LoginViewModel
import com.ladecentro.presentation.ui.authentication.verify.VerifyActivity
import com.ladecentro.presentation.validation.validatePhoneNumber

@Composable
fun Login(viewModel: LoginViewModel) {

    var isError by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val focusRequest = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequest.requestFocus()
    }
    LaunchedEffect(key1 = viewModel.phoneState) {
        if (viewModel.phoneState.length == 10) {
            focusManager.clearFocus()
        }
    }
    Column(modifier = Modifier.padding(20.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Enter the phone number for verification",
                fontSize = 20.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 28.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Login or Create an Account",
                fontSize = 14.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Normal,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(40.dp))
//            Text(
//                text = "Mobile Number",
//                fontSize = 13.sp,
//                fontFamily = fontFamilyHind,
//                fontWeight = FontWeight.Bold,
//                color = light_gray
//            )
//            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = viewModel.phoneState,
                singleLine = true,
                modifier = Modifier.focusRequester(focusRequest),
                leadingIcon = {
                    Icon(
                        imageVector = Rounded.Phone, contentDescription = "call icon",
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        tint = dark_gray
                    )
                },
                shape = RoundedCornerShape(10.dp),
                onValueChange = {
                    if (it.length <= 10) {
                        viewModel.phoneState = it
                        isError = false
                    }
                },
                label = {
                    Text(
                        text = "Enter Phone number",
                        fontFamily = fontFamilyHind
                    )
                },
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = white,
                    unfocusedContainerColor = white,
                    disabledContainerColor = white,
                    unfocusedBorderColor = border_light_gray,
                    unfocusedLabelColor = light_gray,
                    errorLabelColor = MaterialTheme.colorScheme.error
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp, fontFamily = fontFamilyFredoka, fontWeight = FontWeight.Medium
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Invalid Phone number!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isError) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                })
        }
        Button(
            onClick = {
                if (!validatePhoneNumber(viewModel.phoneState)) {
                    isError = true
                    return@Button
                }
                viewModel.sendOTP()
                context.startActivity(
                    Intent(context, VerifyActivity::class.java)
                        .putExtra(Intents.Phone.name, viewModel.phoneState)
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primary_orange, contentColor = Companion.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}