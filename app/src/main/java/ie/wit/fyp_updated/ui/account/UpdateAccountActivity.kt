package ie.wit.fyp_updated.ui.account

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.databinding.ActivityUpdateAccountBinding

class UpdateAccountActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityUpdateAccountBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var uuid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Update Account"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.updateAccountBtn.setOnClickListener {
            val firstName = binding.uName.text.toString()
            val address = binding.uAddress.text.toString()
            val phone = binding.uPhone.text.toString()

            updateData(firstName, address, phone)
        }
    }

    private fun updateData(firstName: String, address: String, phone: String) {
        uuid = firebaseAuth.currentUser!!.uid.toString()
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Profile")

        val user = mapOf<String,String>(
            "firstName" to firstName,
            "address" to address,
            "phoneNum" to phone
        )

        dbRef.updateChildren(user).addOnSuccessListener {
            binding.uName.text.clear()
            binding.uAddress.text.clear()
            binding.uPhone.text.clear()
            Toast.makeText(this, "Account data updated successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
        }
    }
}