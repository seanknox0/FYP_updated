package ie.wit.fyp_updated.ui.personal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.ActivityAccountBinding
import ie.wit.fyp_updated.databinding.ActivityMainBinding
import ie.wit.fyp_updated.databinding.ActivityPersonalBinding

class PersonalActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPersonalBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Personal"
    }
}