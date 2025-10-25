import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screens.AccountScreen
import com.example.weatherapp.screens.MainScreen
import com.example.weatherapp.screens.SearchScreen
import com.example.weatherapp.screens.SignInScreen
import com.example.weatherapp.screens.WelcomeScreen

@Composable
fun MyApp() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WelcomeScreen(navController)
            }
            composable("signIn") {
                SignInScreen(navController)
            }
            composable("signUp") {
                SignUpScreen(navController)
            }
            composable("home") {
                MainScreen(
                    navController = rememberNavController(),
                    onToggleTheme = {}
                )
            }
            composable("weatherDetails") {
                WeatherDetailsScreen(navController)
            }
            composable("search") {
                SearchScreen(navController)
            }
            composable("account") {
                AccountScreen(
                    navController = rememberNavController(),
                    onToggleTheme = {}
                )
            }
        }
    }
}