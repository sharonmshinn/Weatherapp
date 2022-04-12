package com.example.weatherapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.weatherapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var viewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Search"

        binding = FragmentSearchBinding.bind(view)

        viewModel.enableButton.observe(viewLifecycleOwner) { enable ->
            binding.searchButton.isEnabled = enable
        }



        viewModel.showErrorDialog.observe(viewLifecycleOwner) { showError ->
            if(showError) {
                ErrorDialogFragment().show(childFragmentManager, ErrorDialogFragment.TAG)
            }
        }

        binding.zipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.toString()?.let { viewModel.updateZipCode(it)}
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.searchButton.setOnClickListener() {
            viewModel.submitButtonClicked()
            if(!(viewModel.showErrorDialog.value!!)) {
                val currentConditions =
                    SearchFragmentDirections.actionSearchFragmentToCurrentConditionsFragment(
                        viewModel.returnZipCode(),
                        viewModel.currentConditions.value
                    )
                Navigation.findNavController(it).navigate(currentConditions)
            } else {
                viewModel.resetErrorDialog()
            }
        }
    }


}