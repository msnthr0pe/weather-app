import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsScreen(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
    city: String,
    country: String,
    temperature: String,
    description: String,
    feelsLike: String
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Weather",
                            color = Color(0xFF6E4A5C),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF6E4A5C)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        weatherViewModel.removeCity(city)
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFF6E4A5C)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Название города и страны
            Text(
                text = "$city, $country",
                color = Color(0xFF6E4A5C),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )

            // Название региона
            Text(
                text = description,
                color = Color(0xFF6E4A5C),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            // Разделитель
            Divider(modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp)
            )

            // Первый блок параметров
            InfoLine("Temperature: $temperature")
            InfoLine("Feels like: $feelsLike")
            InfoLine("Min. temperature: 2")
            InfoLine("Max. temperature: 8")

            Divider(modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp)
            )

            // Второй блок параметров
            InfoLine("Pressure: 1014 hPa")
            InfoLine("Humidity: 90%")
            InfoLine("Visibility: 10 km")
            InfoLine("Wind speed: 5 m/s")
            InfoLine("Clouds: 0%")

            Divider(modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp)
            )

            // Третий блок параметров
            InfoLine("Sunrise: 7:40")
            InfoLine("Sunset: 17:45")
        }
    }
}

@Composable
fun InfoLine(text: String) {
    // Высота 42 dp, по центру, стиль bodyLarge
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFF6E4A5C),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherDetailsScreen() {
    MaterialTheme {
        WeatherDetailsScreen(
            navController = rememberNavController(),
            weatherViewModel = viewModel(),
            city = "Moscow",
            country = "RU",
            temperature = "7°C",
            description = "Clear sky",
            feelsLike = "Feels like 2°C"
        )
    }
}
