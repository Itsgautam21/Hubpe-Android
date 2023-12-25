package com.ladecentro.presentation.ui.address.add.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.OutlinedTextFieldCompose
import com.ladecentro.presentation.theme.Montserrat
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.ui.address.add.AddAddressViewModel

@Composable
fun AddressDetails(vm: AddAddressViewModel = hiltViewModel()) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White, contentColor = Color.Black),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Address Details",
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = Montserrat,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextFieldCompose(
                value = vm.house,
                label = "Flat/House number, floor, building *",
                isError = vm.isHouseError,
                textLimit = 50,
                supportingText = vm.houseErrorText
            ) { text ->
                vm.house = text.trim()
                vm.isHouseError = false
                vm.houseErrorText = ""
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextFieldCompose(
                value = vm.area,
                label = "Area/sector/locality *",
                isError = vm.isAreaError,
                textLimit = 50,
                supportingText = vm.areaErrorText
            ) { text ->
                vm.area = text.trim()
                vm.isAreaError = false
                vm.areaErrorText = ""
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextFieldCompose(
                value = vm.city,
                label = "City *",
                isError = vm.isCityError,
                textLimit = 50,
                supportingText = vm.cityErrorText,
                enabled = false
            ) { text ->
                vm.city = text.trim()
                vm.isCityError = false
                vm.cityErrorText = ""
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextFieldCompose(
                value = vm.landmark,
                label = "Nearby landmark (optional)"
            ) { text ->
                vm.landmark = text
            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(5) {
                    SampleAddressType()
                }
            }
        }
    }
}

@Composable
fun SampleAddressType() {

    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, contentColor = Color.Black
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, card_border)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Rounded.Home,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "Work",
                fontSize = 14.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}