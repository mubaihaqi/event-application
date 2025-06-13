package com.mubaihaqi.eventapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mubaihaqi.eventapplication.data.model.ListEventsItem
import com.mubaihaqi.eventapplication.databinding.FragmentHomeBinding
import com.mubaihaqi.eventapplication.ui.DetailEventActivity
import com.mubaihaqi.eventapplication.ui.adapter.EventAdapter
import com.mubaihaqi.eventapplication.ui.adapter.UpcomingEventAdapter
import com.mubaihaqi.eventapplication.viewmodel.EventViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var upcomingAdapter: UpcomingEventAdapter
    private lateinit var finishedAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingAdapter = UpcomingEventAdapter(emptyList()) { event -> openDetail(event) }
        finishedAdapter = EventAdapter(emptyList()) { event -> openDetail(event) }

        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcoming.adapter = upcomingAdapter

        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinished.adapter = finishedAdapter

        eventViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            upcomingAdapter.updateData(events)
        }
        eventViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedAdapter.updateData(events)
        }

        eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        eventViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        eventViewModel.getUpcomingEvents()
        eventViewModel.getFinishedEvents()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as androidx.appcompat.app.AppCompatActivity).supportActionBar?.title =
            HtmlCompat.fromHtml("<font color='#FFFFFF'>Dicoding Event App</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun openDetail(event: ListEventsItem) {
        val intent = Intent(requireContext(), DetailEventActivity::class.java)
        intent.putExtra(DetailEventActivity.EXTRA_NAME, event.name)
        intent.putExtra(DetailEventActivity.EXTRA_DATE, event.beginTime)
        intent.putExtra(DetailEventActivity.EXTRA_LOCATION, event.cityName)
        intent.putExtra(DetailEventActivity.EXTRA_DESC, event.description)
        intent.putExtra(DetailEventActivity.EXTRA_IMAGE, event.imageLogo)
        intent.putExtra(DetailEventActivity.EXTRA_LINK, event.link)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}