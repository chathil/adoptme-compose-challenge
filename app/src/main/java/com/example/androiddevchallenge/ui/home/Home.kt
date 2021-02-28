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
package com.example.androiddevchallenge.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Pet
import com.example.androiddevchallenge.model.fake
import com.example.androiddevchallenge.model.icon
import com.example.androiddevchallenge.model.image
import com.example.androiddevchallenge.ui.AdoptmeAppState
import com.example.androiddevchallenge.ui.Screen
import com.example.androiddevchallenge.ui.components.AdoptmeButton
import com.example.androiddevchallenge.ui.components.AdoptmeCard
import com.example.androiddevchallenge.ui.components.AdoptmeProgressBar
import com.example.androiddevchallenge.ui.components.AdoptmeScaffold
import com.example.androiddevchallenge.ui.components.AdoptmeSurface
import com.example.androiddevchallenge.ui.components.Chip
import com.example.androiddevchallenge.ui.components.CircleImage
import com.example.androiddevchallenge.ui.theme.AdoptmeTheme
import com.example.androiddevchallenge.ui.theme.AlphaNearTransparent
import com.example.androiddevchallenge.ui.theme.padding
import com.example.androiddevchallenge.ui.utils.LocalSysUiController
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalAnimationApi
@Composable
fun Home(
    navigateTo: (Screen) -> Unit,
    appState: AdoptmeAppState
) {
    LocalSysUiController.current.setStatusBarColor(
        AdoptmeTheme.colors.uiBackground.copy(
            AlphaNearTransparent
        )
    )
    var filters by remember {
        mutableStateOf(
            setOf(
                Pet.Type.CAT,
                Pet.Type.DOG,
                Pet.Type.CHAMELEON
            )
        )
    }
    AdoptmeScaffold { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)

        ) {

            AdoptmeCard(shape = RectangleShape) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(horizontal = padding)
                            .height(16.dp)
                    )
                    AccountSection(
                        name = "Chathil",
                        Modifier.padding(horizontal = padding)
                    ) { navigateTo(Screen.Account) }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            PetFilter(
                modifier = Modifier.padding(start = 16.dp),
                filters,
                onFilterChange = { if (filters.contains(it) && filters.size > 1) filters -= it else filters += it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                appState.pets(filters).forEach {
                    PetCard(
                        pet = it,
                        onLikeClick = { pet -> appState.like(pet) },
                        onPetClick = { pet ->
                            navigateTo(Screen.Detail(pet))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PetFilter(
    modifier: Modifier = Modifier,
    filters: Set<Pet.Type>,
    onFilterChange: (Pet.Type) -> Unit
) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        PetChip(
            modifier = Modifier.clickable { onFilterChange(Pet.Type.CAT) },
            selected = filters.contains(Pet.Type.CAT),
            petIcon = painterResource(id = R.drawable.icn_cat),
            petType = "Cat"
        )
        PetChip(
            modifier = Modifier.clickable { onFilterChange(Pet.Type.DOG) },
            selected = filters.contains(Pet.Type.DOG),
            petIcon = painterResource(id = R.drawable.icn_dog),
            petType = "Dog"
        )
        PetChip(
            modifier = Modifier.clickable { onFilterChange(Pet.Type.CHAMELEON) },
            selected = filters.contains(Pet.Type.CHAMELEON),
            petIcon = painterResource(id = R.drawable.icn_otter),
            petType = "Chameleon"
        )
    }
}

@Composable
private fun PetChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    petIcon: Painter,
    petType: String
) {
    Chip(
        modifier = modifier,
        selected = selected,
        start = {
            Icon(
                painter = petIcon,
                modifier = Modifier.size(16.dp),
                contentDescription = null
            )
        },
        content = {
            Text(
                petType
            )
        }
    )
}

@ExperimentalAnimationApi
@Composable
private fun AccountSection(
    name: String,
    modifier: Modifier = Modifier,
    onAccountClicked: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    Column(modifier = Modifier.wrapContentHeight()) {
        Row(modifier = modifier.wrapContentHeight(Alignment.CenterVertically)) {
            Column(Modifier.weight(1F)) {
                Text(
                    name,
                    style = MaterialTheme.typography.h4,
                    color = AdoptmeTheme.colors.textPrimary
                )
                Text(
                    "Lorem ipsum dolor sit amet,consectetur adipiscing elit, sed do",
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.height(8.dp))
                AdoptmeButton(onClick = onAccountClicked) {
                    Row {
                        ProvideTextStyle(value = MaterialTheme.typography.body1) {
                            Text("Account")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            tint = AdoptmeTheme.colors.btnContent,
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(Modifier.width(16.dp))
            CircleImage(modifier = Modifier.size(116.dp), painterResource(id = R.drawable.me))
        }
        Spacer(Modifier.height(16.dp))
        AnimatedVisibility(
            visible = visible,
            enter = expandVertically(),
            exit = shrinkVertically(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AccountCompletion(modifier = Modifier.padding(start = 16.dp))
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { visible = false },
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun AccountCompletion(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Complete Your Profile", modifier = Modifier.padding(bottom = 8.dp))
        AdoptmeProgressBar(progress = 0.7f, modifier = Modifier.width(216.dp))
    }
}

@Composable
private fun PetCard(
    pet: Pet,
    modifier: Modifier = Modifier,
    onPetClick: (Pet) -> Unit,
    onLikeClick: (Pet) -> Unit
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(116.dp)
            .clickable(onClick = { onPetClick(pet) })
    ) {
        AdoptmeSurface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.size(116.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                painter = painterResource(id = pet.image(LocalContext.current)),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    pet.name,
                    style = MaterialTheme.typography.h6,
                    color = AdoptmeTheme.colors.textPrimary
                )
                Text(pet.location, style = MaterialTheme.typography.body2)
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = pet.icon()
                    ),
                    tint = AdoptmeTheme.colors.primary,
                    modifier = Modifier.size(20.dp),
                    contentDescription = null
                )
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
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@ExperimentalAnimationApi
@Preview
@Composable
fun AccountSectionLightPreview() {
    AdoptmeTheme {
        AccountSection(name = "Chathil") {}
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun AccountSectionDarkPreview() {
    AdoptmeTheme(darkTheme = true) {
        AdoptmeSurface {
            AccountSection(name = "Chathil") {}
        }
    }
}

@Preview
@Composable
fun PetCardLightPreview() {
    AdoptmeTheme {
        PetCard(Pet.fake, onPetClick = {}, onLikeClick = {})
    }
}

@Preview
@Composable
fun PetCardDarkPreview() {
    AdoptmeTheme(darkTheme = true) {
        AdoptmeSurface {
            PetCard(Pet.fake, onPetClick = {}, onLikeClick = {})
        }
    }
}
