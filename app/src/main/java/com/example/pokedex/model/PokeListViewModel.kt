package com.example.pokedex.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.api.ApiService
import com.example.pokedex.api.PokeApiResponse
import com.example.pokedex.api.PokeResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeListViewModel() : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService = retrofit.create(ApiService::class.java)

    val pokemonList = MutableLiveData<List<PokeResult>>()

    fun getPokemonList(){
        val call = service.getPokemonList(251,0)

        call.enqueue(object : Callback<PokeApiResponse> {
            override fun onResponse(call: Call<PokeApiResponse>, response: Response<PokeApiResponse>) {
                response.body()?.results?.let { list ->
                    pokemonList.postValue(list)
                }

            }

            override fun onFailure(call: Call<PokeApiResponse>, t: Throwable) {
                call.cancel()
            }

        })
    }
}