package com.ladecentro.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ladecentro.common.bounceClick
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.hammersmith
import com.ladecentro.presentation.theme.light_gray

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

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun SimpleDialog(
    title: String = "Confirmation!",
    body: String = "Show more",
    positiveText: String = "Yes",
    negativeText: String = "No",
    positiveClick: () -> Unit = {},
    negativeClick: () -> Unit = {},
    dismissRequest: () -> Unit = {}
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Dialog(onDismissRequest = dismissRequest)
    {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = Color.White
        ) {
            Column {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        title,
                        style = Typography.bodyLarge.copy(fontFamily = fontFamilyHindBold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(body,
                        style = Typography.bodySmall.copy(color = light_gray, lineHeight = 18.sp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (isExpanded) 20 else 2,
                        modifier = Modifier
                            .animateContentSize()
                            .clickable {
                                isExpanded = !isExpanded
                            }
                    )
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        negativeText,
                        style = Typography.bodyLarge,
                        modifier = Modifier
                            .bounceClick { negativeClick() }
                            .padding(horizontal = 50.dp)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        positiveText,
                        style = Typography.bodyLarge,
                        modifier = Modifier
                            .bounceClick { positiveClick() }
                            .padding(horizontal = 50.dp)
                    )
                }
            }
        }
    }
}