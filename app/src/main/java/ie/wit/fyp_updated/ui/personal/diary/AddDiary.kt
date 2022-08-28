package ie.wit.fyp_updated.ui.personal.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.databinding.ActivityAddDiaryBinding

// Adapted from the following references: https://youtu.be/MFcMw9jJA9o
//                                        https://youtu.be/miJooBq9iwE
//                                        https://youtu.be/vMUTGECnU_4

class AddDiary : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    // view binding
    private lateinit var binding: ActivityAddDiaryBinding

    //database reference
    private lateinit var dbRef: DatabaseReference

    // unique id
    private lateinit var uuid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Add Diary"

        // get the users unique id
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // database path to the entries data
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Entries")

        //button to save the data to db
        binding.addBtn.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        //get values
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val date = binding.etDate.text.toString()

        // create the id
        val entryId = dbRef.push().key!!

        // form the data into the data class
        val entry = Entry(title, desc, date, entryId)

        // add the data to the db
        dbRef.child(entryId).setValue(entry)
            .addOnCompleteListener {
                Toast.makeText(this, "Entry Saved", Toast.LENGTH_SHORT).show()

                // clear the populated fields
                binding.etTitle.text.clear()
                binding.etDesc.text.clear()
                binding.etDate.text.clear()
            }
            .addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}