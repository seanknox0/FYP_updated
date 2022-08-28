package ie.wit.fyp_updated.ui.relief

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.fyp_updated.databinding.ActivityAffirmationBinding

// Adapted from the following reference: https://youtu.be/F5-pqV3L96E

class AffirmationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    // view binding
    private lateinit var binding: ActivityAffirmationBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //List of affirmations
    private val affirmationList:MutableList<String> = mutableListOf(
        "Asking for help is a sign of self-respect and self-awareness.",
        "Changing my mind is a strength, not a weakness.",
        "Every decision I make is supported by my whole and inarguable experience.",
        "I am allowed to ask for what I want and what I need.",
        "I am complete as I am, others simply support me.",
        "I am growing and I am going at my own pace.",
        "I am in charge of how I feel and I choose to feel happy.",
        "I am loved and worthy.",
        "I belong here, and I deserve to take up space.",
        "I do not have to linger in dark places; there is help for me here.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAffirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Affirmations"

        genAffirmations()
    }

    // generates affirmations on screen for user
    private fun genAffirmations() {
        binding.tvAffirmation.setOnClickListener{
            val random: Int = (0 until(affirmationList.size)).random() // gen randomly
            binding.tvAffirmation.text = affirmationList[random]
        }
    }
}