package dev.baharudin.tmdb_android.presentation.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.databinding.FragmentAboutBinding

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDeveloperAvatar()
        initShareButton()
    }

    private fun initDeveloperAvatar() {
        val drawable = CircularProgressDrawable(requireContext())
        drawable.setColorSchemeColors(
            R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent
        )
        drawable.centerRadius = 100f
        drawable.strokeWidth = 10f
        drawable.start()

        val url =
            "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos:80a24ebdbd553741dca2ed6f2cda770220220614232417.png"
        Glide.with(this).load(url).centerCrop().placeholder(drawable)
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