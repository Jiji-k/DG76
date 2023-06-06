package com.example.dg76

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dg76.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var privateText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setContentView(binding.root)

        takeInputFromCloud()
        CallForHelp()
        MessageForHelp()
    }

    private fun takeInputFromCloud() {
        // Initialize Firebase database and reference
        val database = FirebaseDatabase.getInstance()
        //TODO change to the message child
        val myRef = database.getReference("fire")
        privateText = binding.fireText

        // Read data from Firebase
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the previous text
                privateText.text = ""
                var value =""
                // Iterate over the child nodes
                for (childSnapshot in dataSnapshot.children) {
                    // Retrieve the value associated with each child node
                    value = childSnapshot.value.toString()

                    // Append the value to the fireText TextView
                    //privateText.append(value + "\n")
                }
                privateText.append(value + "\n")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Log.e("Firebase", "Failed to read value: ${databaseError.toException()}")
            }
        })
    }


    private fun CallForHelp() {
        mainViewModel.onCallButtonClicked.observe(this) { value ->
            if (value) {
                val phoneNumber = "1112"
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(dialIntent)
            }
        }
    }

    private fun MessageForHelp() {
        mainViewModel.onMessageButtonClicked.observe(this) { value ->
            if (value) {
                val phoneNumber = "1113"
                val message = "My address is Strada DG, nr 76, and my house is on fire. Need help!"

                val smsUri = Uri.parse("smsto:$phoneNumber")
                val sendIntent = Intent(Intent.ACTION_SENDTO, smsUri)
                sendIntent.putExtra("sms_body", message)
                startActivity(sendIntent)
            }
        }
    }
}