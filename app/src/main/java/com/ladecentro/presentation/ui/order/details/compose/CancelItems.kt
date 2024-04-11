package com.ladecentro.presentation.ui.order.details.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R
import com.ladecentro.common.bounceClick
import com.ladecentro.domain.model.OrderDetail
import com.ladecentro.domain.model.ReturnDetail
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.order.details.OrderDetailsViewModel

@Composable
fun CanceledItems(heading: String, cancels: List<ReturnDetail>?) {

    if (!cancels.isNullOrEmpty()) {
        Surface(
            color = Color.White,
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, card_border)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = heading,
                    style = Typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.alpha(0.5f)
                ) {
                    repeat(cancels.size) {
                        SampleItem(item = cancels[it].item)
                    }
                }
                if (heading == "Return Item(s)") {
                    HorizontalDashDivider()
                    Text(
                        text = "Track Return",
                        style = Typography.titleSmall.copy(
                            color = primary_orange
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CancelItemLayout(order: OrderDetail, vm: OrderDetailsViewModel = hiltViewModel()) {

    val sheetState = rememberModalBottomSheetState()
    if (vm.cancelSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = Color.White,
            contentColor = Color.Black,
            shape = MaterialTheme.shapes.large,
            onDismissRequest = { vm.cancelSheet = false },
            dragHandle = { DragHandleCancel { vm.cancelSheet = false } },
            properties = ModalBottomSheetDefaults.properties(shouldDismissOnBackPress = false)
        ) {
            CancelItemsUI(order)
        }
    }
}

@Composable
fun DragHandleCancel(onCancel: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Cancel Order",
            style = Typography.titleLarge.copy(fontFamily = fontFamilyHindBold)
        )
        Icon(
            painter = painterResource(id = R.drawable.order_cancel),
            contentDescription = "close dialog",
            tint = border_light_gray,
            modifier = Modifier
                .size(24.dp)
                .bounceClick(onClick = onCancel)
        )
    }
}

@Composable
fun CancelItemsUI(order: OrderDetail) {

    val list = listOf("Cancel Complete Order", "Cancel Selected")
    var radioState by remember { mutableStateOf(list[0]) }

    Column {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            list.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RadioButton(
                        selected = it == radioState,
                        onClick = { radioState = it },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = primary_orange,
                            unselectedColor = light_gray
                        ),
                        modifier = Modifier
                            .size(10.dp)
                            .defaultMinSize(minHeight = 10.dp, minWidth = 10.dp)
                    )
                    Text(
                        text = it,
                        style = Typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
        HorizontalDivider()
        Column(modifier = Modifier.padding(16.dp)) {
            order.items.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    //Checkbox(checked = true, onCheckedChange = {})
                    SampleItem(item = it)
                }
            }
        }
        Box(modifier = Modifier.height(200.dp))
    }
}