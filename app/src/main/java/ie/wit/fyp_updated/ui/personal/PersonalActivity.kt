package ie.wit.fyp_updated.ui.personal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.ActivityAccountBinding
import ie.wit.fyp_updated.databinding.ActivityMainBinding
import ie.wit.fyp_updated.databinding.ActivityPersonalBinding
import ie.wit.fyp_updated.ui.login.LoginActivity

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

        binding.diaryBtn.setOnClickListener {
            startActivity(Intent(this, DiaryActivity::class.java))
        }
    }


}