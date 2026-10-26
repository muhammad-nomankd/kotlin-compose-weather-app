package com.felippeneves.kotlin_compose_weather_app.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felippeneves.kotlin_compose_weather_app.R
import com.felippeneves.kotlin_compose_weather_app.presentation.navigation.NavigationManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    subtitle: String = "",
    showBackButton: Boolean = true,
    showSearchButton: Boolean = false,
    showFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!showBackButton) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location Pin",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (subtitle.isNotEmpty()) {
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = { NavigationManager.navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.favorites),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            },
            actions = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    if (showSearchButton) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(40.dp)
                        ) {
                            IconButton(onClick = { NavigationManager.navigateToSearchCities() }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    if (showFavoriteButton) {
                        val icon = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder
                        val tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(40.dp)
                        ) {
                            IconButton(onClick = onFavoriteClick) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "Favorite",
                                    tint = tint,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Box {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(40.dp)
                        ) {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            shape = RoundedCornerShape(16.dp),
                            containerColor = MaterialTheme.colorScheme.surface
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                        Text(
                                            text = stringResource(id = R.string.favorites),
                                            modifier = Modifier.padding(start = 12.dp),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    NavigationManager.navigateToFavoriteCities()
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = stringResource(id = R.string.settings),
                                            modifier = Modifier.padding(start = 12.dp),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    NavigationManager.navigateToSettings()
                                }
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CustomTopAppBar(
        title = "Akora Khattak",
        subtitle = "Khyber Pakhtunkhwa, Pakistan",
        showBackButton = false,
        showSearchButton = true,
        showFavoriteButton = true,
        isFavorite = false,
        onFavoriteClick = {}
    )
}
