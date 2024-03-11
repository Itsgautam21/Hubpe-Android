package com.ladecentro.presentation.ui.location.select.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.domain.model.DropdownMenu
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.light_green
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_green
import com.ladecentro.presentation.theme.primary_orange

@Composable
fun SampleSavedAddress(
    location: Location,
    dropDownList: List<DropdownMenu>? = null,
    onLocationClick: (location: Location) -> Unit
) {
    var isMenuVisible by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(if (location.selected) light_orange else Companion.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.bounceClick {
            onLocationClick(location)
        },
        border = BorderStroke(1.dp, if (location.selected) primary_orange else Companion.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(12.dp)
        ) {
            Column {
                Icon(
                    painter = painterResource(id = drawable.location),
                    contentDescription = "location",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = location.descriptor.name,
                    fontSize = 15.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = location.descriptor.longDesc ?: "",
                    fontSize = 12.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.SemiBold,
                    color = light_text,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${location.address.name}, +91${location.mobileNumber}",
                    fontSize = 13.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.SemiBold,
                    color = light_gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            dropDownList?.let { d ->

                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Companion.Black
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(2.dp, card_border),
                        modifier = Modifier.bounceClick {
                            isMenuVisible = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(12.dp)
                        )
                        DropdownMenu(
                            expanded = isMenuVisible,
                            onDismissRequest = { isMenuVisible = false },
                            modifier = Modifier.background(Color.White),
                            offset = DpOffset(x = (-12).dp, y = 4.dp)
                        ) {
                            d.forEach {
                                DropdownMenuItem(text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = it.vector,
                                            contentDescription = "",
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = it.name,
                                            fontSize = 14.sp,
                                            fontFamily = fontFamilyHind,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }, onClick = {
                                    it.onItemClick(location)
                                    isMenuVisible = false
                                })
                            }
                        }
                    }
                }
            }
        }

    }
    Spacer(modifier = Modifier.height(12.dp))
}