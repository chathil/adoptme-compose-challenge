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

import androidx.compose.runtime.mutableStateListOf
import com.example.androiddevchallenge.model.Pet

class AdoptmeAppState(initialPets: List<Pet> = listOf()) {

    private val _pets: MutableList<Pet> =
        mutableStateListOf(*initialPets.toTypedArray())

    fun pets(
        types: Set<Pet.Type> = setOf(
            Pet.Type.CAT,
            Pet.Type.DOG,
            Pet.Type.CHAMELEON
        )
    ): List<Pet> = _pets.filter { types.contains(it.type) }

    fun like(pet: Pet) {
        _pets[_pets.indexOf(pet)] = pet.copy(isLiked = !pet.isLiked)
    }
}
