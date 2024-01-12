package com.ladecentro.presentation.ui.address.add.compose

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.common.bounceClick
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.address.add.AddAddressViewModel
import com.ladecentro.presentation.validation.validateName
import com.ladecentro.presentation.validation.validatePhoneNumber
import com.ladecentro.presentation.validation.validateRequiredField

@Composable
fun AddAddressLayout(vm: AddAddressViewModel = hiltViewModel()) {

    val errorState by remember {
        derivedStateOf {
            vm.isNameError || vm.isPhoneError || vm.isHouseError || vm.isAreaError || vm.isCityError
        }
    }
    val context = LocalContext.current as Activity

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Add an address")
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(card_background)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                item { ContactDetails() }
                item { AddressDetails() }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(Color.White, contentColor = Color.Black),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Button(
                    onClick = {
                        validate(vm)
                        if (errorState) {
                            return@Button
                        }
                        vm.addAddress()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary_orange
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .height(50.dp)
                        .bounceClick(true) {

                        },
                    enabled = !vm.updateState.isLoading
                ) {
                    Text(
                        text = "Save Address",
                        fontSize = 18.sp,
                        fontFamily = fontFamilyHind,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = vm.updateState) {

        vm.updateState.content?.let {
            vm.setProfileToLocal(it)
            context.setResult(Activity.RESULT_OK)
            context.finish()
        }
    }
}

fun validate(vm: AddAddressViewModel) {

    val name = validateName(vm.receiverName)
    vm.nameErrorText = name ?: ""
    vm.isNameError = name != null

    val phone = validatePhoneNumber(vm.phoneNumber)
    vm.isPhoneError = !phone
    vm.phoneErrorText = if (phone) "" else "Invalid Phone!"

    val house = validateRequiredField(vm.house)
    vm.isHouseError = house != null
    vm.houseErrorText = house ?: ""

    val area = validateRequiredField(vm.area)
    vm.isAreaError = area != null
    vm.areaErrorText = area ?: ""

    val city = validateRequiredField(vm.city)
    vm.isCityError = city != null
    vm.cityErrorText = city ?: ""
}