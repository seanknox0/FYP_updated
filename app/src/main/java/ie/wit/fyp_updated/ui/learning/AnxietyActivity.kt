package ie.wit.fyp_updated.ui.learning

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ie.wit.fyp_updated.databinding.ActivityAnxietyBinding

class AnxietyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnxietyBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnxietyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Anxiety"
    }
}