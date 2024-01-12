package com.ladecentro.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.ladecentro.data.remote.dto.Location

data class DropdownMenu(
    val name: String,
    val vector: ImageVector,
    val onItemClick: (location: Location) -> Unit
)