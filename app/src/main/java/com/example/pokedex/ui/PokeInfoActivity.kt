package com.example.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.model.PokeInfoViewModel
import com.example.pokedex.databinding.ActivityPokeInfoBinding

class PokeInfoActivity : AppCompatActivity() {

    lateinit var viewModel: PokeInfoViewModel
    private lateinit var binding: ActivityPokeInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokeInfoViewModel::class.java]

        initUI()
    }

    private fun initUI() {
        val id = intent.extras?.get("id") as Int

        viewModel.getPokemonInfo(id)

        viewModel.pokemonInfo.observe(this, Observer { pokemon ->
            val typeNames = pokemon.types.map { it.type.name }
            binding.nameTextView.text = pokemon.name
            binding.heightText.text = "Altura: ${pokemon.height / 10.0}m"
            binding.weightText.text = "Peso: ${pokemon.weight / 10.0}"

            binding.typeText.text = "Tipo: ${typeNames.joinToString()}"
            binding.expBaseText.text = "Exp.Base: ${pokemon.baseExperience}"

            Glide.with(this).load(pokemon.sprites.frontDefault).into(binding.imageView)
        })

        viewModel.getPokemonDescription(id)
        viewModel.pokemonDescription.observe(this) { pokemon ->


            val spanishEntries = pokemon.flavorTextEntries.filter { it.language.name == "es" }

            val spanishText = spanishEntries.firstOrNull()?.flavorText

            binding.descriptionText.text = spanishText ?: ""

            viewModel.pokemonInfo.value?.spanishFlavorTextEntries =
                spanishEntries.map { it.flavorText }
        }
    }
}