package com.ladecentro.common

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode.Restart
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.core.content.ContextCompat
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun Context.isGPSEnable(): Boolean {
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun Modifier.customShimmer(): Modifier = composed {
    shimmer(
        customShimmer = rememberShimmer(
            shimmerBounds = ShimmerBounds.View,
            theme = defaultShimmerTheme.copy(
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 400,
                        easing = LinearEasing,
                        delayMillis = 500,
                    ),
                    repeatMode = Restart,
                )
            )
        )
    )
}

fun Modifier.bounceClick(
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animationTransition = updateTransition(isPressed, label = "BouncingClickableTransition")
    val scaleFactor by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.94f else 1f },
        label = "BouncingClickableScaleFactorTransition",
    )
    val opacity by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.7f else 1f },
        label = "BouncingClickableOpacityTransition"
    )

    this
        .graphicsLayer {
            this.scaleX = scaleFactor
            this.scaleY = scaleFactor
            this.alpha = opacity
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onClick = onClick
        )
}