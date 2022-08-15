package ie.wit.fyp_updated.ui.relief

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.fyp_updated.databinding.ActivityInstantReliefBinding
import ie.wit.fyp_updated.ui.personal.diary.DiaryActivity

class InstantReliefActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityInstantReliefBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstantReliefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Instant Relief"

        binding.quotesBtn.setOnClickListener {
            startActivity(Intent(this, QuoteActivity::class.java))
        }

        binding.affirmationBtn.setOnClickListener {
            startActivity(Intent(this, QuoteActivity::class.java))
        }

        binding.breathingBtn.setOnClickListener {
            startActivity(Intent(this, QuoteActivity::class.java))
        }
    }
}