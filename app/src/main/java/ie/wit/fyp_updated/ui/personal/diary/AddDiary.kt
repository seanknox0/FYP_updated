package ie.wit.fyp_updated.ui.personal.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.databinding.ActivityAddDiaryBinding


class AddDiary : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    private lateinit var binding: ActivityAddDiaryBinding

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Add Diary"

        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Entries")

        binding.addBtn.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {

        //get values
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val date = binding.etDate.text.toString()

        val uuid = dbRef.push().key!!

        val entry = Entry(title, desc, date)

        dbRef.child(uuid).setValue(entry)
            .addOnCompleteListener {
                Toast.makeText(this, "Entry Saved", Toast.LENGTH_SHORT).show()

                binding.etTitle.text.clear()
                binding.etDesc.text.clear()
                binding.etDate.text.clear()

            }
            .addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}