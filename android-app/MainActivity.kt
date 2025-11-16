package com.example.ruchikademoapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ruchikademoapplication.ui.theme.RuchikaDemoApplicationTheme
import kotlin.math.cos
import kotlin.math.sin

data class HouseData(
    val datasetId: String,
    val postcode: String,
    val primarySubstationName: String,
    val hvFeederName: String,
    val secondarySubstationName: String,
    val lvFeederName: String,
    val totalMpanCount: Int,
    val powerIssue: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RuchikaDemoApplicationTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("home") }
    var selectedHouse by remember { mutableStateOf<HouseData?>(null) }

    when (currentScreen) {
        "home" -> HomeScreen(
            onRandomClick = { currentScreen = "random" },
            onGroupClick = { currentScreen = "group" }
        )
        "random" -> RandomPlayersScreen(
            onBack = { currentScreen = "home" },
            onHouseClick = { house ->
                selectedHouse = house
                currentScreen = "houseDetail"
            }
        )
        "group" -> GroupPlayersScreen(
            onBack = { currentScreen = "home" }
        )
        "houseDetail" -> selectedHouse?.let { house ->
            HouseDetailScreen(
                house = house,
                onBack = { currentScreen = "random" },
                onStartGame = { currentScreen = "game" }
            )
        }
        "game" -> selectedHouse?.let { house ->
            GameScreen(
                house = house,
                onBack = { currentScreen = "houseDetail" },
                onStartGame = { currentScreen = "gameplay" }
            )
        }
        "gameplay" -> selectedHouse?.let { house ->
            GameplayScreen(
                house = house,
                onBack = { currentScreen = "game" }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onRandomClick: () -> Unit, onGroupClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Text(
                    text = "Help Solve your Neighbourhood power crisis !!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Text(
                    text = "Neighbourhood Nodes",
                    fontSize = 30.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = onRandomClick,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    )
                ) {
                    Text(
                        text = "Random Players",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onGroupClick,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(
                        text = "Group Players",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomPlayersScreen(onBack: () -> Unit, onHouseClick: (HouseData) -> Unit) {
    val houses = listOf(
        HouseData(
            "000200603002", "DD2 4TF", "CHARLESTON", "GOURDIE ST TEED", "CAMPRDN HOUSE", "", 7,
            "⚡ Peak Demand Crisis! Your 7 households are all cooking dinner at 6 PM, causing a power surge. Help them shift their energy use to balance the grid!"
        ),
        HouseData(
            "000200800502", "DD2 4QN", "CHARLESTON", "BALGARTHNO TEED", "BALGART HNO", "", 24,
            "🔋 Battery Storage Challenge! Your 24 households need to learn when to store energy and when to use it. Can you help them use solar power wisely?"
        ),
        HouseData(
            "000200800503", "DD2 4QT", "CHARLESTON", "BALGARTHNO TEED", "BALGART HNO", "", 24,
            "🚗 EV Charging Overload! Everyone wants to charge their electric cars at the same time. Teach them about smart charging to prevent grid collapse!"
        ),
        HouseData(
            "000200800504", "DD2 4QW", "CHARLESTON", "BALGARTHNO TEED", "BALGART HNO", "", 4,
            "🌡️ Heating Rush Hour! These 4 households all turn on heating at once during winter mornings. Help them stagger their usage to keep the grid stable!"
        ),
        HouseData(
            "000200800505", "DD2 4QX", "CHARLESTON", "BALGARTHNO TEED", "BALGART HNO", "", 42,
            "☀️ Solar Surplus Problem! Your 42 households produce too much solar energy at noon but need power at night. Teach them about energy storage and shifting!"
        ),
        HouseData(
            "000200800505", "DD2 4QY", "CHARLESTON", "BALGARTHNO TEED", "BALGART HNO", "", 42,
            "⚙️ Load Balancing Crisis! Your large neighbourhood has unpredictable energy spikes. Help residents coordinate their appliance usage to smooth out demand!"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Random Players") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Back", color = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(350.dp),
                contentAlignment = Alignment.Center
            ) {
                houses.forEachIndexed { index, house ->
                    val angle = (index * 60.0) * Math.PI / 180.0
                    val radius = 140.dp.value
                    val x = (radius * cos(angle)).dp
                    val y = (radius * sin(angle)).dp

                    Box(
                        modifier = Modifier.offset(x = x, y = y)
                    ) {
                        Button(
                            onClick = { onHouseClick(house) },
                            modifier = Modifier
                                .width(100.dp)
                                .height(70.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800)
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "🏠",
                                    fontSize = 28.sp
                                )
                                Text(
                                    text = house.postcode,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Select a\nNeighbourhood",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1976D2)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseDetailScreen(house: HouseData, onBack: () -> Unit, onStartGame: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("House Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF9800),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Back", color = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🏠",
                fontSize = 64.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Postcode", fontSize = 12.sp, color = Color.Gray)
                    Text(house.postcode, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Dataset ID", fontSize = 12.sp, color = Color.Gray)
                    Text(house.datasetId, fontSize = 16.sp)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Primary Substation", fontSize = 12.sp, color = Color.Gray)
                    Text(house.primarySubstationName, fontSize = 16.sp)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("HV Feeder", fontSize = 12.sp, color = Color.Gray)
                    Text(house.hvFeederName, fontSize = 16.sp)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Secondary Substation", fontSize = 12.sp, color = Color.Gray)
                    Text(house.secondarySubstationName, fontSize = 16.sp)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total Households", fontSize = 12.sp, color = Color.White)
                    Text(
                        "${house.totalMpanCount}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "START THE GAME",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(house: HouseData, onBack: () -> Unit, onStartGame: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Game - ${house.postcode}") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Back", color = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4CAF50),
                            Color(0xFFFFEB3B)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome to ${house.postcode}!",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "🏘️ ${house.totalMpanCount} Households",
                            fontSize = 18.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "⚠️ Power Challenge",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = house.powerIssue,
                            fontSize = 16.sp,
                            color = Color.Black,
                            lineHeight = 24.sp
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎯 Your Mission",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Work together to balance the grid by:\n\n" +
                                    "• Shifting peak demand to off-peak hours\n" +
                                    "• Using battery storage wisely\n" +
                                    "• Coordinating appliance usage\n" +
                                    "• Managing renewable energy sources",
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 22.sp
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎮 Game Starting Soon!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Get ready to balance the neighbourhood grid and save energy!",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onStartGame,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "▶ START",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelloPreview() {
    RuchikaDemoApplicationTheme {
        AppNavigation()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameplayScreen(house: HouseData, onBack: () -> Unit) {
    var gridStability by remember { mutableIntStateOf(40) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var selectedAction by remember { mutableStateOf("") }

    // Define correct actions for each challenge type
    val correctActions = when {
        house.powerIssue.contains("Peak Demand") -> listOf("Shift Peak", "Use Battery")
        house.powerIssue.contains("Battery Storage") -> listOf("Use Battery", "Add Solar")
        house.powerIssue.contains("EV Charging") -> listOf("Delay EV Charging", "Shift Peak")
        house.powerIssue.contains("Heating") -> listOf("Pre-Heat Homes", "Shift Peak")
        house.powerIssue.contains("Solar Surplus") -> listOf("Use Battery", "Delay EV Charging")
        house.powerIssue.contains("Load Balancing") -> listOf("Reduce Load", "Shift Peak")
        else -> listOf("Use Battery")
    }

    if (showResult) {
        // Result Screen
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Result") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFD32F2F),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE))
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isCorrect) "✓" else "✗",
                    fontSize = 120.sp,
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFD32F2F)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = if (isCorrect) "CORRECT!" else "WRONG!",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFD32F2F)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isCorrect)
                                "Great choice! '$selectedAction' helps solve this grid challenge."
                            else
                                "'$selectedAction' isn't the best solution here. Try another strategy!",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        if (!isCorrect) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "💡 Hint: Try '${correctActions.first()}'",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Grid Stability: ${if (isCorrect) gridStability + 20 else gridStability - 10}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFD32F2F)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (isCorrect) {
                            gridStability = (gridStability + 20).coerceAtMost(100)
                        } else {
                            gridStability = (gridStability - 10).coerceAtLeast(0)
                        }
                        showResult = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    )
                ) {
                    Text("Continue Playing", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onBack) {
                    Text("← Back to Menu", fontSize = 16.sp)
                }
            }
        }
    } else {
        // Main Gameplay Screen
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Playing - ${house.postcode}") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1976D2),
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        TextButton(onClick = onBack) {
                            Text("← Back", color = Color.White)
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F5F5))
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Battery Stability Meter
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Grid Stability",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Battery Icon (like phone battery)
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Battery outline
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight()
                                    .background(Color.White)
                                    .then(
                                        Modifier.background(
                                            Color.Gray,
                                            RoundedCornerShape(8.dp)
                                        )
                                    )
                            ) {
                                // Battery fill
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(gridStability / 100f)
                                        .fillMaxHeight()
                                        .background(
                                            when {
                                                gridStability > 70 -> Color(0xFF4CAF50)
                                                gridStability > 40 -> Color(0xFFFF9800)
                                                else -> Color(0xFFD32F2F)
                                            },
                                            RoundedCornerShape(8.dp)
                                        )
                                )

                                // Percentage text
                                Text(
                                    text = "$gridStability%",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            // Battery tip
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .height(40.dp)
                                    .background(Color.Gray, RoundedCornerShape(4.dp))
                                    .align(Alignment.CenterEnd)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = when {
                                gridStability > 70 -> "🟢 Stable"
                                gridStability > 40 -> "🟡 Warning"
                                else -> "🔴 Critical"
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                gridStability > 70 -> Color(0xFF4CAF50)
                                gridStability > 40 -> Color(0xFFFF9800)
                                else -> Color(0xFFD32F2F)
                            }
                        )
                    }
                }

                // Challenge reminder
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "⚠️ Challenge:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                        Text(
                            text = house.powerIssue,
                            fontSize = 12.sp,
                            color = Color.Black,
                            lineHeight = 18.sp
                        )
                    }
                }

                Text(
                    text = "Choose an Action:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )

                // Action Buttons (6 rectangles)
                val actions = listOf(
                    "🔋 Use Battery",
                    "🚗 Delay EV Charging",
                    "⏰ Shift Peak",
                    "💡 Reduce Load",
                    "🌡️ Pre-Heat Homes",
                    "☀️ Add Solar"
                )

                actions.forEach { action ->
                    Button(
                        onClick = {
                            selectedAction = action.substring(2) // Remove emoji
                            isCorrect = correctActions.any {
                                selectedAction.contains(it, ignoreCase = true)
                            }
                            showResult = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1976D2)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = action,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupPlayersScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Group Players") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Back", color = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Group Players",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Coming Soon!",
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/antimundo/poder-solar"))
                    context.startActivity(intent)
                }
            ) {
                Text("Visit GitHub")
            }
        }
    }
}