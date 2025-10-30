package com.example.topplaygroundxml.features.calculator.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.topplaygroundxml.databinding.FragmentCalculatorBinding
import com.example.topplaygroundxml.features.calculator.presentation.viewmodel.CalculatorViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CalculatorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        val buttonMap = mapOf(
            binding.button0 to "0",
            binding.button1 to "1",
            binding.button2 to "2",
            binding.button3 to "3",
            binding.button4 to "4",
            binding.button5 to "5",
            binding.button6 to "6",
            binding.button7 to "7",
            binding.button8 to "8",
            binding.button9 to "9",
            binding.buttonDot to ".",
            binding.buttonAdd to "+",
            binding.buttonSubtract to "-",
            binding.buttonMultiply to "*",
            binding.buttonDivide to "/",
            binding.buttonOpenBracket to "(",
            binding.buttonCloseBracket to ")"
        )

        buttonMap.forEach { (button, text) ->
            button.setOnClickListener {
                viewModel.onButtonClicked(text)
            }
        }

        binding.equalsButton.setOnClickListener {
            viewModel.onEqualsClicked()
        }

        binding.clearButton.setOnClickListener {
            viewModel.onClearClicked()
        }

        binding.backspaceButton.setOnClickListener {
            viewModel.onBackspaceClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.expression.onEach { expression ->
            binding.expressionText.text = expression
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.result.onEach { result ->
            binding.resultText.text = result
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}