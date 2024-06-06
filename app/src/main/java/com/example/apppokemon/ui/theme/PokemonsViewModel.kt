package com.example.apppokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokemon.api.InstanciaRetrofit
import com.example.apppokemon.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonsViewModel : ViewModel() {
    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons

    private val _pokemonsPeso = MutableStateFlow<String?>(null)
    private val _pokemonsAltura = MutableStateFlow<String?>(null)
    val pokemonsPeso: MutableStateFlow<String?> = _pokemonsPeso
    val pokemonsAltura: MutableStateFlow<String?> = _pokemonsAltura

    fun obterPokemons() {
        viewModelScope.launch {
            try {
                val response = InstanciaRetrofit.api.getPokemons(limit = 120, offset = 0)
                _pokemons.value = response.results
                println("API Call - ${response.results}")
            } catch (e: Exception) {
                println("API Call - Failed to fetch data ${e}")
            }
        }
    }

    fun obterPokemonPesoAltura(number: String) {
        viewModelScope.launch {
            try {
                val responsePesoAltura = InstanciaRetrofit.apiPesoAltura.getPokemonsImage(number)
                pokemonsPeso.value = responsePesoAltura.height.toString()
                pokemonsAltura.value = responsePesoAltura.weight.toString()
                println("API Call Peso - ${pokemonsPeso.value}")
                println("API Call ALtura - ${pokemonsAltura.value}")
            } catch (e: Exception) {
                println("API Call - Failed to fetch data ${e}")
            }
        }
    }
}
