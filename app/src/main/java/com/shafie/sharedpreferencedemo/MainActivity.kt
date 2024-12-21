package com.shafie.sharedpreferencedemo

import android.app.backup.BackupManager
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shafie.sharedpreferencedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private final val SAVED = "saved"
    private var backupManager: BackupManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        backupManager = BackupManager(this)

        var savedData = loadPreference()
        if (savedData != "") {
            binding.readDataEditText.setText(savedData)
        }
        else{
            binding.readDataBtn.isEnabled = false
        }

        binding.saveDataBtn.setOnClickListener {
            val text = binding.saveDataEditText.text.toString()

            Log.d("debug", "Calling Backup Manager")
            backupManager?.dataChanged()
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
        val sharedPreferences = getSharedPreferences(BackupData.PREFS_TEST, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(SAVED, value)
        editor.apply() // Use apply() instead of commit() for asynchronous saving
    }

    private fun loadPreference(): String? {
        val sharedPreferences = getSharedPreferences(BackupData.PREFS_TEST,MODE_PRIVATE)
        return sharedPreferences.getString(SAVED, "")
    }
}
