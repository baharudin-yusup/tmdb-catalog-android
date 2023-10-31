package dev.baharudin.themoviedb.presentation.about

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.databinding.FragmentAboutBinding

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initDeveloperAvatar()
        initShareButton()
    }

    private fun initToolbar() {
//        setSupportActionBar(binding.toolbar)
//        // Hide action bar title
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        // Show action bar back button
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    private fun initDeveloperAvatar() {
        val drawable = CircularProgressDrawable(requireContext())
        drawable.setColorSchemeColors(
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
            R.color.colorAccent
        );
        drawable.centerRadius = 100f;
        drawable.strokeWidth = 10f;
        drawable.start()

        val url =
            "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos:80a24ebdbd553741dca2ed6f2cda770220220614232417.png";
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(drawable)
            .into(binding.imgDeveloperAvatar)
    }

    private fun initShareButton() {
        binding.btnShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hello from Bahar!")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share via"))
        }
    }
}