package com.shafie.sharedpreferencedemo

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shafie.sharedpreferencedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private final val SAVED = "saved"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.readDataBtn.isEnabled = false

        binding.saveDataBtn.setOnClickListener {
            val text = binding.saveDataEditText.text.toString()
            if (text.isNotEmpty()) {
                savePreference(text)
            }
            binding.readDataBtn.isEnabled = true
        }

        binding.readDataBtn.setOnClickListener {
            val savedData = loadPreference()
            binding.readDataEditText.setText(savedData)
        }
    }

    private fun savePreference(value: String) {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(SAVED, value)
        editor.apply() // Use apply() instead of commit() for asynchronous saving
    }

    private fun loadPreference(): String? {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        return sharedPreferences.getString(SAVED, "")
    }
}
