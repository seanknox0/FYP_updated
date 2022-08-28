package ie.wit.fyp_updated.ui.personal.mood

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.ActivityMoodBinding
import ie.wit.fyp_updated.ui.personal.diary.DiaryDetails
import ie.wit.fyp_updated.ui.personal.diary.EntryAdapter

// Adapted from the following references: (Not functional) https://stackoverflow.com/questions/63917617/operator-call-corresponds-to-a-dot-qualified-call-list-min-compareto500-wh
//                                        https://youtu.be/GKxPJbp1WNo
//                                        https://youtu.be/DW-d0kalMvU
//                                        https://youtu.be/0oOC9cdN2I0

class MoodActivity: AppCompatActivity() {
    // view binding
    private lateinit var binding: ActivityMoodBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    // ImageViews to update
    //private lateinit var imageView: ImageView
    //RecyclerView
    private lateinit var moodRecyclerView: RecyclerView
    //ArrayList for Mood Logs
    private lateinit var listMoods: ArrayList<Mood>
    //Firebase Database
    private lateinit var dbRef: DatabaseReference
    // Unique ID
    private lateinit var uuid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Mood Logging"

        //Find the RecyclerView by its ID
        moodRecyclerView = findViewById(R.id.rvMood)
        moodRecyclerView.layoutManager = LinearLayoutManager(this)
        moodRecyclerView.setHasFixedSize(true)

        //Initialize ArrayList
        listMoods = arrayListOf<Mood>()

        //obtain data from db
        getMoodData()
    }


    private fun getMoodData() {
        // get id from logged in user
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        // reference to the database data
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/MoodLogs")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMoods.clear()       //clear the arraylist
                if (snapshot.exists()){
                    for (moodSnap in snapshot.children){      //data from snapshot into the arraylist
                        val moodData = moodSnap.getValue(Mood::class.java)
                        listMoods.add(moodData!!)
                    }
                    var myAdapter = MoodAdapter(listMoods)        // Send arraylist into the MoodAdapter
                    binding.rvMood.adapter = myAdapter

                    // When list item is clicked open the details of clicked item
                    myAdapter.setOnItemClickListener(object : MoodAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent =  Intent(this@MoodActivity, MoodDetails::class.java)

                            // obtaining the data from the arraylist at the position of the item list
                            intent.putExtra("day", listMoods[position].day)
                            intent.putExtra("date", listMoods[position].date)
                            intent.putExtra("moodLevel", listMoods[position].moodLevel)
                            intent.putExtra("moodId", listMoods[position].moodId)
                            startActivity(intent)
                        }
                    })

                    // NOT FUNCTIONING
                    //imageView = findViewById(R.id.ivSmile)
                    //setImageView(imageView)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    // NOT FUNCTIONING
    /*private fun setImageView(imageView: ImageView){
        for (position in listMoods) {
            if (position.moodLevel!! >= 8){
                imageView.setBackgroundResource(R.drawable.happy)
            } else if (position.moodLevel!! <= 3){
                imageView.setBackgroundResource(R.drawable.sad)
            } else if (position.moodLevel!! > 3 && position.moodLevel!! < 8){
                imageView.setBackgroundResource(R.drawable.neutral)
            } else{
                imageView.setBackgroundResource(R.drawable.sammy_yellow)
            }
        }
    }*/

    // When the add diary item is clicked, open the AddDiary activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.addMood -> {
                    // go to add page
                    var intent = Intent(this, AddMood::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Get the View and appbar items
    // Search query is not functional - failed to implement from reference
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_mood, menu)
        val searchView = menu!!.findItem(R.id.searchMood).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}