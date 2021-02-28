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
package com.example.androiddevchallenge.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.model.Pet
import com.example.androiddevchallenge.model.fake
import com.example.androiddevchallenge.model.icon
import com.example.androiddevchallenge.model.image
import com.example.androiddevchallenge.ui.AdoptmeAppState
import com.example.androiddevchallenge.ui.components.AdoptmeButton
import com.example.androiddevchallenge.ui.components.AdoptmeScaffold
import com.example.androiddevchallenge.ui.components.Chip
import com.example.androiddevchallenge.ui.components.UpButton
import com.example.androiddevchallenge.ui.theme.AdoptmeTheme
import com.example.androiddevchallenge.ui.theme.AlphaNearTransparent
import com.example.androiddevchallenge.ui.theme.padding
import com.example.androiddevchallenge.ui.utils.LocalSysUiController
import java.util.Locale

@Composable
fun PetDetail(
    pet: Pet,
    upPress: () -> Unit,
    appState: AdoptmeAppState
) {
    LocalSysUiController.current.setStatusBarColor(
        AdoptmeTheme.colors.uiBackground.copy(
            AlphaNearTransparent
        )
    )
    AdoptmeScaffold {
        Box {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(bottom = 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = pet.image(LocalContext.current)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(296.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                Body(pet = pet, onLikeClick = { appState.like(pet) })
                Spacer(modifier = Modifier.height(32.dp))
            }
            UpButton(upPress)
        }
    }
}

@Composable
private fun Body(pet: Pet, modifier: Modifier = Modifier, onLikeClick: (Pet) -> Unit) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(horizontal = padding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(pet.name, style = MaterialTheme.typography.h4)
                Text(pet.location, style = MaterialTheme.typography.caption)
            }
            PetSize(pet = pet)
        }
        Row(
            modifier = Modifier
                .padding(horizontal = padding)
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Chip(
                    selected = true,
                    start = {
                        Icon(
                            painter = painterResource(
                                id = pet.icon()
                            ),
                            modifier = Modifier.size(16.dp),
                            contentDescription = null
                        )
                    },
                    content = {
                        Text(
                            pet.type.name.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT),
                        )
                    }
                )
                Chip(
                    selected = true,
                    content = {
                        Text(
                            if (pet.isMale) "Male" else "Female",
                        )
                    }
                )
            }
            IconButton(
                onClick = { onLikeClick(pet) }
            ) {
                Icon(
                    imageVector = if (pet.isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    tint = AdoptmeTheme.colors.btnLike,
                    contentDescription = null
                )
            }
        }
        Text(
            pet.desc,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(horizontal = padding)
                .padding(top = 32.dp)
        )
        Spacer(Modifier.height(24.dp))
        AdoptmeButton(
            modifier = Modifier.fillMaxWidth().height(46.dp).padding(horizontal = 16.dp),
            onClick = { /** Not yet implemented */ }
        ) {
            Text("Adopt".toUpperCase(Locale.ROOT))
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun PetSize(
    pet: Pet,
    modifier: Modifier = Modifier,
    colorActive: Color = AdoptmeTheme.colors.primary,
    colorInactive: Color = AdoptmeTheme.colors.textPrimary
) {
    Row(
        modifier = modifier.wrapContentSize(Alignment.BottomStart),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = pet.icon()),
            modifier = Modifier
                .size(24.dp)
                .padding(bottom = 1.dp),
            tint = if (pet.size == Pet.Size.S) colorActive else colorInactive,
            contentDescription = null
        )
        Icon(
            painter = painterResource(id = pet.icon()),
            modifier = Modifier
                .size(32.dp)
                .padding(bottom = 1.dp),
            tint = if (pet.size == Pet.Size.M) colorActive else colorInactive,
            contentDescription = null
        )
        Icon(
            painter = painterResource(id = pet.icon()),
            modifier = Modifier.size(40.dp),
            tint = if (pet.size == Pet.Size.L) colorActive else colorInactive,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PetSizePreview() {
    AdoptmeTheme {
        PetSize(pet = Pet.fake)
    }
}
