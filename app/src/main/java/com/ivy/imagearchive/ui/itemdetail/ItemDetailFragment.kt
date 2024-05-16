package com.ivy.imagearchive.ui.itemdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ivy.imagearchive.ItemDetailActivity
import com.ivy.imagearchive.R
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.databinding.FragmentItemDetailBinding
import com.ivy.imagearchive.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val selectedItem = (activity as ItemDetailActivity).selectedItem

        when (selectedItem.itemType){
            ITEMTYPE_VCLIP -> {
                Glide.with((activity as ItemDetailActivity)).load(selectedItem.thumbnailUrl).into(binding.detailImage)
                binding.detailImage.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(selectedItem.contentUrl)
                    startActivity(i)
                }

            }
            ITEMTYPE_IMAGE -> {
                Glide.with((activity as ItemDetailActivity))
                    .load(selectedItem.contentUrl)
                    .into(binding.detailImage)
                    .onLoadFailed((activity as ItemDetailActivity).getDrawable(android.R.drawable.ic_dialog_alert))
                binding.clipPlay.visibility = View.GONE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}