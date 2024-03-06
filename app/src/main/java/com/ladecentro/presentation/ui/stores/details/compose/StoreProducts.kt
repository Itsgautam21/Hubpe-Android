package com.ladecentro.presentation.ui.stores.details.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.data.remote.dto.Category
import com.ladecentro.domain.model.ItemDetails
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyFredoka
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.stores.details.StoreViewModel

@Composable
fun SampleGeneralProduct(
    product: ItemDetails,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = Modifier.width(140.dp)
    ) {
        Column {
            Surface(
                color = Color.White,
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, card_border)
            ) {
                LoadImage(
                    image = product.image,
                    modifier = Modifier.size(140.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)) {
                Text(
                    text = product.brand ?: "",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    fontFamily = fontFamilyHind,
                    maxLines = 1,
                    minLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    fontFamily = fontFamilyHind,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "₹${product.price}",
                        fontFamily = fontFamilyFredoka,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                    )
                    TextButton(
                        onClick = onClick,
                        border = BorderStroke(1.dp, card_border),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .defaultMinSize(minHeight = 28.dp)
                            .height(28.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AnimatedVisibility(visible = product.quantity != 0) {
                                Text(
                                    text = "-",
                                    fontSize = 16.sp,
                                    fontFamily = fontFamilyHindBold,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = primary_orange,
                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = true
                                        )
                                    ),
                                    modifier = Modifier.clickable(onClick = onMinusClick)
                                )
                            }
                            Text(
                                text = if (product.quantity == 0) "ADD" else product.quantity.toString(),
                                fontSize = 14.sp,
                                fontFamily = fontFamilyHindBold,
                                fontWeight = FontWeight.ExtraBold,
                                color = primary_orange,
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = true
                                    )
                                )
                            )
                            AnimatedVisibility(visible = product.quantity != 0) {
                                Text(
                                    text = "+",
                                    fontSize = 16.sp,
                                    fontFamily = fontFamilyHindBold,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = primary_orange,
                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = true
                                        )
                                    ),
                                    modifier = Modifier.clickable(onClick = onPlusClick)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoreProducts(category: Category, vm: StoreViewModel = hiltViewModel()) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp, vertical = 0.dp)
    ) {
        Text(
            text = category.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            fontFamily = fontFamilyHindBold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "View All",
            fontSize = 12.sp,
            color = primary_orange,
            fontFamily = fontFamilyHindBold,
            fontWeight = FontWeight.SemiBold
        )
    }
    vm.products.content?.let { products ->

        val list = products.filter { product -> product.categoryId == category.name }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(list) {
                SampleGeneralProduct(it,
                    onPlusClick = { vm.createCart(it, vm.getItemCount(it.id!!).plus(1)) },
                    onMinusClick = { vm.createCart(it, vm.getItemCount(it.id!!).minus(1)) },
                    onClick = { vm.createCart(it, vm.getItemCount(it.id!!).plus(1)) }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(28.dp))
}