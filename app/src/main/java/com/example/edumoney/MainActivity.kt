package com.example.edumoney

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById<TextView>(R.id.txt_result)

        val editField = findViewById<EditText>(R.id.edit_field)
        editField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Não é necessário implementar nada aqui
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Não é necessário implementar nada aqui
            }
        })

        val buttonConverter = findViewById<Button>(R.id.btn_converter)
        buttonConverter.setOnClickListener {
            convertCurrency()
        }
    }

    private fun getExchangeRate(currency: String, amount: Double): Double {
        return when (currency) {
            "USD" -> if (amount <= 100.0) 4.99 else 0.0
            "EUR" -> if (amount <= 100.0) 5.35 else 0.0
            "CLP" -> if (amount <= 100.0) 0.0062 else 0.0
            else -> 0.0
        }
    }

    private fun convertCurrency() {
        val amountString = findViewById<EditText>(R.id.edit_field).text.toString()
        val amount = amountString.toDoubleOrNull()

        if (amount != null) {
            val selectedCurrency = findViewById<RadioGroup>(R.id.radio_group)
            val checked = selectedCurrency.checkedRadioButtonId

            val currency = when (checked) {
                R.id.radio_usd -> "USD"
                R.id.radio_eur -> "EUR"
                else -> "CLP"
            }

            val rate: Double = getExchangeRate(currency, amount)
            val convertedAmount = amount * rate

            val resultTextView = findViewById<TextView>(R.id.txt_result)
            resultTextView.text = convertedAmount.toString()
            resultTextView.visibility = View.VISIBLE
        } else {
            val resultTextView = findViewById<TextView>(R.id.txt_result)
            resultTextView.visibility = View.GONE
        }
    }
}

// Não deu para rodar o aplicatico com a internet, então fiz de forma manual as conversõe