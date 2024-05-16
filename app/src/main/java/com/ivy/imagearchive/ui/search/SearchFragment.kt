package com.ivy.imagearchive.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivy.imagearchive.MainActivity
import com.ivy.imagearchive.constant.PATH_IMAGE
import com.ivy.imagearchive.constant.showEmptyQueryToast
import com.ivy.imagearchive.databinding.FragmentSearchBinding
import com.ivy.imagearchive.repository.SearchRepository
import com.ivy.imagearchive.network.dataclass.SearchRequestData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!

    lateinit var searchViewModel : SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkRecentQuery()


        searchViewModel._query.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                binding.searchDelete.visibility = View.VISIBLE
            }else{
                binding.searchDelete.visibility = View.GONE
            }
        })


        // 검색 시
        binding.searchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && actionId == EditorInfo.IME_ACTION_SEARCH){
                val newQuery = textView.text.toString()
                showEmptyQueryToast(activity as MainActivity, newQuery)
                searchViewModel.performSearch(
                    SearchRequestData(
                        path = PATH_IMAGE,
                        query = newQuery
                    )
                )
            }
            return@setOnEditorActionListener false
        }

        binding.searchIcon.setOnClickListener {
            val newQuery = binding.searchBar.text.toString()
            showEmptyQueryToast(activity as MainActivity, newQuery)
            searchViewModel.performSearch(
                SearchRequestData(
                    path = PATH_IMAGE,
                    query = newQuery
                )
            )
        }

        binding.searchBar.addTextChangedListener {
            searchViewModel.typeQuery(binding.searchBar.text.toString())
        }

        binding.searchDelete.setOnClickListener {
            binding.searchBar.text.clear()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SearchRecyclerViewAdapter(arrayListOf(), activity as MainActivity)

            // 스크롤 맨끝에 도달 시
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!binding.recyclerView.canScrollVertically(1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        showEmptyQueryToast(activity as MainActivity, searchViewModel.query)
                        searchViewModel.fetchNextPage(
                            SearchRequestData(
                                path = PATH_IMAGE,
                                query = searchViewModel.query,
                                page = searchViewModel.page
                            )
                        )
                    }
                }
            })
        }

        searchViewModel._imageData.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as SearchRecyclerViewAdapter).setItemList(it)
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkRecentQuery(){
        if ((activity as MainActivity).recentQuery.isNotEmpty()){
            searchViewModel.performSearch(
                SearchRequestData(
                    path = PATH_IMAGE,
                    query = (activity as MainActivity).recentQuery
                )
            )
        }
    }
}