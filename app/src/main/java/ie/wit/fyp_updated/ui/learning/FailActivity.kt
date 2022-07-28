package ie.wit.fyp_updated.ui.learning

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ie.wit.fyp_updated.databinding.ActivityFailBinding

class FailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFailBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Failure"
    }
}