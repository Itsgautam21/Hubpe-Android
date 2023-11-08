package com.ladecentro.presentation.ui.search.compose

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.theme.dark_gray
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_text

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarSearch() {

    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
        title = {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                SearchMainCompose()
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun SearchMainCompose() {

    val context = LocalContext.current as Activity
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(end = 14.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.Black, containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 15.dp)
        ) {
            IconButton(onClick = {
                context.finish()
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "search back"
                )
            }
            Divider(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp, end = 0.dp)
                    .fillMaxHeight()
                    .width(1.dp), color = card_border
            )
            Box(modifier = Modifier.weight(1f)) {
                SearchTextField()
            }
            Divider(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp, end = 12.dp)
                    .fillMaxHeight()
                    .width(1.dp), color = card_border
            )
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "search",
                tint = darkBlue
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchTextField() {

    var searchText by remember { mutableStateOf("") }
    val focusRequest = remember { FocusRequester() }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        singleLine = true,
        placeholder = {
            Text(
                text = "Search Here",
                fontSize = 14.sp,
                color = light_text,
                fontFamily = fontFamilyHind
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { searchText = "" }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "close",
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        tint = dark_gray
                    )
                }
            }
        },
        modifier = Modifier.focusRequester(focusRequest),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = fontFamilyHind,
            fontWeight = FontWeight.SemiBold
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        )
    )
    LaunchedEffect(key1 = Unit) {
        focusRequest.requestFocus()
    }
}