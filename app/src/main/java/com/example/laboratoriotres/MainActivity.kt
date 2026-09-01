package com.example.laboratoriotres

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GamingBlogApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GamingBlogApp() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    var selectedTab by remember { mutableIntStateOf(0) }
    var darkModeState by remember { mutableStateOf(true) }
    var reviewRating by remember { mutableFloatStateOf(8.5f) }
    var showFilterMenu by remember { mutableStateOf(false) }

    val categories = listOf("Todos", "PS5", "Xbox", "PC", "Nintendo", "Indies")

    // CONTENEDOR 1: Scaffold
    Scaffold(
        topBar = {
            // CONTROL 1: TopAppBar
            TopAppBar(
                title = { Text("El Blog de Sebas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    // CONTROL 2: Icon / IconButton
                    IconButton(onClick = { showFilterMenu = !showFilterMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                    }
                    // CONTROL 3: DropdownMenu
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Notificaciones") },
                            onClick = { showFilterMenu = false },
                            leadingIcon = { Icon(Icons.Default.Notifications, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Perfil de usuario") },
                            onClick = { showFilterMenu = false },
                            leadingIcon = { Icon(Icons.Default.Person, null) }
                        )
                    }
                }
            )
        },
        bottomBar = {
            // CONTROL 4: NavigationBar (BottomNavigation)
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Feed") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Reviews") },
                    label = { Text("Análisis") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = "Guardados") },
                    label = { Text("Guardados") }
                )
            }
        },
        // CONTROL 5: FloatingActionButton
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Escribir reseña", tint = Color.White)
            }
        }
    ) { paddingValues ->

        // CONTENEDOR 2: LazyColumn (Feed principal)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // CONTROL 6: OutlinedTextField (Buscador)
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar noticias, juegos o análisis...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // CONTENEDOR 3: FlowRow + CONTROL 7: FilterChip
            item {
                Text("Categorías", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat) },
                            leadingIcon = if (selectedCategory == cat) {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                            } else null
                        )
                    }
                }
            }

            // CONTENEDOR 4: LazyRow (Destacados)
            item {
                Text("Noticias Destacadas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(3) { index ->
                        // CONTENEDOR 5: Card
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .height(180.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            // CONTENEDOR 6: Box (Para superponer texto sobre la imagen)
                            Box(modifier = Modifier.fillMaxSize()) {

                                Image(
                                    painter = painterResource(id = R.drawable.icon_cyberleek),
                                    contentDescription = "Banner de juego",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter),
                                    color = Color.Black.copy(alpha = 0.7f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            "CyberLeek no era mas que un estafador #${index + 1}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            "Hace 6 horas • 10 min de lectura",
                                            color = Color.LightGray,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // CONTROL 8: HorizontalDivider
            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

            // CONTROL 9: TabRow
            item {
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                        Text("Últimos Artículos", modifier = Modifier.padding(12.dp))
                    }
                    Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                        Text("Comunidad", modifier = Modifier.padding(12.dp))
                    }
                }
            }

            // CONTENEDOR 7: Surface (Tarjeta de Artículo Individual)
            item {
                // CONTENEDOR 8: Surface
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {



                            Image(
                                painter = painterResource(id = R.drawable.icon_plague),
                                contentDescription = "Miniatura del artículo",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                // CONTROL 10: AssistChip
                                AssistChip(
                                    onClick = { },
                                    label = { Text("Análisis", fontSize = 10.sp) },
                                    modifier = Modifier.height(24.dp)
                                )
                                Text(
                                    "Reseña Completa: ¿Cumple las expectativas?",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    "Analizamos el rendimiento gráfico y la jugabilidad...",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    maxLines = 2
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Control interactivo dentro de la publicación: Slider de Calificación
                        Text("Calificación del Redactor: ${reviewRating.toInt()}/10", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                        // CONTROL 11: Slider
                        Slider(
                            value = reviewRating,
                            onValueChange = { reviewRating = it },
                            valueRange = 0f..10f,
                            steps = 9
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // CONTROL 12: Button / TextButton
                            TextButton(onClick = { }) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Compartir")
                            }

                            // CONTROL 13: CircularProgressIndicator (Indicador de lectura/carga)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Popularidad: ", fontSize = 11.sp, color = Color.Gray)
                                CircularProgressIndicator(
                                    progress = { 0.85f },
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }
                }
            }

            // CONTENEDOR 9 & 10: Column con Switch de Modo Oscuro (Ajustes Rápidos)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Modo Alto Rendimiento", fontWeight = FontWeight.Bold)
                            Text("Reduce animaciones al leer", fontSize = 12.sp, color = Color.Gray)
                        }
                        // CONTROL 14: Switch
                        Switch(
                            checked = darkModeState,
                            onCheckedChange = { darkModeState = it }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGamingBlogApp() {
    GamingBlogApp()
}