package ie.wit.fyp_updated.ui.personal.diary

import android.app.Dialog
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
        binding = ActivityEntryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Diary Details"

        //initView()
        setValuesToViews()

        binding.btnUpdate.setOnClickListener{
            openUpdateDialog(intent.getStringExtra("entryId").toString(), intent.getStringExtra("entryTitle").toString())
        }
    }

    private fun openUpdateDialog(entryId: String, entryTitle: String){
        val myDialog = Dialog(this)
        binding2 = ActivityUpdateDiaryBinding.inflate(layoutInflater)
        myDialog.setContentView(binding2.root)
        //val inflater = layoutInflater
        //val myDialogView = inflater.inflate(R.layout.activity_update_diary, null)

        //myDialog.setView(myDialogView)

        /*val etUpTitle = myDialogView.findViewById<EditText>(R.id.etUpTitle)
        val etUpDesc = myDialogView.findViewById<EditText>(R.id.etUpDesc)
        val etUpDate = myDialogView.findViewById<EditText>(R.id.etUpDate)
        val btnUpdateData = myDialogView.findViewById<Button>(R.id.btnUpdateData)*/

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
    }

    private fun updateEntryData(name: String, desc: String, date: String, id: String) {
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Entries").child(id)

        val entryInfo = Entry(name, desc, date, id)
        dbRef.setValue(entryInfo)
    }

    /*private fun initView(){
        tvEntryTitle = findViewById(R.id.tvEntryTitle)
        tvEntryDesc = findViewById(R.id.tvEntryDesc)
        tvEntryDate = findViewById(R.id.tvEntryDate)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }*/

    private fun setValuesToViews(){
        binding.tvEntryTitle.text = intent.getStringExtra("entryTitle")
        binding.tvEntryDesc.text = intent.getStringExtra("entryDesc")
        binding.tvEntryDate.text = intent.getStringExtra("entryDate")
    }
}