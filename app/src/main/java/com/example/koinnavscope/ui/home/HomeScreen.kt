package com.example.koinnavscope.ui.home

import android.Manifest
import android.R
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.koinnavscope.MainActivity
import com.example.koinnavscope.featureA.ui.screenA2.DEEPLINK_URI_A2
import com.example.koinnavscope.featureC.ui.screenC1.DEEPLINK_URI_C1
import com.example.koinnavscope.ui.theme.KoinNavScopeTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToFeatureA: () -> Unit,
    onNavigateToFeatureB: () -> Unit,
    onNavigateToFeatureC: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var showDiPicker by rememberSaveable { mutableStateOf(false) }

    HomeScreen(
        state = state,
        onNavigateToFeatureA = onNavigateToFeatureA,
        onNavigateToFeatureB = onNavigateToFeatureB,
        onNavigateToFeatureC = onNavigateToFeatureC,
        onClickIncrease = viewModel::increaseCounter,
        onClickNotificationIcon = {
            notify(context)
        }
    ) {
        showDiPicker = true
    }

    if (showDiPicker) {
        BasicAlertDialog(
            onDismissRequest = { showDiPicker = false },
            properties = DialogProperties()
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    KoinScope.entries.forEach { di ->
                        val source = remember { MutableInteractionSource() }
                        val onClick = {
                            showDiPicker = false
                            viewModel.setDiScope(di)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = onClick,
                                    interactionSource = source,
                                    indication = ripple()
                                )
                        ) {
                            RadioButton(
                                di == state.di,
                                interactionSource = source,
                                onClick = onClick
                            )
                            Text(di.name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeScreen(
    state: HomeUiState,
    onNavigateToFeatureA: () -> Unit,
    onNavigateToFeatureB: () -> Unit,
    onNavigateToFeatureC: () -> Unit,
    onClickIncrease: () -> Unit,
    onClickNotificationIcon: () -> Unit,
    onClickPickerIcon: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Surface(shadowElevation = 16.dp) {
                CenterAlignedTopAppBar(
                    title = { Text("Home") },
                    actions = {
                        val isNotificationAllow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        } else true
                        IconButton(onClickNotificationIcon, enabled = isNotificationAllow) {
                            Icon(Icons.Rounded.Notifications, "Notifications")
                        }
                        IconButton(onClickPickerIcon) {
                            Icon(Icons.Rounded.Settings, "Settings")
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            LargeFloatingActionButton(onClickIncrease) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) { innerPadding ->
        Column {
            CounterCard(
                counter = state.counter,
                label = "App State",
                modifier = Modifier.padding(innerPadding)
            )
            NavigationList(
                Modifier.padding(24.dp),
                onClickFeatureA = onNavigateToFeatureA,
                onClickFeatureB = onNavigateToFeatureB,
                onClickFeatureC = onNavigateToFeatureC
            )
        }
    }
}


@Composable
private fun CounterCard(
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
private fun CounterText(count: Int) {
    val paddingModifier = Modifier.padding(20.dp)

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
            style = MaterialTheme.typography.displayLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun NavigationList(
    modifier: Modifier = Modifier,
    onClickFeatureA: () -> Unit,
    onClickFeatureB: () -> Unit,
    onClickFeatureC: () -> Unit,
) {
    ElevatedCard(modifier) {
        val startPaddingModifier = Modifier.padding(start = 56.dp)
        Column {
            ListItem(
                headlineContent = {
                    Text("FeatureA Nav Graph")
                },
                leadingContent = {
                    Icon(Icons.Rounded.ThumbUp, "ThumbUp")
                },
                trailingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, "ArrowRight")
                },
                modifier = Modifier.clickable(onClick = onClickFeatureA)
            )
            HorizontalDivider(startPaddingModifier)
            ListItem(
                headlineContent = {
                    Text("FeatureB Nav Graph")
                },
                leadingContent = {
                    Icon(Icons.Rounded.CheckCircle, "CheckCircle")
                },
                trailingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, "ArrowRight")
                },
                modifier = Modifier.clickable(onClick = onClickFeatureB)
            )
            HorizontalDivider(startPaddingModifier)
            ListItem(
                headlineContent = {
                    Text("FeatureC Nav Graph")
                },
                leadingContent = {
                    Icon(Icons.Rounded.ShoppingCart, "ShoppingCart")
                },
                trailingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, "ArrowRight")
                },
                modifier = Modifier.clickable(onClick = onClickFeatureC)
            )
        }
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

@Preview
@Composable
private fun HomeScreenPreview() {
    KoinNavScopeTheme {
        HomeScreen(HomeUiState(1), {}, {}, {}, {}, {}, {})
    }
}

private fun notify(context: Context) {
    val notifier = NotificationManagerCompat.from(context)
    val intent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(
            Intent(
                Intent.ACTION_VIEW,
                listOf(DEEPLINK_URI_A2, DEEPLINK_URI_C1).random().toUri(),
                context,
                MainActivity::class.java
            )
        )
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    val notification =
        NotificationCompat.Builder(context, "Koin")
            .setContentTitle("Deep Link")
            .setContentText("Tap To open a random deep link screen")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setFullScreenIntent(intent, true)
            .setContentIntent(intent)
            .setSmallIcon(R.drawable.ic_secure)
            .build()

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        notifier.notify(
            Random.nextInt(),
            notification
        )
    }
}
