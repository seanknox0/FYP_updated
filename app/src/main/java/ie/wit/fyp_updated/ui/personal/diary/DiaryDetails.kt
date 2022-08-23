package ie.wit.fyp_updated.ui.personal.diary

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.ActivityEntryDetailsBinding
import ie.wit.fyp_updated.databinding.ActivityUpdateDiaryBinding

class DiaryDetails : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    private lateinit var binding: ActivityEntryDetailsBinding
    private lateinit var binding2: ActivityUpdateDiaryBinding

    private lateinit var dbRef: DatabaseReference

    private lateinit var uuid:String

    /*private lateinit var tvEntryTitle: TextView
    private lateinit var tvEntryDesc: TextView
    private lateinit var tvEntryDate: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityUpdateDiaryBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Diary Details"

        //initView()
        //setValuesToViews()

        binding2.etUpTitle.setText(intent.getStringExtra("entryTitle").toString())
        binding2.etUpDesc.setText(intent.getStringExtra("entryDesc").toString())
        binding2.etUpDate.setText(intent.getStringExtra("entryDate").toString())

        binding2.btnUpdateData.setOnClickListener{
            updateEntryData(
                binding2.etUpTitle.text.toString(),
                binding2.etUpDesc.text.toString(),
                binding2.etUpDate.text.toString(),
                intent.getStringExtra("entryId").toString()
            )
        }

        binding2.btnDeleteData.setOnClickListener{
            deleteEntryData(intent.getStringExtra("entryId").toString())
        }

        /*binding.btnUpdate.setOnClickListener{
            openUpdateDialog(intent.getStringExtra("entryId").toString(), intent.getStringExtra("entryTitle").toString())
        }*/
    }

    private fun deleteEntryData(id: String) {
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Entries").child(id)

        val task = dbRef.removeValue()
        task.addOnSuccessListener {
            Toast.makeText(this, "Diary Entry Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DiaryActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateEntryData(title: String, desc: String, date: String, id: String) {
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Entries").child(id)

        val entryInfo = Entry(title, desc, date, id)
        dbRef.setValue(entryInfo)
        Toast.makeText(applicationContext, "Diary Entry Updated", Toast.LENGTH_LONG).show()
    }

    /*private fun openUpdateDialog(entryId: String, entryTitle: String){
        val myDialog = Dialog(this)
        binding2 = ActivityUpdateDiaryBinding.inflate(layoutInflater)
        myDialog.setContentView(binding2.root)
        //val inflater = layoutInflater
        //val myDialogView = inflater.inflate(R.layout.activity_update_diary, null)

        //myDialog.setView(myDialogView)

        *//*val etUpTitle = myDialogView.findViewById<EditText>(R.id.etUpTitle)
        val etUpDesc = myDialogView.findViewById<EditText>(R.id.etUpDesc)
        val etUpDate = myDialogView.findViewById<EditText>(R.id.etUpDate)
        val btnUpdateData = myDialogView.findViewById<Button>(R.id.btnUpdateData)*//*

        binding2.etUpTitle.setText(intent.getStringExtra("entryTitle").toString())
        binding2.etUpDesc.setText(intent.getStringExtra("entryDesc").toString())
        binding2.etUpDate.setText(intent.getStringExtra("entryDate").toString())

        myDialog.setTitle("Updating $entryTitle Entry")

        //val alertDialog = myDialog.create()
        myDialog.show()

        binding2.btnUpdateData.setOnClickListener{
            updateEntryData(
                entryId,
                binding2.etUpTitle.text.toString(),
                binding2.etUpDesc.text.toString(),
                binding2.etUpDate.text.toString()
            )
        }
        Toast.makeText(applicationContext, "Diary Entry Updated", Toast.LENGTH_LONG).show()

        //Setting updated data to TextViews
        binding.tvEntryTitle.text = binding2.etUpTitle.text.toString()
        binding.tvEntryDesc.text = binding2.etUpDesc.text.toString()
        binding.tvEntryDate.text = binding2.etUpDate.text.toString()

        myDialog.dismiss()
    }*/

    /*private fun initView(){
        tvEntryTitle = findViewById(R.id.tvEntryTitle)
        tvEntryDesc = findViewById(R.id.tvEntryDesc)
        tvEntryDate = findViewById(R.id.tvEntryDate)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        binding.tvEntryTitle.text = intent.getStringExtra("entryTitle")
        binding.tvEntryDesc.text = intent.getStringExtra("entryDesc")
        binding.tvEntryDate.text = intent.getStringExtra("entryDate")
    }*/
}