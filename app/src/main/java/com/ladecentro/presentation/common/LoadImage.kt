package com.ladecentro.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.ladecentro.R

@Composable
fun LoadImage(
    image: String,
    modifier: Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.None
) {

    val painter = // You can apply additional options for image loading here if needed
        rememberAsyncImagePainter(
            Builder(LocalContext.current).data(data = image).error(R.drawable.default_profile)
                .apply(block = fun Builder.() {
                    // You can apply additional options for image loading here if needed
                }).build()
        )

    Image(
        painter = painter,
        contentDescription = contentDescription, // Provide content description if needed
        modifier = modifier,
        contentScale = contentScale
    )
}
