package com.ladecentro.presentation.ui.order.details.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.domain.model.DeliveryDetails
import com.ladecentro.domain.model.Store
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray

@Composable
fun DeliveryDetails(delivery: DeliveryDetails) {

    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, color = card_border)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Delivery details",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))
            StoreDetails(delivery.store)
            Row(verticalAlignment = Alignment.CenterVertically) {
                VerticalDivider(
                    thickness = 1.dp, color = card_border, modifier = Modifier
                        .height(32.dp)
                        .padding(horizontal = 24.dp)
                )
                HorizontalDashDivider()
            }
            StoreDetails(delivery.person)
        }
    }
}

@Composable
fun StoreDetails(store: Store) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, card_border),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            LoadImage(
                image = store.image,
                contentDescription = "store logo",
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp),
                contentScale = ContentScale.Inside
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = store.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = store.shortAddress,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = fontFamilyHind,
                color = light_gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}