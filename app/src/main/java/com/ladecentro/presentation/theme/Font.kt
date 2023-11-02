package com.ladecentro.presentation.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.ladecentro.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val fontFamilyHind = FontFamily(Font(googleFont = GoogleFont("Hind Madurai"), fontProvider = provider))
val fontFamilyAverSans = FontFamily(Font(googleFont = GoogleFont("Averia Sans Libre"), fontProvider = provider))
val fontFamilyFredoka = FontFamily(Font(googleFont = GoogleFont("Fredoka"), fontProvider = provider))
val signika = FontFamily(Font(googleFont = GoogleFont("Quasimoda"), fontProvider = provider))
val hammersmith = FontFamily(Font(googleFont = GoogleFont("Hammersmith One"), fontProvider = provider))
val doppio_one = FontFamily(Font(googleFont = GoogleFont("Doppio One"), fontProvider = provider))