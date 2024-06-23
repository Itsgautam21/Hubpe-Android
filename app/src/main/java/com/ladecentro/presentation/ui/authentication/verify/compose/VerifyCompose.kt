package com.ladecentro.presentation.ui.authentication.verify.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.presentation.common.OtpTextField
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.authentication.verify.VerifyViewModel

@Composable
fun VerifyCompose(viewModel: VerifyViewModel) {

    Column(modifier = Modifier.padding(20.dp)) {

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Please enter OTP",
                fontSize = 22.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 28.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sent to " + viewModel.phoneNumber,
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Normal,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(24.dp))
            OtpTextField(otpText = viewModel.otpState) { value, _ ->
                viewModel.otpState = value
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Didn't receive OTP yet?",
                    fontSize = 13.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.Normal,
                    color = light_gray
                )
                Text(
                    text = "Re-send",
                    fontSize = 13.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            }

        }
        Button(
            onClick = {
                viewModel.verifyOTP()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primary_orange,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(10.dp),
            enabled = !viewModel.state.value.isLoading
        ) {
            Text(
                text = "Verify",
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}