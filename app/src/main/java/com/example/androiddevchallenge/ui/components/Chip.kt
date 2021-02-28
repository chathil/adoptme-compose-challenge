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
package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.AdoptmeTheme

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    colors: ChipColors = AdoptmeDefaultChip.defaultColors(),
    selected: Boolean = false,
    start: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    trailing: @Composable (() -> Unit)? = null
) {
    val contentColor = colors.contentColor(selected = selected)
    Row(
        modifier.height(32.dp).padding(end = 4.dp).wrapContentWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .background(colors.backgroundColor(selected = selected).value, RoundedCornerShape(50))
            .border(2.dp, AdoptmeTheme.colors.primary, RoundedCornerShape(50)).padding(8.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor.value) {
            ProvideTextStyle(value = MaterialTheme.typography.caption) {
                Spacer(modifier = Modifier.size(4.dp))
                start?.let {
                    it()
                    Spacer(modifier = Modifier.size(8.dp))
                }
                content()
                trailing?.let {
                    Spacer(modifier = Modifier.size(8.dp))
                    it()
                }
                Spacer(modifier = Modifier.size(4.dp))
            }
        }
    }
}

class AdoptmeDefaultChip {
    companion object {
        @Composable
        fun defaultColors(): ChipColors = AdoptmeDefaultChipColors(
            backgroundColor = AdoptmeTheme.colors.uiBackground,
            contentColor = AdoptmeTheme.colors.primary,
            selectedBackgroundColor = AdoptmeTheme.colors.primary,
            selectedContentColor = AdoptmeTheme.colors.uiBackground
        )
    }
}

@Immutable
private class AdoptmeDefaultChipColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val selectedBackgroundColor: Color,
    private val selectedContentColor: Color
) : ChipColors {
    @Composable
    override fun backgroundColor(selected: Boolean): State<Color> {
        val backgroundColor by animateColorAsState(if (selected) selectedBackgroundColor else backgroundColor)
        return rememberUpdatedState(backgroundColor)
    }

    @Composable
    override fun contentColor(selected: Boolean): State<Color> {
        val contentColor by animateColorAsState(if (selected) selectedContentColor else contentColor)
        return rememberUpdatedState(contentColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AdoptmeDefaultChipColors

        if (backgroundColor != other.backgroundColor) return false
        if (contentColor != other.contentColor) return false
        if (selectedBackgroundColor != other.selectedBackgroundColor) return false
        if (selectedContentColor != other.selectedContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + selectedBackgroundColor.hashCode()
        result = 31 * result + selectedContentColor.hashCode()
        return result
    }
}

@Stable
interface ChipColors {
    @Composable
    fun backgroundColor(selected: Boolean): State<Color>
    @Composable
    fun contentColor(selected: Boolean): State<Color>
}

@Preview
@Composable
fun ChipPreview() {
    Chip(
        start = {
            Image(
                painter = painterResource(id = R.drawable.icn_otter),
                modifier = Modifier
                    .size(16.dp)
                    .padding(start = 4.dp),
                contentDescription = null
            )
        },
        content = { Text("Account", modifier = Modifier.padding(end = 4.dp)) },
        trailing = {
            Image(
                painter = painterResource(id = R.drawable.icn_otter),
                modifier = Modifier
                    .size(16.dp)
                    .padding(start = 4.dp),
                contentDescription = null
            )
        }
    )
}
