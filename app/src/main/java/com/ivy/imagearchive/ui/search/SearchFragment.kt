package com.ivy.imagearchive.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivy.imagearchive.MainActivity
import com.ivy.imagearchive.databinding.FragmentSearchBinding
import com.ivy.imagearchive.remotesource.SearchRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    @Inject
    lateinit var searchRepository: SearchRepository

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                searchViewModel.search()
            }
            return@setOnEditorActionListener false
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SearchRecyclerViewAdapter(arrayListOf(), activity as MainActivity)
        }

        searchViewModel.imageData.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as SearchRecyclerViewAdapter).setItemList(it)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}