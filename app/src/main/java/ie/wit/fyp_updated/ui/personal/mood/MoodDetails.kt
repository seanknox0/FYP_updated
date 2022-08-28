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

// Adapted from the following references: https://youtu.be/l1E3NpA4pII
//                                        https://youtu.be/wonVSkSUAhU
//                                        https://youtu.be/3fBJBJvn5UU
//                                        https://youtu.be/4fy8fPyIFUU

class MoodDetails : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    // view binding
    private lateinit var binding: ActivityMoodDetailsBinding

    // database reference
    private lateinit var dbRef: DatabaseReference

    // unique id
    private lateinit var uuid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Mood Log Details"

        // set the fields with the data of the clicked list item
        binding.etUpMoodDay.setText(intent.getStringExtra("day").toString())
        binding.etUpMoodDate.setText(intent.getStringExtra("date").toString())

        // button to update the data stored in the db
        binding.updateMoodBtn.setOnClickListener {
            updateMoodData(
                binding.etUpMoodDay.text.toString(),
                binding.etUpMoodDate.text.toString(),
                binding.sliderUpdate.value,
                intent.getStringExtra("moodId").toString()
            )
        }
        // button to delete the data from db
        binding.deleteMoodBtn.setOnClickListener {
            deleteMoodData(intent.getStringExtra("moodId").toString())
        }
    }

    // using the id of the entry selected, find the entry in db to delete
    private fun deleteMoodData(id: String) {
        // users id
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // path to the db data
        dbRef =
            FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users/${uuid}/MoodLogs").child(id)

        //delete the data
        val task = dbRef.removeValue()
        task.addOnSuccessListener {
            Toast.makeText(this, "Mood Log Deleted", Toast.LENGTH_LONG).show()

            //start the Mood activity again to view new list
            val intent = Intent(this, MoodActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    // update the data using the new data entered in the fields
    private fun updateMoodData(day: String, date: String, moodLevel: Float, id: String) {
        // users id
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // path to the db data
        dbRef =
            FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users/${uuid}/MoodLogs").child(id)

        // form the object with the entered data
        val moodInfo = Mood(day, date, moodLevel, id)

        //set the value in db to this object
        dbRef.setValue(moodInfo)
        Toast.makeText(applicationContext, "Mood Log Updated", Toast.LENGTH_LONG).show()
    }
}