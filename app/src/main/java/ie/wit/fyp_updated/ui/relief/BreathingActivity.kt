package ie.wit.fyp_updated.ui.relief

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.fyp_updated.databinding.ActivityBreathingBinding

class BreathingActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBreathingBinding
    private lateinit var timer: CountDownTimer

    //ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreathingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Take a Breath"

        binding.btnBreath1.setOnClickListener{
            //Breathe in
            binding.tvTask.text = "Breathe in!"
            startExercise()
        }

        binding.btnBreath2.setOnClickListener{
            //Hold Breath
            binding.tvTask.text = "Hold Breath!"
            startExercise()
        }

        binding.btnBreath3.setOnClickListener{
            //Hold Breath
            binding.tvTask.text = "Breathe out!"
            startExercise()
        }
    }

    private fun startExercise() {
        timer = object : CountDownTimer(4_000, 1_000) {
            override fun onTick(remaining: Long) {
                var milli = remaining
                val secondsInMilli: Long = 1000
                val elapsedSeconds = milli / secondsInMilli
                binding.tvCount.text = elapsedSeconds.toString()
            }

            override fun onFinish() {
                binding.tvCount.text = "Done!"
            }
        }
        timer.start()
    }
}
