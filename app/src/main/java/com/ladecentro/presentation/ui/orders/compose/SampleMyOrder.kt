package com.ladecentro.presentation.ui.orders.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.common.RatingBar
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyFredoka
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange

@Composable
fun SampleMyOrder() {

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
    ) {
        Column(modifier = Modifier.background(card_background)) {
            StoreDetails()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            repeat(2) {
                ItemDetails()
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Divider(modifier = Modifier.height(1.dp), color = card_border)
                Card(
                    border = BorderStroke(1.dp, card_border),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Ordered ID : #100001",
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        fontFamily = fontFamilyHind,
                        color = light_gray,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            PaymentsAndStatus()
            Spacer(modifier = Modifier.height(12.dp))
            Divider(modifier = Modifier.height(1.dp), color = card_border)
            Spacer(modifier = Modifier.height(12.dp))
            Rating()
        }
    }
}

@Composable
fun StoreDetails() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(shape = RoundedCornerShape(12.dp)) {
            Image(
                painter = painterResource(id = drawable.spotlight1),
                contentDescription = "store logo",
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Macdolands",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Ruby, Kolkata",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = fontFamilyHind,
                color = light_gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "forward arrow",
            modifier = Modifier
                .height(28.dp)
                .width(28.dp),
        )
    }
    Divider(modifier = Modifier.height(1.dp), color = card_border)
}

@Composable
fun ItemDetails() {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = drawable.spotlight2),
                    contentDescription = "store logo",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "1 x Double Quarter Pounder with Chicken",
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun PaymentsAndStatus() {
    Row {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
            Text(
                text = "Fri, Jan 13, 2023, 4:24 PM",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                fontFamily = fontFamilyHind,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = drawable.order_complete),
                    contentDescription = "Order Complete",
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Completed",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    fontFamily = fontFamilyFredoka,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
            Text(
                text = "Prepaid",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                fontFamily = fontFamilyHind,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "₹520",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = doppio_one,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun Rating() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            Text(
                text = "How would you rate this order?",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                fontFamily = fontFamilyHind,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            RatingBar(rating = 0)
        }
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = primary_orange,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Icon(
                imageVector = Rounded.Refresh,
                contentDescription = "Reorder",
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Reorder",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontFamily = fontFamilyHind,
                color = Color.White
            )
        }
    }
}

@Composable
fun MyOrders() {

    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(10) {
            SampleMyOrder()
        }
    }
}