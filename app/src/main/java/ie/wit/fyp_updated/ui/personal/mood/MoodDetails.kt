package ie.wit.fyp_updated.ui.personal.mood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.databinding.ActivityMoodDetailsBinding

class MoodDetails : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    private lateinit var binding: ActivityMoodDetailsBinding

    private lateinit var dbRef: DatabaseReference

    private lateinit var uuid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Mood Log Details"

        binding.etUpMoodDay.setText(intent.getStringExtra("day").toString())
        binding.etUpMoodDate.setText(intent.getStringExtra("date").toString())

        binding.updateMoodBtn.setOnClickListener {
            updateMoodData(
                binding.etUpMoodDay.text.toString(),
                binding.etUpMoodDate.text.toString(),
                binding.sliderUpdate.value,
                intent.getStringExtra("moodId").toString()
            )
        }

        binding.deleteMoodBtn.setOnClickListener {
            deleteMoodData(intent.getStringExtra("moodId").toString())
        }
    }

    private fun deleteMoodData(id: String) {
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef =
            FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users/${uuid}/MoodLogs").child(id)

        val task = dbRef.removeValue()
        task.addOnSuccessListener {
            Toast.makeText(this, "Mood Log Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MoodActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateMoodData(day: String, date: String, moodLevel: Float, id: String) {
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef =
            FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users/${uuid}/MoodLogs").child(id)

        val moodInfo = Mood(day, date, moodLevel, id)
        dbRef.setValue(moodInfo)
        Toast.makeText(applicationContext, "Mood Log Updated", Toast.LENGTH_LONG).show()
    }
}