package com.example.stocktest.presentation.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stocktest.*
import com.example.stocktest.R
import com.example.stocktest.data.Status
import com.example.stocktest.data.model.Ticker
import com.example.stocktest.presentation.view.MainActivity.Companion.TAG
import com.example.stocktest.presentation.viewmodel.LoginViewModel
import com.example.stocktest.presentation.viewmodel.MainViewModel
import com.example.stocktest.ui.theme.StocktestTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StocktestTheme {
                ApplicationSwitcher {
                    // TODO
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.tag(TAG).d("onConfigurationChanged()")
    }

    companion object {
        const val TAG = "MainActivity-bob"
    }
}

fun showToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

@Composable
fun ApplicationSwitcher(viewModel: LoginViewModel = viewModel(), content: @Composable () -> Unit) {
    Timber.tag(TAG).d("ApplicationSwitcher(0)")

    val user by viewModel.user.observeAsState()
    Timber.tag(TAG).d("ApplicationSwitcher(1) user = ${user?.status}")

    when (user?.status) {
        Status.LOADING -> {
            CircularProgress()
        }
        Status.SUCCESS -> {
            if (user?.data == null) {
                LoginScreen()
            } else {
                MainScreenView()
            }
        }
        Status.ERROR -> {

        }
        null -> {
            LoginScreen()
        }
    }
}

@Composable
fun CircularProgress() {
    Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun MainScreenView(viewModel: LoginViewModel = viewModel()) {
    Timber.tag("bob").d("MainScreenView(0)")
    val user by viewModel.user.observeAsState()
    Timber.tag(TAG).d("MainScreenView(1) user = ${user?.status}")

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
    val items = listOf(
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
fun HomeScreen(viewModel: MainViewModel = viewModel()) {

    Timber.tag(TAG).d("HomeScreen(0)")

    val watchList by viewModel.watchList.observeAsState()

    Timber.tag(TAG).d("HomeScreen(1) ${watchList?.status}, $watchList")

    when (watchList?.status) {
        Status.LOADING -> {
            CircularProgress()
        }
        Status.SUCCESS -> {
            watchList?.data?.let {
                MainTickerList(it)
            }
        }
        Status.ERROR -> {
            DefaultScreen(stringResource(id = R.string.home_screen))
        }
        null -> {
            viewModel.getLatestSymbols()
        }
    }
}

@Composable
fun MainTickerList(tickers: List<Ticker>) {
    Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(20.dp, 0.dp)) {
            Text(
                text = "Watch list",
                modifier = Modifier.padding(start = 4.dp, top = 24.dp, end = 4.dp, bottom = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp

            )
            Divider(color = Color.LightGray)
            LazyColumn {
                items(tickers) { ticker ->
                    MainTicker(ticker.s, "description")
                    Divider(color = Color(0xF000000))
                }
            }
        }
    }
}

@Composable
fun MainTicker(title: String, description: String) {
    Column(modifier = Modifier.padding(all = 8.dp)) {
        Row {
            Image(
                ColorPainter(Color.Blue),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = title,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(2.dp))
        Text(text = description,
            fontSize = 16.sp,
            color = Color(0x80000000)
        )
    }
}

@Composable
fun DefaultScreen(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
//        }
    }
}

@Composable
fun SearchScreen() {
    Timber.tag(TAG).d("SearchScreen()")
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
    Timber.tag("bob").d("NavigationGraph(0) = $navController")
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        Timber.tag("bob").d("NavigationGraph(1) = ${BottomNavItem.Home.screenRoute}")
        composable(BottomNavItem.Home.screenRoute) {
            Timber.tag("bob").d("NavigationGraph(2)")
            val viewModel = hiltViewModel<MainViewModel>()
            HomeScreen(viewModel)
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
        MainTickerList(
            listOf(
                Ticker(s = "VIB", t = "STOCK", o = 21450, h = 21800, l = 21050, c = 21400, a = 3202.95, ch = -900, ra = -4.04, vo = 469600, va = 1504105000.0, mv = 100, ss = "ATC",
                    bb = listOf(Ticker.CPV(p = 21350, v = 9100, c = 0), Ticker.CPV(p = 21300, v = 21800, c = 0), Ticker.CPV(p = 21250, v = 10600, c = 0)),
                    bo = listOf(Ticker.CPV(p = 21400, v = 2300, c = 0), Ticker.CPV(p = 21500, v = 25000, c = 0), Ticker.CPV(p = 21550, v = 18600, c = 0)),
                    fr = Ticker.FR(bv = 0, sv = 0, tr = 432072953.0, cr = 0.0), pva = 0, pvo = 0,
                    ba = null, ic = null, mb = null, tb = null, tc = null, to = null, tuc = null
                ),
                Ticker(s = "SSI", t = "STOCK", o = 21450, h = 21800, l = 21050, c = 21400, a = 3202.95, ch = -900, ra = -4.04, vo = 469600, va = 1504105000.0, mv = 100, ss = "ATC",
                    bb = listOf(Ticker.CPV(p = 21350, v = 9100, c = 0), Ticker.CPV(p = 21300, v = 21800, c = 0), Ticker.CPV(p = 21250, v = 10600, c = 0)),
                    bo = listOf(Ticker.CPV(p = 21400, v = 2300, c = 0), Ticker.CPV(p = 21500, v = 25000, c = 0), Ticker.CPV(p = 21550, v = 18600, c = 0)),
                    fr = Ticker.FR(bv = 0, sv = 0, tr = 432072953.0, cr = 0.0), pva = 0, pvo = 0,
                    ba = null, ic = null, mb = null, tb = null, tc = null, to = null, tuc = null
                )
            )
        )
    }
}
