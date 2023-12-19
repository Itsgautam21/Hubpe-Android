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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.shimmer_gray

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarSearch(
    placeHolder: String,
    isFocus: Boolean,
    color: Color = Color.White,
    textValue: (value: String) -> Unit
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = color),
        title = {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                SearchMainCompose(placeHolder, isFocus, textValue)
            }
        }
    )
}

@Composable
fun SearchMainCompose(placeHolder: String, isFocus: Boolean, textValue: (value: String) -> Unit) {

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
                    .padding(top = 12.dp, bottom = 12.dp, end = 12.dp)
                    .fillMaxHeight()
                    .width(1.dp), color = card_border
            )
            Box(modifier = Modifier.weight(1f)) {
                SearchTextField(placeHolder, isFocus, textValue)
            }
            Divider(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp, end = 12.dp, start = 4.dp)
                    .fillMaxHeight()
                    .width(1.dp), color = card_border
            )
            Icon(
                painter = painterResource(id = drawable.search),
                contentDescription = "search",
                tint = darkBlue,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun SearchTextField(
    placeHolder: String,
    isFocus: Boolean,
    textValue: (value: String) -> Unit
) {

    var searchText by remember { mutableStateOf("") }
    val focusRequest = remember { FocusRequester() }

    LaunchedEffect(key1 = searchText) {
        textValue(searchText)
    }

    BasicTextField(
        value = searchText,
        onValueChange = { searchText = it },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequest),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = fontFamilyHind,
            fontWeight = FontWeight.Bold
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    if (searchText.isEmpty()) Text(
                        placeHolder,
                        style = LocalTextStyle.current.copy(
                            color = light_text,
                            fontSize = 15.sp,
                            fontFamily = fontFamilyHind
                        )
                    )
                    innerTextField()
                }
                if (searchText.isNotBlank()) {
                    IconButton(
                        onClick = { searchText = "" },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = drawable.icons_clear),
                            contentDescription = "clear text",
                            modifier = Modifier.size(18.dp),
                            tint = shimmer_gray
                        )
                    }
                }
            }
        }
    )
    LaunchedEffect(key1 = Unit) {
        if (isFocus) {
            focusRequest.requestFocus()
        }
    }
}