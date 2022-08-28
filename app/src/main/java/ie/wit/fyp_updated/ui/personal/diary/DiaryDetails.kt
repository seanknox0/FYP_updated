package ie.wit.fyp_updated.ui.personal.diary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.databinding.ActivityUpdateDiaryBinding

// Adapted from the following references: https://youtu.be/3fBJBJvn5UU
//                                        https://youtu.be/wonVSkSUAhU
//                                        https://youtu.be/4fy8fPyIFUU
//                                        https://youtu.be/l1E3NpA4pII

class DiaryDetails : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    // view binding
    private lateinit var binding2: ActivityUpdateDiaryBinding

    // database reference
    private lateinit var dbRef: DatabaseReference

    // unique id
    private lateinit var uuid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityUpdateDiaryBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Diary Details"

        // set the fields with the data of the clicked list item
        binding2.etUpTitle.setText(intent.getStringExtra("entryTitle").toString())
        binding2.etUpDesc.setText(intent.getStringExtra("entryDesc").toString())
        binding2.etUpDate.setText(intent.getStringExtra("entryDate").toString())

        // button to update the data stored in the db
        binding2.btnUpdateData.setOnClickListener{
            updateEntryData(
                // extract data from the fields
                binding2.etUpTitle.text.toString(),
                binding2.etUpDesc.text.toString(),
                binding2.etUpDate.text.toString(),
                intent.getStringExtra("entryId").toString()
            )
        }
        // button to delete the data from db
        binding2.btnDeleteData.setOnClickListener{
            deleteEntryData(intent.getStringExtra("entryId").toString())
        }
    }

    // using the id of the entry selected, find the entry in db to delete
    private fun deleteEntryData(id: String) {
        // users id
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // path to the db data
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Entries").child(id)

        //delete the data
        val task = dbRef.removeValue()
        task.addOnSuccessListener {
            Toast.makeText(this, "Diary Entry Deleted", Toast.LENGTH_LONG).show()

            //start the diary activity again to view new list
            val intent = Intent(this, DiaryActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    // update the data using the new data entered in the fields
    private fun updateEntryData(title: String, desc: String, date: String, id: String) {
        // users id
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // path to the db data
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Entries").child(id)

        // form the object with the entered data
        val entryInfo = Entry(title, desc, date, id)

        //set the value in db to this object
        dbRef.setValue(entryInfo)
        Toast.makeText(applicationContext, "Diary Entry Updated", Toast.LENGTH_LONG).show()
    }
}