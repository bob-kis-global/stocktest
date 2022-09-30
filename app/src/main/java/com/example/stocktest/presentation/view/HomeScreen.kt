package com.example.stocktest.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocktest.R
import com.example.stocktest.data.Status
import com.example.stocktest.data.model.Ticker
import com.example.stocktest.presentation.viewmodel.MainViewModel
import com.example.stocktest.utils.ColorUtil
import com.example.stocktest.utils.NumberUtil
import timber.log.Timber

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    val tag = "HomeScreen-bob"
    Timber.tag(tag).d("HomeScreen(0)")

    val watchList by viewModel.watchList.observeAsState()

    Timber.tag(tag).d("HomeScreen(1) ${watchList?.status}, $watchList")

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
            viewModel.getMainData()
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

            var isClicked = remember { mutableStateOf(true) }
            LazyColumn {
                items(tickers) { ticker ->
                    MainTicker(ticker, isClicked)
                    Divider(color = Color(0xF000000))
                }
            }
        }
    }
}

@Composable
fun MainTicker(ticker: Ticker, isClicked: MutableState<Boolean>) {
    Box(modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxWidth()) {

        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Row {

                Image(
                    ColorPainter(ColorUtil.getMarketColor(ticker)),
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = ticker.s,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = ticker.marketData?.let { it.n1 } ?: "",
                fontSize = 12.sp,
                color = Color(0x80000000)
            )
        }

        val color = ColorUtil.getStockColor(ticker)

        Row(
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                val c = NumberUtil.getNumberFormat(ticker.c.toLong())
                val va = NumberUtil.compactDecimalFormat(ticker.va)

                Text(text = if(isClicked.value) c else va,
                    fontSize = 16.sp,
                    color = color,
                    modifier = Modifier.align(Alignment.End)
                        .clickable(onClick = { isClicked.value = !isClicked.value })
                )

                Text(text = "${NumberUtil.getNumberFormat(ticker.ch.toLong())}, ${ticker.ra}%",
                    fontSize = 12.sp,
                    color = color,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            CandleView(color, ticker.c, ticker.o, ticker.l, ticker.h)
        }
    }
}