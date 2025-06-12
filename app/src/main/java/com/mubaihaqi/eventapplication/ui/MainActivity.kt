package com.mubaihaqi.eventapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mubaihaqi.eventapplication.data.model.ListEventsItem
import com.mubaihaqi.eventapplication.databinding.ActivityMainBinding
import com.mubaihaqi.eventapplication.ui.adapter.EventAdapter
import com.mubaihaqi.eventapplication.viewmodel.EventViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private var fullEventList: List<ListEventsItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventAdapter = EventAdapter(emptyList()) { event ->
            val intent = Intent(this, DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_NAME, event.name)
            intent.putExtra(DetailEventActivity.EXTRA_DATE, event.beginTime)
            intent.putExtra(DetailEventActivity.EXTRA_LOCATION, event.cityName)
            intent.putExtra(DetailEventActivity.EXTRA_DESC, event.description)
            intent.putExtra(DetailEventActivity.EXTRA_IMAGE, event.imageLogo)
            startActivity(intent)
        }
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = eventAdapter

        eventViewModel.listEvents.observe(this) { events ->
            fullEventList = events
            eventAdapter.updateData(events)
        }

        eventViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        eventViewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterEvents(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterEvents(newText)
                return true
            }
        })

        eventViewModel.getEvents()
    }

    private fun filterEvents(query: String?) {
        val filtered = if (query.isNullOrBlank()) {
            fullEventList
        } else {
            fullEventList.filter { it.name.contains(query, ignoreCase = true) }
        }
        eventAdapter.updateData(filtered)
    }
}