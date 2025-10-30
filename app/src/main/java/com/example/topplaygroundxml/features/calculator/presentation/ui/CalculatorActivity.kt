package com.example.topplaygroundxml.features.calculator.presentation.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.topplaygroundxml.databinding.ActivityCalculatorBinding
import com.example.topplaygroundxml.features.calculator.presentation.viewmodel.CalculatorViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private val viewModel: CalculatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
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
        lifecycleScope.launch {
            viewModel.expression.collect { expression ->
                binding.expressionText.text = expression
            }
        }

        lifecycleScope.launch {
            viewModel.result.collect { result ->
                binding.resultText.text = result
            }
        }
    }
}