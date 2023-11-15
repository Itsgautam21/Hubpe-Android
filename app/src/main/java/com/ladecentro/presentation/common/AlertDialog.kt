package com.ladecentro.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.hammersmith

@Composable
fun SimpleAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector?,
) {
    AlertDialog(
        title = {
            Text(
                text = dialogTitle,
                fontSize = 16.sp,
                fontFamily = hammersmith,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        },
        text = {
            Text(
                text = dialogText,
                fontSize = 14.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}