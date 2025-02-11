package com.example.koinnavscope.featureC.ui.screenC2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.CheckCircle
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
import com.example.koinnavscope.featureC.ui.screenC1.AppCounterCard
import com.example.koinnavscope.featureC.ui.screenC1.CounterCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScreenC2(
    onClickBack: () -> Unit,
    onClickScreenB2: () -> Unit,
    viewModel: ScreenC2ViewModel = koinViewModel()
) {
    val counter by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(0.75f),
        topBar = {
            Surface(shadowElevation = 16.dp) {
                CenterAlignedTopAppBar(
                    title = { Text("C2") },
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
                label = "FeatureC State",
            )
            AppCounterCard(viewModel.appState)
            Spacer(Modifier.height(40.dp))
            ListItem(
                headlineContent = {
                    Text("ScreenB2")
                },
                leadingContent = {
                    Icon(Icons.Rounded.CheckCircle, "CheckCircle")
                },
                trailingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, "ArrowRight")
                },
                modifier = Modifier.padding(horizontal = 24.dp)
                    .clickable(onClick = onClickScreenB2)
            )
        }
    }
}