package com.example.koinnavscope.featureC.ui.screenC1

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScreenC1(
    onClickBack: () -> Unit,
    onClickScreenC2: () -> Unit,
    viewModel: ScreenC1ViewModel = koinViewModel()
) {
    val counter by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Surface(shadowElevation = 16.dp) {
                CenterAlignedTopAppBar(
                    title = { Text("C1") },
                    navigationIcon = {
                        IconButton(onClickBack) {
                            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowLeft, "ArrowLeft")
                        }
                    },
                    windowInsets = TopAppBarDefaults.windowInsets.only(WindowInsetsSides.Horizontal)
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
                    Text("ScreenC2")
                },
                trailingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, "ArrowRight")
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable(onClick = onClickScreenC2)
            )
        }
    }
}


@Composable
internal fun CounterCard(
    counter: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(label, style = MaterialTheme.typography.headlineLarge)
            CounterText(counter)
        }
    }
}

@Composable
internal fun AppCounterCard(
    counter: Int,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("App State", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.weight(1f))
            CounterText(
                counter, 0.dp, MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
private fun CounterText(
    count: Int,
    padding: Dp = 20.dp,
    style: TextStyle = MaterialTheme.typography.displayLarge.copy(
        color = MaterialTheme.colorScheme.primary
    )
) {
    val paddingModifier: Modifier = Modifier.padding(padding)
    AnimatedContent(
        targetState = count.toString(),
        transitionSpec = {
            slideInVertically(animationSpec = tween(90, delayMillis = 90)) {
                it
            } togetherWith slideOutVertically(animationSpec = tween(90)) {
                -it
            }
        }
    ) { state ->
        Text(
            text = state,
            modifier = paddingModifier,
            style = style
        )
    }
}

@Composable
fun ElevatedCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        content()
    }
}