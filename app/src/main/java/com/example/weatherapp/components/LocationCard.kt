import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

@Composable
fun LocationCard(
    city: String,
    country: String,
    state: String,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        border = BorderStroke(1.dp, colorResource(id = R.color.card_border))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = city,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.card_border)
            )

            Text(
                text = "${stringResource(R.string.country)} $country",
                fontSize = 16.sp,
                color = colorResource(id = R.color.card_border)
            )

            Text(
                text = "${stringResource(R.string.state)} $state",
                fontSize = 16.sp,
                color = colorResource(id = R.color.card_border)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAddClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_background)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_icon_desc),
                    tint = colorResource(id = R.color.button_text),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.add), color = colorResource(id = R.color.button_text))
            }
        }
    }
}
