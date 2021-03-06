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
package com.example.androiddevchallenge.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.androiddevchallenge.ui.account.Account
import com.example.androiddevchallenge.ui.detail.PetDetail
import com.example.androiddevchallenge.ui.home.Home
import com.example.androiddevchallenge.ui.theme.AdoptmeTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@ExperimentalAnimationApi
@Composable
fun AdoptmeApp(appState: AdoptmeAppState, navigationViewModel: NavigationViewModel) {
    ProvideWindowInsets {
        AdoptmeTheme {
            Crossfade(navigationViewModel.currentScreen) { destination ->
                when (destination) {
                    is Screen.Home -> {
                        Home(
                            navigationViewModel::navigateTo, appState
                        )
                    }
                    is Screen.Detail -> PetDetail(
                        pet = destination.pet,
                        upPress = { navigationViewModel.onBack() },
                        appState = appState
                    )
                    is Screen.Account -> Account(upPress = { navigationViewModel.onBack() })
                }
            }
        }
    }
}
