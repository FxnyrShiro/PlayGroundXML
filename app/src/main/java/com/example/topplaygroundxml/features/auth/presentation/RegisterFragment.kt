package com.example.topplaygroundxml.features.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.topplaygroundxml.R
import com.example.topplaygroundxml.databinding.FragmentRegisterBinding
import com.example.topplaygroundxml.features.auth.presentation.viewmodel.AuthNavigation
import com.example.topplaygroundxml.features.auth.presentation.viewmodel.AuthState
import com.example.topplaygroundxml.features.auth.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.register(email, password)
        }
    }

    private fun observeViewModel() {
        viewModel.authState.onEach { state ->
            binding.progressBar.isVisible = state is AuthState.Loading
            if (state is AuthState.Error) {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.navigation.onEach { navigation ->
            if (navigation is AuthNavigation.ToMainScreen) {
                findNavController().navigate(R.id.action_register_fragment_to_weather_fragment)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}