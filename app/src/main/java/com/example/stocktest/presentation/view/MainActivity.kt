package com.example.stocktest.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stocktest.*
import com.example.stocktest.R
import com.example.stocktest.presentation.viewmodel.UserState
import com.example.stocktest.presentation.viewmodel.UserStateViewModel
import com.example.stocktest.ui.theme.StocktestTheme
import com.example.stocktest.ui.theme.TextFieldTextColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userState by viewModels<UserStateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(UserState provides userState) {
                ApplicationSwitcher()
            }
        }
    }
}

@Composable
fun ApplicationSwitcher() {
    val vm = UserState.current
    if (vm.isLoggedIn) {
        StocktestTheme {
            MainScreenView()
        }
    } else {
        LoginScreen()
    }
}

@Composable
fun LoginScreen() {

    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    val vm = UserState.current

    Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colors.background){
        Column (modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            if (vm.isBusy) {
                CircularProgressIndicator()
            } else {
                TextField(value = id,
                    onValueChange = { newInput -> id = newInput },
                    label = {Text(text = "ID",color = MaterialTheme.colors.TextFieldTextColor)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .padding(top = 25.dp)
                )

                TextField(value = pw,
                    onValueChange = { newInput -> pw = newInput },
                    label = { Text(text = "Password",color = MaterialTheme.colors.TextFieldTextColor)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .padding(top = 25.dp)
                )

                Button(onClick = {
                    vm.signIn(id, pw)
                },modifier = Modifier
                    .padding(top = 25.dp)
                    .requiredWidth(277.dp)){
                    Text(text = "Sign In")
                }
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        Box(Modifier.padding(it)){
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf<BottomNavItem>(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Investment,
        BottomNavItem.Assets,
        BottomNavItem.More
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            text = stringResource(id = R.string.home_screen),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SearchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant)
    ) {
        Text(
            text = stringResource(id = R.string.search_screen),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun InvestmentScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {
        Text(
            text = stringResource(id = R.string.investment_screen),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AssetsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondaryVariant)
    ) {
        Text(
            text = stringResource(id = R.string.assets_screen),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun MoreScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.error)
    ) {
        Text(
            text = stringResource(id = R.string.more_screen),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen()
        }
        composable(BottomNavItem.Search.screenRoute) {
            SearchScreen()
        }
        composable(BottomNavItem.Investment.screenRoute) {
            InvestmentScreen()
        }
        composable(BottomNavItem.Assets.screenRoute) {
            AssetsScreen()
        }
        composable(BottomNavItem.More.screenRoute) {
            MoreScreen()
        }
    }
}


sealed class BottomNavItem(val title: Int, val icon: Int, val screenRoute: String) {
    object Home : BottomNavItem(R.string.home_screen, R.drawable.ic_baseline_home_24, HOME)
    object Search : BottomNavItem(R.string.search_screen, R.drawable.ic_baseline_search_24, SEARCH)
    object Investment : BottomNavItem(R.string.investment_screen, R.drawable.ic_baseline_star_24, INVESTMENT)
    object Assets : BottomNavItem(R.string.assets_screen, R.drawable.ic_baseline_monetization_on_24, ASSETS)
    object More : BottomNavItem(R.string.more_screen, R.drawable.ic_baseline_more_horiz_24, MORE)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StocktestTheme {
        LoginScreen()
    }
}