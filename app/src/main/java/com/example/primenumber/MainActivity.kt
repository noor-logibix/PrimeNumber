package com.example.primenumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.primenumber.ui.theme.PrimeNumberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimeNumberTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PrimeNumberApp()
                }
            }
        }
    }
}

@Composable
fun PrimeNumberApp() {
    ShowPrimeNumber(
        modifier = Modifier
            .fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShowPrimeNumber(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {

    val uiState by mainViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
    ) {

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(uiState.primeNumbers){
                PrimeCard(primeNumber = it, modifier = Modifier.padding(8.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                .height(56.dp)

        ) {
            OutlinedTextField(
                value = mainViewModel.targetRange.toString(), onValueChange = {
                    mainViewModel.updateTargetRange(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = roundedCornerShape()
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = {
                    mainViewModel.updatePrimeNumbersList()
                },
                shape = roundedCornerShape(),
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = "Show")
            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun ShowPrimeNumberPreview() {
    PrimeNumberTheme {
        ShowPrimeNumber(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun PrimeCard(primeNumber: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = roundedCornerShape()) {
        Text(
            text = primeNumber.toString(), fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun roundedCornerShape() = RoundedCornerShape(8.dp)

@Preview(showBackground = true)
@Composable
fun PrimeCardPreview() {
    PrimeCard(23, modifier = Modifier.padding(8.dp))
}