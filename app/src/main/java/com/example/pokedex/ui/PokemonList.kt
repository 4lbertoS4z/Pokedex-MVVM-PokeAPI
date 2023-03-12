package com.example.pokedex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.model.PokeListAdapter
import com.example.pokedex.model.PokeListViewModel
import com.example.pokedex.databinding.ActivityMainBinding

class PokemonList : AppCompatActivity() {

    private lateinit var viewModel: PokeListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var pokeListAdapter: PokeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokeListViewModel::class.java]

        initUI()
    }

    private fun initUI() {
        binding.pokelistRecyclerView.layoutManager = LinearLayoutManager(this)


        pokeListAdapter = PokeListAdapter{
            val intent = Intent(this, PokeInfoActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }


        binding.pokelistRecyclerView.adapter = pokeListAdapter

        viewModel.getPokemonList()
        viewModel.pokemonList.observe(this, Observer { list ->
            pokeListAdapter.setData(list)
        })
    }
}
