/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = AdoptmeColorPalette(
    primary = DeepOrange300,
    primaryVariant = DeepOrange500,
    secondary = DeepOrange900,
    btnLike = Red500,
    uiBackground = Black,
    uiBorder = Grey200,
    textPrimary = White,
    textSecondary = Grey200,
    btnPrimary = DeepOrange300,
    btnContentInactive = Grey700,
    btnContent = White,
    isDark = true
)

private val LightColorPalette = AdoptmeColorPalette(
    primary = DeepOrange500,
    primaryVariant = DeepOrange300,
    secondary = DeepOrange900,
    btnLike = Red500,
    uiBackground = White,
    uiBorder = Grey700,
    textPrimary = Black,
    textSecondary = Grey700,
    btnPrimary = DeepOrange100,
    btnContentInactive = Grey200,
    btnContent = DeepOrange500,
    isDark = false
)

object AdoptmeTheme {
    val colors: AdoptmeColorPalette
        @Composable
        get() = LocalAdoptmeColorAmbient.current
}

class AdoptmeColorPalette(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    btnLike: Color,
    uiBackground: Color,
    uiBorder: Color,
    textPrimary: Color,
    textSecondary: Color,
    btnPrimary: Color,
    btnContentInactive: Color,
    btnContent: Color,
    isDark: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var btnLike by mutableStateOf(btnLike)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var btnPrimary by mutableStateOf(btnPrimary)
        private set
    var btnContentInactive by mutableStateOf(btnContentInactive)
        private set
    var btnContent by mutableStateOf(btnContent)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: AdoptmeColorPalette) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        btnLike = other.btnLike
        uiBackground = other.uiBackground
        uiBorder = other.uiBorder
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        btnPrimary = other.btnPrimary
        btnContentInactive = other.btnContentInactive
        btnContent = other.btnContent
        isDark = other.isDark
    }
}

@Composable
fun ProvideAdoptmeColors(
    colors: AdoptmeColorPalette,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalAdoptmeColorAmbient provides colorPalette, content = content)
}

private val LocalAdoptmeColorAmbient = staticCompositionLocalOf<AdoptmeColorPalette> {
    error("No AdoptmeColorPalette provided")
}

@Composable
fun AdoptmeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    ProvideAdoptmeColors(colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)
