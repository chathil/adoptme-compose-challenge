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
package com.example.androiddevchallenge.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.User
import com.example.androiddevchallenge.model.fake
import com.example.androiddevchallenge.ui.components.AdoptmeScaffold
import com.example.androiddevchallenge.ui.components.AdoptmeSurface
import com.example.androiddevchallenge.ui.components.CircleImage
import com.example.androiddevchallenge.ui.components.UpButton
import com.example.androiddevchallenge.ui.theme.AdoptmeTheme
import com.example.androiddevchallenge.ui.theme.AlphaNearTransparent
import com.example.androiddevchallenge.ui.theme.padding
import com.example.androiddevchallenge.ui.utils.LocalSysUiController

@Composable
fun Account(upPress: () -> Unit) {
    LocalSysUiController.current.setStatusBarColor(
        AdoptmeTheme.colors.uiBackground.copy(
            AlphaNearTransparent
        )
    )
    val user = User.fake
    AdoptmeScaffold { innerPadding ->
        Box {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Header(user)
                Body(Modifier.padding(innerPadding), user)
            }
            UpButton(upPress)
        }
    }
}

@Composable
private fun Body(modifier: Modifier = Modifier, user: User) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = padding)
    ) {
        Text(
            user.fullName,
            style = MaterialTheme.typography.h4,
            color = AdoptmeTheme.colors.textPrimary
        )
        Text(
            user.email,
            style = MaterialTheme.typography.h6,
            color = AdoptmeTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Credits",
            style = MaterialTheme.typography.h4,
            color = AdoptmeTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Unsplash\nfor the awesome pet photos",
            style = MaterialTheme.typography.body1,
            color = AdoptmeTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Freepik\nfor the illustrations @pikisuperstar",
            style = MaterialTheme.typography.body1,
            color = AdoptmeTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "FontAwesome\nfor the pet icons",
            style = MaterialTheme.typography.body1,
            color = AdoptmeTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ill_dog),
            modifier = Modifier.size(216.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun Header(
    user: User
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .padding(bottom = 46.dp)
    ) {
        AdoptmeSurface(shape = CircleShape, modifier = Modifier.offset(y = 90.dp)) {
            Box(
                modifier = Modifier
                    .size(112.dp)
                    .background(AdoptmeTheme.colors.btnPrimary)
            )
        }
        CircleImage(
            asset = painterResource(id = user.photo),
            modifier = Modifier
                .fillMaxWidth()
                .height(316.dp)
                .offset(x = 96.dp)
        )
        AdoptmeSurface(shape = CircleShape, modifier = Modifier.offset(x = 36.dp, y = 196.dp)) {
            Box(
                modifier = Modifier
                    .size(156.dp)
                    .background(AdoptmeTheme.colors.btnPrimary)

            )
        }
    }
}

@Preview
@Composable
fun BodyLightPreview() {
    AdoptmeTheme {
        AdoptmeSurface {
            Body(user = User.fake)
        }
    }
}

@Preview
@Composable
fun BodyDarkPreview() {
    AdoptmeTheme(darkTheme = true) {
        AdoptmeSurface {
            Body(user = User.fake)
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    AdoptmeTheme {
        Header(user = User.fake)
    }
}

@Preview
@Composable
fun AccountPreview() {
    Account {}
}
