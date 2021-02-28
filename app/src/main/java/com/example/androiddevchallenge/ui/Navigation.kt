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

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import com.example.androiddevchallenge.model.Pet
import com.example.androiddevchallenge.ui.Screen.Account
import com.example.androiddevchallenge.ui.Screen.Detail
import com.example.androiddevchallenge.ui.Screen.Home
import com.example.androiddevchallenge.ui.ScreenName.ACCOUNT
import com.example.androiddevchallenge.ui.ScreenName.DETAIL
import com.example.androiddevchallenge.ui.ScreenName.HOME
import com.example.androiddevchallenge.ui.utils.getMutableStateOf

enum class ScreenName { HOME, DETAIL, ACCOUNT }

sealed class Screen(val id: ScreenName) {
    object Home : Screen(HOME)
    data class Detail(val pet: Pet) : Screen(DETAIL)
    object Account : Screen(ACCOUNT)
}

private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_PET = "pet"

private fun Screen.toBundle(): Bundle {
    return bundleOf(SIS_NAME to id.name).also {
        // add extra keys for various types here
        if (this is Detail) {
            it.putParcelable(SIS_PET, pet)
        }
    }
}

private fun Bundle.toScreen(): Screen {
    return when (ScreenName.valueOf(getStringOrThrow(SIS_NAME))) {
        HOME -> Home
        DETAIL -> {
            val pet = getParcelable<Pet>(SIS_PET)!!
            Detail(pet)
        }
        ACCOUNT -> {
            Account
        }
    }
}

private fun Bundle.getStringOrThrow(key: String) =
    requireNotNull(getString(key)) { "Missing key '$key' in $this" }

class NavigationViewModel(savedStateHandle: androidx.lifecycle.SavedStateHandle) : androidx.lifecycle.ViewModel() {
    /**
     * Hold the current screen in an observable, restored from savedStateHandle after process
     * death.
     *
     * mutableStateOf is an observable similar to LiveData that's designed to be read by compose. It
     * supports observability via property delegate syntax as shown here.
     */
    var currentScreen: Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = SIS_SCREEN,
        default = Home,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set // limit the writes to only inside this class.

    /**
     * Go back (always to [Home]).
     *
     * Returns true if this call caused user-visible navigation. Will always return false
     * when [currentScreen] is [Home].
     */
    @androidx.annotation.MainThread
    fun onBack(): Boolean {
        val wasHandled = currentScreen != Home
        currentScreen = Home
        return wasHandled
    }

    /**
     * Navigate to requested [Screen].
     *
     * If the requested screen is not [Home], it will always create a back stack with one element:
     * ([Home] -> [screen]). More back entries are not supported in this app.
     */
    @androidx.annotation.MainThread
    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}
