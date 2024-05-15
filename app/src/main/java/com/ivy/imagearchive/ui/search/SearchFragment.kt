package com.ivy.imagearchive.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivy.imagearchive.MainActivity
import com.ivy.imagearchive.constant.PATH_IMAGE
import com.ivy.imagearchive.databinding.FragmentSearchBinding
import com.ivy.imagearchive.remotesource.SearchRepository
import com.ivy.imagearchive.remotesource.dataclass.SearchRequestData
import dagger.hilt.android.AndroidEntryPoint
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
        val searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 검색 시
        binding.searchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && actionId == EditorInfo.IME_ACTION_SEARCH){
                val newQuery = textView.text.toString()
                searchViewModel.performSearch(
                    SearchRequestData(
                        path = PATH_IMAGE,
                        query = newQuery
                    )
                )
            }
            return@setOnEditorActionListener false
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
}