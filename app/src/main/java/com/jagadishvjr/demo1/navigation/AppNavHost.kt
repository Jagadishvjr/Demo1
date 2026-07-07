package com.jagadishvjr.demo1.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jagadishvjr.demo1.R
import com.jagadishvjr.demo1.features.fixtures.presentation.FixturesRoute
import com.jagadishvjr.demo1.features.users.presentation.UsersRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (currentRoute == AppDestination.Fixtures.route) {
                            stringResource(R.string.fixtures_screen_title)
                        } else {
                            stringResource(R.string.users_screen_title)
                        }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                AppDestination.entries.forEach { destination ->
                    val selected = backStackEntry?.destination?.hierarchy?.any {
                        it.route == destination.route
                    } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(
                                text = when (destination) {
                                    AppDestination.Users -> stringResource(R.string.users_tab_label)
                                    AppDestination.Fixtures -> stringResource(R.string.fixtures_tab_label)
                                }
                            )
                        },
                        icon = {}
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Users.route
        ) {
            composable(AppDestination.Users.route) {
                UsersRoute(paddingValues = paddingValues)
            }
            composable(AppDestination.Fixtures.route) {
                FixturesRoute(paddingValues = paddingValues)
            }
        }
    }
}
