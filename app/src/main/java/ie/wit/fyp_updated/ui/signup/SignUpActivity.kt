package ie.wit.fyp_updated.ui.signup

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.fyp_updated.MainActivity
import ie.wit.fyp_updated.databinding.ActivitySignUpBinding
import ie.wit.fyp_updated.ui.account.User
import ie.wit.fyp_updated.ui.personal.diary.Entry

// Adapted from the following reference: https://www.youtube.com/watch?v=kxdoLfRL6DY
//                                       https://youtu.be/2ciHixbc4HE
//                                       https://youtu.be/y4npeX35B34

class SignUpActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    //DB Reference
    private lateinit var dbRef: DatabaseReference

    //User Info
    private var firstName = ""
    private var email = ""
    private var password = ""
    private var phoneNum = ""
    private var address = ""
    private lateinit var uuid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Actionbar, // enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //Configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //initialize firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin signup
        binding.signUpBtn.setOnClickListener{
            //validate data
            validateData()
        }
    }

    private fun validateData() {
        //get data
        firstName = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error = "Please enter password"
        }
        else if (password.length <6) {
            //password is less than 6
            binding.passwordEt.error = "Password must be at least 6 characters long"
        }
        else{
            //data is validated, continue signup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()
        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    //signup success
                    progressDialog.dismiss()

                    //get current user
                    val firebaseUser = firebaseAuth.currentUser
                    val email = firebaseUser!!.email
                    Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                    //open profile
                    addToDatabase()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener{e->
                    //signup failed
                    progressDialog.dismiss()
                    Toast.makeText(this, "SignUp failed due to ${e.message}", Toast.LENGTH_SHORT).show()
                }
    }

    // Add the entered data to the database
    private fun addToDatabase() {
        // get user id
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // Path to the users data stored in the database
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Profile")

        // structure the data in the user data class
        val user = User(firstName, email, password, phoneNum, address)
        // add the user to database
        dbRef.setValue(user)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}