package com.ladecentro.presentation.ui.address.add.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.OutlinedTextFieldCompose
import com.ladecentro.presentation.theme.Montserrat
import com.ladecentro.presentation.ui.address.add.AddAddressViewModel

@Composable
fun ContactDetails(vm: AddAddressViewModel = hiltViewModel()) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White, contentColor = Color.Black),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Contact Details",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextFieldCompose(
                label = "Receiver's name *",
                isError = vm.isNameError,
                textLimit = 50,
                supportingText = vm.nameErrorText
            ) { text ->
                vm.receiverName = text.trim()
                vm.isNameError = false
                vm.nameErrorText = ""
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextFieldCompose(
                label = "Phone number *",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                textLimit = 10,
                isError = vm.isPhoneError,
                supportingText = vm.phoneErrorText
            ) { text ->
                vm.phoneNumber = text.trim()
                vm.isPhoneError = false
                vm.phoneErrorText = ""
            }
        }
    }
}