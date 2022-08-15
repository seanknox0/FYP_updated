package ie.wit.fyp_updated.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ie.wit.fyp_updated.databinding.ActivityAccountBinding
import ie.wit.fyp_updated.ui.login.LoginActivity
import ie.wit.fyp_updated.ui.personal.diary.DiaryActivity


class AccountActivity: AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityAccountBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var user: User
    private lateinit var uuid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "My Account"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        uuid = firebaseAuth.currentUser!!.uid.toString()
        if (uuid.isNotEmpty()){
            getUserData()
        }

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        //handle click, update
        binding.upAccountBtn.setOnClickListener {
            startActivity(Intent(this, UpdateAccountActivity::class.java))
        }

        //handle click, delete
        binding.upDeleteBtn.setOnClickListener {
            //TO-DO
        }
    }

    private fun getUserData() {
        val dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Profile")
        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.tvFirstName.setText(user.firstName)
                binding.tvAddress.setText(user.address)
                binding.tvPhoneNum.setText(user.phoneNum)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AccountActivity, "SignUp failed due to ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user not null, user is logged in, get user info
            val email = firebaseUser.email
            //set to text view
            binding.tvEmail.text = email
        } else {
            //user is null, user is not logged in, go to login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}