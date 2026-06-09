package hr.dtakac.prognoza.ui.forecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import hr.dtakac.prognoza.R
import hr.dtakac.prognoza.presentation.asString
import hr.dtakac.prognoza.presentation.forecast.CurrentUi
import hr.dtakac.prognoza.presentation.forecast.DayHourUi
import hr.dtakac.prognoza.presentation.forecast.ForecastState
import hr.dtakac.prognoza.ui.theme.Manrope
import hr.dtakac.prognoza.ui.theme.asWeatherIconResId

private val detailGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF8A63DD), Color(0xFF5C3AA6))
)
private val cardTranslucent = Color(0x30FFFFFF)
private val bottomNavColor = Color(0xFF6A4BB0)
private val navPillColor = Color(0xFFD9EFDF)
private val navPillText = Color(0xFF2E7D4F)
private val textPrimary = Color.White
private val textSecondary = Color(0xFFEDE7FB)

@Composable
fun ForecastContent(
    state: ForecastState,
    onMenuClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(detailGradient)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_location),
                        contentDescription = null,
                        tint = textPrimary
                    )
                }
            }

            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = textPrimary,
                    trackColor = cardTranslucent
                )
            }

            val forecast = state.forecast
            if (forecast == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error?.asString() ?: "",
                        color = textPrimary,
                        fontFamily = Manrope,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 18.dp)
                        .padding(top = 4.dp, bottom = 18.dp)
                ) {
                    TodayCard(
                        current = forecast.current,
                        lowHigh = forecast.today?.lowHighTemperature?.asString()
                    )

                    forecast.today?.hourly?.takeIf { it.isNotEmpty() }?.let { hours ->
                        Spacer(modifier = Modifier.height(16.dp))
                        HourlyCard(hours = hours)
                    }
                }

                BottomBar(
                    onPlacesClick = onMenuClick,
                    onSettingsClick = onSettingsClick
                )
            }
        }
    }
}

@Composable
private fun TodayCard(
    current: CurrentUi,
    lowHigh: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(cardTranslucent)
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Today",
            color = textPrimary,
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = current.weatherIconDescription.asWeatherIconResId()
                ),
                contentDescription = null,
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = current.temperature.asString(),
                color = textPrimary,
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                fontSize = 52.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            text = current.description.asString(),
            color = textPrimary,
            fontFamily = Manrope,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 6.dp)
        )
        Text(
            text = current.place.asString(),
            color = textSecondary,
            fontFamily = Manrope,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = current.date.asString(),
            color = textSecondary,
            fontFamily = Manrope,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Column(
            modifier = Modifier.padding(top = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = current.wind.asString(),
                color = textSecondary,
                fontFamily = Manrope,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = current.feelsLike.asString(),
                color = textSecondary,
                fontFamily = Manrope,
                fontSize = 13.sp
            )
        }

        lowHigh?.takeIf { it.isNotBlank() }?.let { lowHighText ->
            val parts = lowHighText.split("/")
            if (parts.size == 2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "High: ${parts[0].trim()}",
                        color = textSecondary,
                        fontFamily = Manrope,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Text(
                        text = "Low: ${parts[1].trim()}",
                        color = textSecondary,
                        fontFamily = Manrope,
                        fontSize = 13.sp
                    )
                }
            } else {
                Text(
                    text = lowHighText,
                    color = textSecondary,
                    fontFamily = Manrope,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun HourlyCard(hours: List<DayHourUi>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(cardTranslucent)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.hourly),
            color = textPrimary,
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            hours.forEach { hour ->
                HourColumn(hour = hour)
            }
        }
    }
}

@Composable
private fun HourColumn(hour: DayHourUi) {
    Column(
        modifier = Modifier.padding(end = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hour.time.asString(),
            color = textSecondary,
            fontFamily = Manrope,
            fontSize = 12.sp
        )
        Image(
            painter = rememberAsyncImagePainter(
                model = hour.weatherIconDescription.asWeatherIconResId()
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .size(28.dp)
        )
        Text(
            text = hour.temperature.asString(),
            color = textPrimary,
            fontFamily = Manrope,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun BottomBar(
    onPlacesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
            .background(bottomNavColor)
            .navigationBarsPadding()
            .height(74.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
                .background(navPillColor)
                .clickableNoRipple(onPlacesClick)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                tint = navPillText,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Places",
                color = navPillText,
                fontFamily = Manrope,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 6.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple(onSettingsClick),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = null,
                tint = textSecondary,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = stringResource(id = R.string.settings),
                color = textSecondary,
                fontFamily = Manrope,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier =
    composed {
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    }
