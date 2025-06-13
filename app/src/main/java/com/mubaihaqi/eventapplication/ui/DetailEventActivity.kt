package com.mubaihaqi.eventapplication.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.mubaihaqi.eventapplication.R
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = name

        binding.tvEventNameDetail.text = name
        binding.tvEventDateDetail.text = date
        binding.tvEventLocationDetail.text = location

        supportActionBar?.title = HtmlCompat.fromHtml(
            "<font color='#FFFFFF'>$name</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        val descClean = desc ?: ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvEventDescDetail.text = android.text.Html.fromHtml(descClean, android.text.Html.FROM_HTML_MODE_LEGACY)
        } else {
            binding.tvEventDescDetail.text = android.text.Html.fromHtml(descClean)
        }

        Glide.with(this)
            .load(imageUrl)
            .into(binding.imgEventDetail)

        binding.btnRegister.setOnClickListener {
            if (!link.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        }
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.teal_700)))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
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