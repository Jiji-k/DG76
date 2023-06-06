package com.example.dg76

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val fireText = MutableLiveData<String>("")
    val onCallButtonClicked = MutableLiveData(false)
    val onMessageButtonClicked = MutableLiveData(false)
    val onResolvedButtonClicked =MutableLiveData(false)
    fun onClickCallButton() {
        onCallButtonClicked.value = true
    }

    fun onClickMessageButton() {
        onMessageButtonClicked.value = true
    }

    fun onClickResolvedButton()
    {
        onResolvedButtonClicked.value = true
    }


}