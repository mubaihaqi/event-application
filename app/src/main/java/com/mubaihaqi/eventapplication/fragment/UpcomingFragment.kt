package com.mubaihaqi.eventapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mubaihaqi.eventapplication.data.model.ListEventsItem
import com.mubaihaqi.eventapplication.databinding.FragmentUpcomingBinding
import com.mubaihaqi.eventapplication.ui.DetailEventActivity
import com.mubaihaqi.eventapplication.ui.adapter.EventAdapter
import com.mubaihaqi.eventapplication.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.*

class UpcomingFragment : Fragment() {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = EventAdapter(emptyList()) { event ->
            openDetail(event)
        }

        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUpcoming.adapter = eventAdapter

        eventViewModel.listEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter.updateData(filterUpcomingEvents(events))
        }

        eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        eventViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        eventViewModel.getEvents()
    }

    private fun openDetail(event: ListEventsItem) {
        val intent = Intent(requireContext(), DetailEventActivity::class.java)
        intent.putExtra(DetailEventActivity.EXTRA_NAME, event.name)
        intent.putExtra(DetailEventActivity.EXTRA_DATE, event.beginTime)
        intent.putExtra(DetailEventActivity.EXTRA_LOCATION, event.cityName)
        intent.putExtra(DetailEventActivity.EXTRA_DESC, event.description)
        intent.putExtra(DetailEventActivity.EXTRA_IMAGE, event.imageLogo)
        startActivity(intent)
    }

    private fun filterUpcomingEvents(events: List<ListEventsItem>): List<ListEventsItem> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.parse(sdf.format(Date()))
        return events.filter {
            try {
                val eventDate = sdf.parse(it.beginTime)
                eventDate != null && eventDate.after(today)
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}