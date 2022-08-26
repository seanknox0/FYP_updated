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

class MoodActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMoodBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //private lateinit var imageView: ImageView

    private lateinit var moodRecyclerView: RecyclerView

    private lateinit var listMoods: ArrayList<Mood>

    private lateinit var dbRef: DatabaseReference

    private lateinit var uuid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Mood Logging"

        moodRecyclerView = findViewById(R.id.rvMood)
        moodRecyclerView.layoutManager = LinearLayoutManager(this)
        moodRecyclerView.setHasFixedSize(true)

        listMoods = arrayListOf<Mood>()

        getMoodData()
    }


    private fun getMoodData() {
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/MoodLogs")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMoods.clear()
                if (snapshot.exists()){
                    for (moodSnap in snapshot.children){
                        val moodData = moodSnap.getValue(Mood::class.java)
                        listMoods.add(moodData!!)
                    }
                    var myAdapter = MoodAdapter(listMoods)
                    binding.rvMood.adapter = myAdapter

                    myAdapter.setOnItemClickListener(object : MoodAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent =  Intent(this@MoodActivity, MoodDetails::class.java)

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


    // SEARCH NOT FUNCTIONAL //////////////////////
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
    ///////////////////////////////////////////////
}