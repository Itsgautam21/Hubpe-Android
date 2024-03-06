package com.ladecentro.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.ladecentro.R

@Composable
fun LoadImage(
    image: String?,
    modifier: Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.None
) {

    val painter = rememberAsyncImagePainter(
        Builder(LocalContext.current)
            .data(data = image ?: "https://ndh.imgix.net/ndh-assets/img/no_image.png")
            .error(R.drawable.no_image)
            .apply {}
            .build()
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}
