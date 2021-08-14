package com.example.tiptime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // initializes the binding object to access views.
        setContentView(binding.root) // specifies the root of hierarchy of views in the app.
        binding.calculate.setOnClickListener { calculateTip() }
        // for more enhanced user experience, remove keyboard when enter is pressed.
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if(cost == null) {
            binding.tipAmount.text = ""
            return
        }

        val tipPercentage = when(binding.radioOptions.checkedRadioButtonId){
            R.id.amazing -> 0.20
            R.id.good -> 0.18
            else -> 0.15
        }
        var tip = cost*tipPercentage

        val roundup = binding.roundOf.isChecked

        if(roundup) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text = getString(R.string.tip_amount,formattedTip)
    }
// function to remove keyboard.
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
