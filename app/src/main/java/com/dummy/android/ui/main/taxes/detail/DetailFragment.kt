package com.dummy.android.ui.main.taxes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dummy.android.R
import com.dummy.android.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val selectedRecord = DetailFragmentArgs.fromBundle(requireArguments()).selectedRecord
        val application = requireNotNull(activity).application
        val viewModelFactory = DetailViewModelFactory(selectedRecord, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.detailViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}
