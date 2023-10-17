package com.ladecentro.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder

@Composable
fun LoadImage(image: String) {

    val painter = // You can apply additional options for image loading here if needed
        rememberAsyncImagePainter(
            Builder(LocalContext.current).data(data = image).apply(block = fun Builder.() {
                // You can apply additional options for image loading here if needed
            }).build()
        )

    Image(
        painter = painter,
        contentDescription = "", // Provide content description if needed
        modifier = Modifier
            .fillMaxSize() // Adjust modifier as per your layout requirements
    )
}
