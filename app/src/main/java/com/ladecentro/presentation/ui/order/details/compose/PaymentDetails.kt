package com.ladecentro.presentation.ui.order.details.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R
import com.ladecentro.common.bounceClick
import com.ladecentro.domain.model.PaymentDetails
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange

@Composable
fun PaymentDetails(paymentDetails: PaymentDetails) {

    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    text = "Payment Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyHind,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.order_complete),
                    contentDescription = "Order Complete",
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = paymentDetails.date,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                fontFamily = fontFamilyHind,
                color = light_text
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "₹${paymentDetails.price} - ${paymentDetails.mode} - ${paymentDetails.info}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Ref No: ${paymentDetails.refNo}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                fontFamily = fontFamilyHind,
                color = border_light_gray
            )
        }
    }
}

@Composable
fun RaiseCancelReturn() {

    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Raise Issue",
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.bounceClick { }
            )
            VerticalDivider(
                color = card_border,
                thickness = 1.dp,
                modifier = Modifier.fillMaxHeight()
            )
            Text(
                text = "Cancel Order",
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                color = primary_orange,
                textAlign = TextAlign.End,
                modifier = Modifier.bounceClick { }
            )
        }
    }
}