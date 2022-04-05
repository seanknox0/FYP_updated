package ie.wit.fyp_updated.ui.personal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import ie.wit.fyp_updated.R


class AddDiary : AppCompatActivity() {

    //ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_diary)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Add Diary"
    }

    fun btnAdd(view: View){
        finish()
    }
}