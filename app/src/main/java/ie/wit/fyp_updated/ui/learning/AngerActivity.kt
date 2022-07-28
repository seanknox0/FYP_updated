package ie.wit.fyp_updated.ui.learning

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ie.wit.fyp_updated.databinding.ActivityAngerBinding

class AngerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngerBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Anger"
    }
}