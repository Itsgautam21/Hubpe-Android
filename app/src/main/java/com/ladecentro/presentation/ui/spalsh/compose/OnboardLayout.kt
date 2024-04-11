package com.ladecentro.presentation.ui.spalsh.compose

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.ladecentro.R
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.authentication.login.LoginActivity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnBoardingLayout() {

    val context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize()) {
        ChangeStatusBar()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            OnboardUI {
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
        }
    }
}

@Composable
fun ChangeStatusBar() {

    val localView = LocalView.current
    val window = (localView.context as Activity).window
    SideEffect {
        window.statusBarColor = light_orange.toArgb()
        WindowCompat.getInsetsController(
            window, localView
        ).isAppearanceLightStatusBars = true
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true)
fun OnboardUI(onClickStart: () -> Unit = {}) {

    val images = listOf(
        R.drawable.onboard_burger,
        R.drawable.onboard_apple,
        R.drawable.onboard_women_bag,
        R.drawable.onboard_phone,
        R.drawable.onboard_sofa
    )
    val title = listOf(
        "Buy Food",
        "Buy Fruits & Vegetable",
        "Buy Fashion & Accessorise",
        "Buy Mobile & Electronics",
        "Buy Home & Décor"
    )
    val pagerState = rememberPagerState(pageCount = { images.size })
    val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isDraggedState) {
        snapshotFlow { isDraggedState.value }
            .collectLatest { isDragged ->
                if (!isDragged) {
                    while (true) {
                        delay(1000)
                        coroutineScope {
                            pagerState.animateScrollToPage(pagerState.currentPage.inc() % pagerState.pageCount)
                        }
                    }
                }
            }
    }
    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 200.dp, bottomEnd = 200.dp))
            .background(light_orange)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.hubpe_logo),
            contentDescription = "logo",
            modifier = Modifier
                .width(172.dp)
                .padding(top = 40.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(0.dp),
                pageSpacing = 16.dp
            ) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = images[index]),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = title[index],
                        textAlign = TextAlign.Center,
                        style = Typography.titleLarge.copy(fontFamily = fontFamilyHindBold)
                    )
                }
            }
            Text(
                text = "Order food online from your nearest restaurants. Get food delivered within 30 min.",
                style = Typography.bodyMedium.copy(
                    color = light_text,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 18.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onClickStart,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = primary_orange
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Get Started",
                    style = Typography.titleMedium.copy(
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = "Terms & Conditions",
                style = Typography.bodyLarge.copy(
                    color = light_text,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}