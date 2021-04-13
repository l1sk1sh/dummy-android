package com.dummy.android.ui.main.taxes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dummy.android.R
import com.dummy.android.data.PreferencesDataStore
import com.dummy.android.databinding.FragmentDummyTaxesBinding
import com.google.android.material.snackbar.Snackbar

class DummyTaxesFragment : Fragment() {

    private lateinit var viewModel: DummyTaxesViewModel
    private lateinit var binding: FragmentDummyTaxesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dummy_taxes, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = DummyTaxesViewModelFactory(application, PreferencesDataStore)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DummyTaxesViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val latestDummyTaxesAdapter = DummyTaxesAdapter(DummyTaxesListener {
            viewModel.onDetailsClicked(it)
        })

        val searchDummyTaxesAdapter = DummyTaxesAdapter(DummyTaxesListener {
            viewModel.onDetailsClicked(it)
        })

        binding.recvRecentDummyTaxesRecordsList.adapter = latestDummyTaxesAdapter
        binding.recvSearchDummyTaxesRecordsList.adapter = searchDummyTaxesAdapter

        viewModel.filteredRecords.observe(viewLifecycleOwner, {
            it?.let {
                latestDummyTaxesAdapter.addHeaderAndSubmitList(it)
            }
        })

        viewModel.searchedRecords.observe(viewLifecycleOwner, {
            it?.let {
                searchDummyTaxesAdapter.addHeaderAndSubmitList(it)
            }
        })

        viewModel.filterButtonClicked.observe(viewLifecycleOwner, {
            it?.let {
                binding.root.hideKeyboard()
            }
        })

        viewModel.searchButtonClicked.observe(viewLifecycleOwner, {
            it?.let {
                binding.root.hideKeyboard()
            }
        })

        viewModel.showRecordDetails.observe(viewLifecycleOwner, { record ->
            record?.let {
                findNavController().navigate(
                    DummyTaxesFragmentDirections.actionShowDetails(it)
                )
                viewModel.onDetailsClickedComplete()
            }
        })

        viewModel.eventFailure.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onFailureComplete()
            }
        })

        return binding.root
    }

    private fun View.hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}