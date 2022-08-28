package ie.wit.fyp_updated.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Adapted from the following reference: https://www.youtube.com/watch?v=5VsRFJjyMjU&list=LL&index=6

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}