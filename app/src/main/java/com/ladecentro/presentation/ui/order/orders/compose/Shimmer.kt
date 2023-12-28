package com.ladecentro.presentation.ui.order.orders.compose

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ladecentro.common.customShimmer
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.shimmer_gray

@Composable
fun SampleOrderShimmer() {

    Card(
        modifier = Modifier.padding(horizontal = 0.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        //border = BorderStroke(1.dp, card_border)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .customShimmer()
                        .background(color = shimmer_gray, shape = RoundedCornerShape(10.dp))
                )
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                            .weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                            .weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(.3f)
                    .height(12.dp)
                    .customShimmer()
                    .background(
                        color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                    )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(0.7f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )

                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(0.3f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .height(12.dp)
                            .customShimmer()
                            .background(
                                color = shimmer_gray, shape = RoundedCornerShape(10.dp)
                            )

                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ShimmerContent() {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false,
        modifier = Modifier
            .background(card_background)
            .height(1000.dp)
    ) {
        items(5) {
            SampleOrderShimmer()
        }
    }
}

