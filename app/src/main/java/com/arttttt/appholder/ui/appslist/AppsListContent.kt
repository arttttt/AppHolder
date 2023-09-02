package com.arttttt.appholder.ui.appslist

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arttttt.appholder.ui.appslist.lazylist.delegates.ActivityListDelegate
import com.arttttt.appholder.ui.appslist.lazylist.delegates.AppListDelegate
import com.arttttt.appholder.ui.appslist.lazylist.delegates.DividerListDelegate
import com.arttttt.appholder.ui.base.ListItem
import com.arttttt.appholder.ui.base.dsl.rememberLazyListDelegateManager
import com.arttttt.appholder.ui.custom.LocalCorrectHapticFeedback
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppsListContent(component: AppListComponent) {
    val state by component.state.subscribeAsState()

    val density = LocalDensity.current

    var listPadding by remember {
        mutableStateOf(PaddingValues(0.dp))
    }

    var parentCoordinates: LayoutCoordinates? by remember {
        mutableStateOf(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                parentCoordinates = coordinates
            }
    ) {
        TopAppBar(
            title = {
                Text(text = "Apps list")
            },
            actions = {
                val context = LocalContext.current

                IconButton(
                    onClick = {
                        Toast.makeText(context, "Under construction", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Default.Settings),
                        contentDescription = null,
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    parentCoordinates = coordinates
                }
        ) {
            AppsList(
                modifier = Modifier.matchParentSize(),
                contentPadding = listPadding,
                apps = state.apps,
                onAppClicked = component::onAppClicked,
                activityClicked = component::activityClicked,
            )

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .onGloballyPositioned { coordinates ->
                        parentCoordinates ?: return@onGloballyPositioned
                        if (listPadding.calculateBottomPadding() > 0.dp) return@onGloballyPositioned

                        listPadding = PaddingValues(
                            bottom = with(density) {
                                (parentCoordinates!!.size.height - coordinates.boundsInParent().top)
                                    .coerceAtLeast(0f)
                                    .toDp()
                            }
                        )
                    }
                    .align(Alignment.BottomEnd),
                onClick = component::startApps,
                text = {
                    Text(
                        text = "Start apps",
                    )
                },
                icon = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.PlayArrow),
                        contentDescription = null,
                    )
                }
            )
        }
    }
}

@Composable
private fun AppsList(
    modifier: Modifier,
    contentPadding: PaddingValues,
    apps: List<ListItem>,
    onAppClicked: (String) -> Unit,
    activityClicked: (String, String) -> Unit,
) {
    val hapticFeedback = LocalCorrectHapticFeedback.current

    val lazyListDelegateManager = rememberLazyListDelegateManager(
        delegates = persistentListOf(
            AppListDelegate(
                onClick = onAppClicked
            ),
            ActivityListDelegate(
                onClick = { pkg, name ->
                    hapticFeedback.performHapticFeedback(HapticFeedbackConstantsCompat.VIRTUAL_KEY)
                    activityClicked.invoke(pkg, name)
                },
            ),
            DividerListDelegate(),
        ),
    )

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(
            items = apps,
            key = lazyListDelegateManager::getKey,
            contentType = lazyListDelegateManager::getContentType
        ) { item ->
            lazyListDelegateManager.Content(
                context = this,
                item = item,
                modifier = Modifier,
            )
        }
    }
}