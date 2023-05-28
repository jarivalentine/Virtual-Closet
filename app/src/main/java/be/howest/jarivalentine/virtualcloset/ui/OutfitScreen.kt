package be.howest.jarivalentine.virtualcloset.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarivalentine.virtualcloset.R
import be.howest.jarivalentine.virtualcloset.data.Outfit
import be.howest.jarivalentine.virtualcloset.ui.theme.Shapes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import java.util.Calendar

val labels = listOf("summer", "winter", "spring", "autumn")

@Composable
fun OutfitScreen(
    viewModel: VirtualClosetViewModel
) {
    val uiState by viewModel.virtualClosetUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SearchField()
        Outfits(
            outfits = uiState.outfitList
        )
    }
}

@Composable
fun SearchField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        label = { Text(text = stringResource(R.string.search)) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface
        ),
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = ""
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Outfits(
    outfits: List<Outfit>
) {
    SlidingCarousel(
        itemsCount = outfits.size,
        itemContent = { index ->
            Outfit(outfits[index])
        }
    )
}

@Composable
fun Outfit(outfit: Outfit) {
    Card {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.outfit2),
                contentDescription = outfit.name,
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp - 20.dp)
                    .height(screenHeight - 250.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp - 20.dp)
                    .align(Alignment.BottomStart)
                    .background(MaterialTheme.colors.onSecondary.copy(alpha = 0.7f)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        text = outfit.name,
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                    Labels()
                }
                IntentOptions(outfit.name)
            }
        }
    }
}

@Composable
fun IntentOptions(name: String) {
    val context = LocalContext.current;
    Icon(
        imageVector = Icons.Filled.Share,
        contentDescription = "More",
        modifier = Modifier
            .size(50.dp)
            .padding(10.dp)
            .clickable {
                val shareText = "I am wearing $name today!"
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                val shareIntent = Intent.createChooser(intent, null)

                context.startActivity(shareIntent)
            },
        tint = MaterialTheme.colors.onSurface
    )
    Icon(
        imageVector = Icons.Filled.DateRange,
        contentDescription = "More",
        modifier = Modifier
            .size(50.dp)
            .padding(10.dp)
            .clickable {
                showDatePickerDialog(context, name)
            },
        tint = MaterialTheme.colors.onSurface
    )
}

private fun showDatePickerDialog(context: Context, outfitName: String) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)

            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Wear $outfitName today")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Outfit planned")
                .putExtra(CalendarContract.Events.DESCRIPTION, "This outfit is planned for today")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, selectedDate.timeInMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, selectedDate.timeInMillis + 2 * 60 * 60 * 1000)
                .putExtra(CalendarContract.Events.ALL_DAY, false)

            val calendarIntent = Intent.createChooser(intent, null)
            context.startActivity(calendarIntent)
        },
        year,
        month,
        day
    )

    datePickerDialog.show()
}

@Composable
fun Labels() {
    Row() {
        Label(labels[2])
        Label("Available")
    }
}

@Composable
fun Label(name: String) {
    Box(
        modifier = Modifier
            .padding(
                start = 10.dp,
                top = 10.dp,
                bottom = 10.dp,
                end = if (name == labels.last()) 10.dp else 0.dp
            )
            .background(shape = Shapes.small, color = MaterialTheme.colors.secondary)
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = name,
            color = MaterialTheme.colors.onSecondary,
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SlidingCarousel(
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }
        Surface(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .align(Alignment.CenterHorizontally),
            shape = CircleShape,
            color = MaterialTheme.colors.primaryVariant
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = MaterialTheme.colors.onSecondary,
    unSelectedColor: Color = Color.DarkGray,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}
