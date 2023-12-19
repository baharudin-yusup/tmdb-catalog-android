package dev.baharudin.themoviedb.favorite.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.baharudin.themoviedb.favorite.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}