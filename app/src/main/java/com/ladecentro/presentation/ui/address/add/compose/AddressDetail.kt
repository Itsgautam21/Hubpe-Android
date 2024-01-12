package com.ladecentro.presentation.ui.address.add.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.OutlinedTextFieldCompose
import com.ladecentro.presentation.theme.Montserrat
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
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
            Text(
                text = "Save address as *",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = fontFamilyHind,
                color = light_text
            )
            Spacer(modifier = Modifier.height(4.dp))
            TypeAddressChip()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TypeAddressChip(vm: AddAddressViewModel = hiltViewModel()) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(vm.addressTypes) { item ->
            FilterChip(
                selected = (item == vm.selectedItem),
                onClick = {
                    vm.selectedItem = item
                },
                label = {
                    Text(
                        text = item,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = fontFamilyHind,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                border = FilterChipDefaults.filterChipBorder(
                    selectedBorderColor = primary_orange,
                    selectedBorderWidth = 1.dp,
                    borderColor = border_light_gray,
                    borderWidth = 1.dp
                ),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Companion.White,
                    selectedContainerColor = light_orange,
                )
            )
        }
    }
}