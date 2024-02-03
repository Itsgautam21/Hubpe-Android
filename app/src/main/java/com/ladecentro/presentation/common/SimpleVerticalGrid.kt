package com.ladecentro.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalGrid(
    composableList: List<@Composable () -> Unit>,
    itemsPerRow: Int,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    horizontalSpace: Dp = 20.dp,
    verticalSpace: Dp = 16.dp
) {
    val components = composableList.toMutableList()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(contentPadding)
    ) {
        while (components.isNotEmpty()) {
            val rowComponents: List<@Composable () -> Unit> = components.take(itemsPerRow)
            val listOfSpacers: List<@Composable () -> Unit> =
                listOfSpacers(itemsPerRow - rowComponents.size)
            RowWithItems(items = rowComponents.plus(listOfSpacers), horizontalSpace)
            components.removeAll(rowComponents)
            Spacer(modifier = Modifier.height(verticalSpace))
        }
    }
}

private fun listOfSpacers(number: Int): List<@Composable () -> Unit> {

    val mutableList = emptyList<@Composable () -> Unit>().toMutableList()
    repeat(number) {
        mutableList.add { Spacer(Modifier) }
    }
    return mutableList.toList()
}

@Composable
private fun RowWithItems(items: List<@Composable () -> Unit>, horizontalSpace: Dp) {

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(horizontalSpace)) {
        items.forEach { item ->
            Box(Modifier.weight(1f)) {
                item()
            }
        }
    }
}