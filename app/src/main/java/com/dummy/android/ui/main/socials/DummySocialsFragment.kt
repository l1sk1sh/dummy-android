package com.dummy.android.ui.main.socials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dummy.android.BaseFragment
import com.dummy.android.R
import com.dummy.android.data.PreferencesDataStore
import com.dummy.android.databinding.FragmentDummySocialsBinding

class DummySocialsFragment : BaseFragment() {

    private lateinit var binding: FragmentDummySocialsBinding
    private lateinit var viewModel: DummySocialsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dummy_socials, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = DummySocialsViewModelFactory(application, PreferencesDataStore)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DummySocialsViewModel::class.java)

        binding.wikipediaviewsViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.openAds.observe(viewLifecycleOwner, {
            if (it == true) {
                openBillingFromAd()
            }
        })

        return binding.root
    }
}