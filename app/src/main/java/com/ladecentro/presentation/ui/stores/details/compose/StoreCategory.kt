package com.ladecentro.presentation.ui.stores.details.compose

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.Category
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.common.VerticalGrid
import com.ladecentro.presentation.theme.ImageBackground
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.ui.stores.product.ProductsActivity

@Composable
fun SampleStoreCategory(category: Category, onClick: () -> Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(0.dp)
            .bounceClick { onClick() }
    ) {
        Surface(shape = MaterialTheme.shapes.medium, color = Color.White) {
            LoadImage(
                image = category.images?.getOrNull(0),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(ImageBackground),
                contentScale = ContentScale.Crop

            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontFamily = fontFamilyHind,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}

@Composable
fun StoreCategories(categories: List<Category>, storeId: String, storeName: String) {

    val context = LocalContext.current

    Text(
        text = "Shop by Categories",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = fontFamilyHindBold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    VerticalGrid(
        composableList = categories.map {
            {
                SampleStoreCategory(category = it) {
                    context.startActivity(
                        Intent(context, ProductsActivity::class.java)
                            .putExtra(Intents.STORE_ID.name, storeId)
                            .putExtra(Intents.CATEGORY_NAME.name, it.name)
                            .putExtra(Intents.STORE_NAME.name, storeName)
                    )
                }
            }
        },
        itemsPerRow = 3
    )
}