package com.dummy.android.ui.main.stores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dummy.android.BaseFragment
import com.dummy.android.R
import com.dummy.android.data.PreferencesDataStore
import com.dummy.android.databinding.FragmentDummyStoresBinding

class DummyStoresFragment : BaseFragment() {

    private lateinit var binding: FragmentDummyStoresBinding
    private lateinit var viewModel: DummyStoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dummy_stores, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = DummyStoresViewModelFactory(application, PreferencesDataStore)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DummyStoresViewModel::class.java)

        binding.wallstreetbetsViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.openAds.observe(viewLifecycleOwner, {
            if (it == true) {
                openBillingFromAd()
            }
        })

        return binding.root
    }
}