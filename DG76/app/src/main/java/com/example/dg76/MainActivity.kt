package com.example.dg76

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dg76.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setContentView(binding.root)

        CallForHelp()
        MessageForHelp()
    }

    private fun CallForHelp() {
        mainViewModel.onCallButtonClicked.observe(this) { value ->
            if (value) {
                val phoneNumber = "0751919106"
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(dialIntent)
            }
        }
    }

    private fun MessageForHelp() {
        mainViewModel.onMessageButtonClicked.observe(this) { value ->
            if (value) {
                val phoneNumber = "0751919106"
                val message = "My address is Strada Zorilor, nr 76, and my house is on fire. Need help!"

                val smsUri = Uri.parse("smsto:$phoneNumber")
                val sendIntent = Intent(Intent.ACTION_SENDTO, smsUri)
                sendIntent.putExtra("sms_body", message)
                startActivity(sendIntent)
            }
        }
    }
}