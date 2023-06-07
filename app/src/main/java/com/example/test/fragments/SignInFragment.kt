package com.example.test.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentSignInBinding
import com.example.test.utils.ApiStatus
import com.example.test.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireActivity().getSharedPreferences("token", Context.MODE_PRIVATE)
        val refreshToken = prefs.getString("refreshToken", "")
        if (refreshToken != null) {
            viewModel.refreshToken(refreshToken)
            viewModel.tokenStatus.observe(viewLifecycleOwner) {
                if (it == ApiStatus.COMPLETE) {
                    val token = viewModel.authData.value?.payload?.token?.accessToken
                    prefs.edit().putString("accessToken", token).apply()
                    findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                }
            }
        }

        binding.signInButton.setOnClickListener {
            viewModel.auth(binding.login.text.toString(), binding.password.text.toString())
            viewModel.authStatus.observe(viewLifecycleOwner) {
                if (it == ApiStatus.COMPLETE) {
                    val token = viewModel.authData.value!!
                    prefs.edit()
                        .putString("accessToken", token.payload.token.accessToken)
                        .putString("refreshToken", token.payload.token.refreshToken)
                        .apply()
                    findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}