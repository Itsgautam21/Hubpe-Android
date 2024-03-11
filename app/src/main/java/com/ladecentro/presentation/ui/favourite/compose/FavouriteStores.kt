package com.ladecentro.presentation.ui.favourite.compose

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.domain.model.FavouriteStore
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.ui.stores.details.StoreActivity

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun SampleFavouriteStore(favourite: FavouriteStore, onClick: () -> Unit) {

    Surface(
        color = Color.White,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.bounceClick {
            onClick()
        }) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, card_border),
                color = Color.White
            ) {
                LoadImage(image = favourite.image, modifier = Modifier.size(72.dp))
            }
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = favourite.name,
                    style = Typography.bodyLarge.copy(fontFamily = fontFamilyHindBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.basicMarquee()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = favourite.shortAddress,
                    style = Typography.bodySmall.copy(color = light_text),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = light_gray
                    )
                    Text(
                        text = favourite.deliveryTime,
                        style = Typography.bodySmall.copy(
                            color = light_gray,
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun FavouriteStores(favourites: List<FavouriteStore>) {

    val context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(12.dp), modifier = Modifier
            .fillMaxSize()
            .background(background),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(favourites) {
            SampleFavouriteStore(it) {
                context.startActivity(
                    Intent(context, StoreActivity::class.java)
                        .putExtra(Intents.STORE_ID.name, it.id)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}