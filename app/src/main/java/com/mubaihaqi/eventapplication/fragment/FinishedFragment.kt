package com.mubaihaqi.eventapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mubaihaqi.eventapplication.data.model.ListEventsItem
import com.mubaihaqi.eventapplication.databinding.FragmentFinishedBinding
import com.mubaihaqi.eventapplication.ui.DetailEventActivity
import com.mubaihaqi.eventapplication.ui.adapter.EventAdapter
import com.mubaihaqi.eventapplication.viewmodel.EventViewModel

class FinishedFragment : Fragment() {
    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private var fullEventList: List<ListEventsItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Dicoding Event App"

        eventAdapter = EventAdapter(emptyList()) { event ->
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_NAME, event.name)
            intent.putExtra(DetailEventActivity.EXTRA_DATE, event.beginTime)
            intent.putExtra(DetailEventActivity.EXTRA_LOCATION, event.cityName)
            intent.putExtra(DetailEventActivity.EXTRA_DESC, event.description)
            intent.putExtra(DetailEventActivity.EXTRA_IMAGE, event.imageLogo)
            intent.putExtra(DetailEventActivity.EXTRA_LINK, event.link)
            startActivity(intent)
        }

        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = eventAdapter

        eventViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            fullEventList = events
            eventAdapter.updateData(events)
        }

        eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        eventViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterEvents(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterEvents(newText)
                return true
            }
        })

        binding.cardSearch.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.requestFocusFromTouch()
        }

        eventViewModel.getFinishedEvents()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as androidx.appcompat.app.AppCompatActivity).supportActionBar?.title =
            HtmlCompat.fromHtml("<font color='#FFFFFF'>Finished</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun filterEvents(query: String?) {
        val filtered = if (query.isNullOrBlank()) {
            fullEventList
        } else {
            fullEventList.filter { it.name.contains(query, ignoreCase = true) }
        }
        eventAdapter.updateData(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}