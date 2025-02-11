package com.example.koinnavscope.featureB.ui.screenB2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.koinnavscope.featureB.ui.screenB1.AppCounterCard
import com.example.koinnavscope.featureB.ui.screenB1.CounterCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScreenB2(
    onClickBack: () -> Unit,
    onClickHome: () -> Unit,
    viewModel: ScreenB2ViewModel
) {
    val counter by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            Surface(shadowElevation = 16.dp) {
                CenterAlignedTopAppBar(
                    title = { Text("B2") },
                    navigationIcon = {
                        IconButton(onClickBack) {
                            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowLeft, "ArrowLeft")
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            LargeFloatingActionButton(viewModel::increaseCounter) {
                Icon(Icons.Filled.Add, "Add")
            }
        },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            CounterCard(
                counter = counter,
                label = "FeatureB State",
            )
            AppCounterCard(viewModel.appState)
            Spacer(Modifier.height(40.dp))
            ListItem(
                headlineContent = {
                    Text("Back To Home")
                },
                leadingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowLeft, "KeyboardArrowLeft")
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable(onClick = onClickHome)
            )
        }
    }
}