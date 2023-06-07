package com.example.test.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.test.adapters.RecycleViewAdapter
import com.example.test.databinding.FragmentMainBinding
import com.example.test.model.Product
import com.example.test.model.Products
import com.example.test.utils.ApiStatus
import com.example.test.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var adapter : RecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts()
        val array = ArrayList<Product>()
        viewModel.status.observe(viewLifecycleOwner){
            if(it == ApiStatus.COMPLETE){
                viewModel.productData.value?.payload?.content?.forEach {pr ->
                    array.add(pr)
                }
                adapter = RecycleViewAdapter(requireContext(), array)
                binding.recycleView.adapter = adapter
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}