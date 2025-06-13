package com.mubaihaqi.eventapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mubaihaqi.eventapplication.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val date = intent.getStringExtra(EXTRA_DATE)
        val location = intent.getStringExtra(EXTRA_LOCATION)
        val desc = intent.getStringExtra(EXTRA_DESC)
        val imageUrl = intent.getStringExtra(EXTRA_IMAGE)
        val link = intent.getStringExtra(EXTRA_LINK)

        binding.tvEventNameDetail.text = name
        binding.tvEventDateDetail.text = date
        binding.tvEventLocationDetail.text = location
        binding.tvEventDescDetail.text = desc
        Glide.with(this)
            .load(imageUrl)
            .into(binding.imgEventDetail)

        binding.btnRegister.setOnClickListener {
            if (!link.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_LINK = "extra_link"
    }
}