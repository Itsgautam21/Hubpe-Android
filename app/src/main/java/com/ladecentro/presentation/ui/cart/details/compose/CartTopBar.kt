package com.ladecentro.presentation.ui.cart.details.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.primary_orange

@Composable
fun CartLoading() {

    Surface(color = light_orange, modifier = Modifier.fillMaxWidth(), shadowElevation = 4.dp) {
        Column {
            Text(
                text = "Verifying your cart with the store",
                fontFamily = fontFamilyHind,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
            LinearProgressIndicator(
                color = primary_orange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )
        }
    }
}