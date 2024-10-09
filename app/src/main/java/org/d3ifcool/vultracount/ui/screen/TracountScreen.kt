package org.d3ifcool.vultracount.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3ifcool.vultracount.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_black),
)
val poppinsFontFamily2 = FontFamily(
    Font(R.font.poppins_regular),
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracountScreen(modifier: Modifier, context: Context) {
    val vehicleCounts = remember { mutableStateMapOf<String, Int>() }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.White
            ),
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    vehicleCounts.clear()
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    shareVehicleData(vehicleCounts, context)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
            }
        )
        Column(modifier = Modifier.fillMaxSize()) {
            VehicleCategory(
                title = "Pribadi",
                vehicles = listOf("Mobil", "Motor"),
                images = listOf(R.drawable.mobilpribadi, R.drawable.motor),
                colors = listOf(Color(0xFF8B0000), Color(0xFF006400)),
                vehicleCounts = vehicleCounts
            )
            VehicleCategory(
                title = "Publik",
                vehicles = listOf("Bus Kecil", "Bus Medium", "Bus Besar"),
                images = listOf(R.drawable.buskecil, R.drawable.busmedium, R.drawable.busbesar),
                colors = listOf(Color(0xFF8B008B), Color(0xFF008B8B), Color(0xFFFF8C00)),
                vehicleCounts = vehicleCounts
            )
            VehicleCategory(
                title = "Barang",
                vehicles = listOf("Truk Kecil", "Truk Medium", "Truk Besar"),
                images = listOf(R.drawable.truckkecil, R.drawable.truckmedium, R.drawable.truckbesar),
                colors = listOf(Color(0xFF00008B), Color(0xFF8B0000), Color(0xFF006400)),
                vehicleCounts = vehicleCounts
            )
        }
    }
}
@Composable
fun VehicleCategory(
    title: String,
    vehicles: List<String>,
    images: List<Int>,
    colors: List<Color>,
    vehicleCounts: MutableMap<String, Int>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = poppinsFontFamily2,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(vehicles.chunked(2)) { vehicleChunk ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                vehicleChunk.forEach { vehicle ->
                    val imageIndex = vehicles.indexOf(vehicle)
                    val color = colors[imageIndex]

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(color)
                            .padding(8.dp)
                            .height(95.dp)
                            .clickable {
                                vehicleCounts[vehicle] = (vehicleCounts[vehicle] ?: 0) + 1
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = images[imageIndex]),
                                contentDescription = vehicle,
                                modifier = Modifier
                                    .size(60.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Row(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = vehicle,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontFamily = poppinsFontFamily2,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                Text(
                                    text = "${vehicleCounts[vehicle] ?: 0}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontFamily = poppinsFontFamily2,
                                        color = Color.White,
                                        fontSize = 28.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
private fun shareVehicleData(vehicleCounts: MutableMap<String, Int>, context: Context) {
    val sharedData = StringBuilder()

    sharedData.append("Data Kendaraan:\n")

    sharedData.append("\nPribadi:\n")
    val personalVehicles = listOf("Mobil", "Motor")
    personalVehicles.forEach { vehicle ->
        val count = vehicleCounts[vehicle] ?: 0
        sharedData.append("$vehicle: $count\n")
    }

    sharedData.append("\nPublik:\n")
    val publicVehicles = listOf("Bus Kecil", "Bus Medium", "Bus Besar")
    publicVehicles.forEach { vehicle ->
        val count = vehicleCounts[vehicle] ?: 0
        sharedData.append("$vehicle: $count\n")
    }

    sharedData.append("\nBarang:\n")
    val goodsVehicles = listOf("Truk Kecil", "Truk Medium", "Truk Besar")
    goodsVehicles.forEach { vehicle ->
        val count = vehicleCounts[vehicle] ?: 0
        sharedData.append("$vehicle: $count\n")
    }

    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    sharedData.append("\nTanggal dikirim: $currentDate\n")

    sharedData.append("\nDeveloper: Andre Putra Pratama\nTelkom University")

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, sharedData.toString())
        putExtra(Intent.EXTRA_SUBJECT, "Data Kendaraan")
    }
    context.startActivity(Intent.createChooser(intent, "Bagikan Data Kendaraan"))
}

@Preview(showBackground = true)
@Composable
fun TrafficCounterAppPreview() {
    CompositionLocalProvider(LocalContext provides LocalContext.current) {
        TracountScreen(modifier = Modifier.fillMaxSize(), context = LocalContext.current)
    }
}

