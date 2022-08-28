package ie.wit.fyp_updated.ui.personal.mood

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.databinding.ActivityAddMoodBinding

// Adapted from the following references: https://youtu.be/MFcMw9jJA9o
//                                        https://youtu.be/miJooBq9iwE
//                                        https://youtu.be/vMUTGECnU_4

class AddMood : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    //view binding
    private lateinit var binding: ActivityAddMoodBinding

    //database reference
    private lateinit var dbRef: DatabaseReference

    // unique id
    private lateinit var uuid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Add Mood Log"

        //users id
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // path to the db mood logs data
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/MoodLogs")

        // save data in db
        binding.addMoodBtn.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        //get values
        val day = binding.etMoodDay.text.toString()
        val date = binding.etMoodDate.text.toString()
        val moodLevel = binding.slider.value

        // create the id for mood log
        val moodId = dbRef.push().key!!

        val mood = Mood(day, date, moodLevel, moodId)

        // add the data with the formed object
        dbRef.child(moodId).setValue(mood)
            .addOnCompleteListener {
                Toast.makeText(this, "Mood Log Saved", Toast.LENGTH_SHORT).show()

                //clear fields
                binding.etMoodDay.text.clear()
                binding.etMoodDate.text.clear()
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}