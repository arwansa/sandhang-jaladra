package com.arwan.arwansa_scanmecalculator.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.arwan.arwansa_scanmecalculator.BuildConfig

private val RedDarkColorScheme = darkColorScheme(
    primary = Color(0xFFCC0000),     // Dark Red
    secondary = Color(0xFF8B0000),   // Darker Red
    tertiary = Color(0xFFFFC0CB)     // Pink
)

private val RedLightColorScheme = lightColorScheme(
    primary = Color(0xFFFF0000),     // Red
    secondary = Color(0xFFFF69B4),   // Light Pink
    tertiary = Color(0xFFFFC0CB)     // Pink
)

private val GreenDarkColorScheme = darkColorScheme(
    primary = Color(0xFF00CC00),     // Green
    secondary = Color(0xFF006400),   // Dark Green
    tertiary = Color(0xFF00FF00)     // Lime
)

private val GreenLightColorScheme = lightColorScheme(
    primary = Color(0xFF00FF00),     // Light Green
    secondary = Color(0xFF00FF00),   // Light Green
    tertiary = Color(0xFF00FF00)     // Lime
)

@Composable
fun ArwanSAScanMeCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (BuildConfig.IS_RED) {
        if (darkTheme) RedDarkColorScheme else RedLightColorScheme
    } else {
        if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}