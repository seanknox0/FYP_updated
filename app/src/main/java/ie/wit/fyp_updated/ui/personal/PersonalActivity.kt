package ie.wit.fyp_updated.ui.personal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.fyp_updated.databinding.ActivityPersonalBinding
import ie.wit.fyp_updated.ui.personal.diary.DiaryActivity
import ie.wit.fyp_updated.ui.personal.mood.MoodActivity

class PersonalActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    // view binding
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

        // button to direct user to diary activity
        binding.diaryBtn.setOnClickListener {
            startActivity(Intent(this, DiaryActivity::class.java))
        }

        // button to direct user to mood activity
        binding.moodBtn.setOnClickListener {
            startActivity(Intent(this,  MoodActivity::class.java))
        }
    }


}