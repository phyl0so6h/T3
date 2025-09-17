package com.sahak7an.t3.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sahak7an.t3.core.ui.createCourseAdapter
import com.sahak7an.t3.feature.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = createCourseAdapter(onToggleFavorite = vm::toggleFavorite)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.buttonSort.setOnClickListener { vm.sortByPublishDateDesc() }

        binding.swipe.setOnRefreshListener { vm.refresh() }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.courses.collectLatest { list ->
                adapter.items = list
                adapter.notifyDataSetChanged()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.loading.collectLatest { isLoading ->
                binding.swipe.isRefreshing = isLoading
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.error.collectLatest { msg ->
                binding.errorText.text = msg ?: ""
                binding.errorText.visibility = if (msg == null) View.GONE else View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

