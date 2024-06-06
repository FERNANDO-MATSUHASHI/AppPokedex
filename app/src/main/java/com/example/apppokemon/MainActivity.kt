package com.example.apppokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.apppokemon.model.Pokemon
import com.example.apppokemon.ui.theme.AppPokemonTheme
import com.example.apppokemon.viewmodel.PokemonsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPokemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppPokemon()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPokemon() {
    ListaDePokemons(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun ListaDePokemons(modifier: Modifier = Modifier) {
    val pokemonsViewModel: PokemonsViewModel = viewModel()
    //val pokemons by pokemonsViewModel.pokemons // ALT+ENTER aqui para importar getValue
    val pokemons by pokemonsViewModel.pokemons.collectAsState(initial = emptyList())
    //val pokemonsImage by pokemonsViewModel.pokemonsImage.collectAsState()

    val offset = Offset(3.0f, 4.0f)

    fun extractNumberFromUrl(url: String): String? {
        val regex = """.*/(\d+)/""".toRegex()
        return regex.find(url)?.groupValues?.get(1)
    }
    Text(
        text = "Pokédex",
        fontSize = 35.sp,
        style = TextStyle(
            fontSize = 24.sp,
            shadow = Shadow(
                color = Color.Blue, blurRadius = 3f, offset = offset
            )
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
    )
    LazyColumn(
        modifier = modifier.fillMaxSize()
            .padding(top = 100.dp)
    ) {
        items(pokemons) { pokemon ->
            val number = extractNumberFromUrl(pokemon.url)
            if (number != null) {

                CardPokemons(pokemon = pokemon, number = number)
            }

        }
    }
    DisposableEffect(Unit) {
        pokemonsViewModel.obterPokemons()
        onDispose {  }
    }
}

@Composable
fun CardPokemons(pokemon: Pokemon, number: String) {
    var expanded by remember { mutableStateOf (false) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 17.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(35.dp)
            .clickable(onClick = { expanded = true })
        //.height(330.dp)
        //.fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(
                    context = LocalContext.current
                )
                    .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${number}.png")
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
                //colorFilter = ColorFilter.tint(Color.Black)  // Deixa a figura toda preta
            )
            Text(
                text = "#$number ${pokemon.name}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            //Text(
            //    text = pokemon.url,
            //    modifier = Modifier
            //        .padding(bottom = 12.dp)
            //        .fillMaxWidth(),
            //    textAlign = TextAlign.Center
            //)

            if (expanded) {
                val pokemonsViewModel: PokemonsViewModel = viewModel()

                val pokemonPeso by pokemonsViewModel.pokemonsPeso.collectAsState()
                val pokemonAltura by pokemonsViewModel.pokemonsAltura.collectAsState()

                pokemonsViewModel.obterPokemonPesoAltura(number)

                AlertDialog(
                    onDismissRequest = {

                    },
                    confirmButton = {
                        Button(
                            onClick = { expanded = false }
                        ) {
                            Text(text = "Ok")
                        }
                    },
                    title = {
                        Column {
                            AsyncImage(
                                model = ImageRequest.Builder(
                                    context = LocalContext.current
                                )
                                    .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${number}.png")
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    //.fillMaxSize()
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally),
                                contentScale = ContentScale.Fit,
                                //colorFilter = ColorFilter.tint(Color.Black)  // Deixa a figura toda preta
                            )
                            Text(
                                text = pokemon.name,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    },
                    text = {
                        Text(
                            text = "Peso: $pokemonPeso\nAltura: $pokemonAltura",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(15.dp)
                )
            }
        }
    }

}
