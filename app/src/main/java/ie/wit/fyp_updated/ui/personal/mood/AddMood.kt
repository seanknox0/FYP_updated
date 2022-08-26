package ie.wit.fyp_updated.ui.personal.mood

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.databinding.ActivityAddMoodBinding


class AddMood : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    private lateinit var binding: ActivityAddMoodBinding

    private lateinit var dbRef: DatabaseReference

    private lateinit var uuid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Add Mood Log"

        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/MoodLogs")

        binding.addMoodBtn.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {

        //get values
        val day = binding.etMoodDay.text.toString()
        val date = binding.etMoodDate.text.toString()
        val moodLevel = binding.slider.value

        val moodId = dbRef.push().key!!

        val mood = Mood(day, date, moodLevel, moodId)

        dbRef.child(moodId).setValue(mood)
            .addOnCompleteListener {
                Toast.makeText(this, "Mood Log Saved", Toast.LENGTH_SHORT).show()

                binding.etMoodDay.text.clear()
                binding.etMoodDate.text.clear()
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}