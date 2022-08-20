package ie.wit.fyp_updated.ui.personal.diary

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.ActivityDiaryBinding
import ie.wit.fyp_updated.databinding.DiaryItemListBinding


class DiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    private lateinit var entryRecyclerView: RecyclerView

    private lateinit var listEntries: ArrayList<Entry>

    private lateinit var dbRef: DatabaseReference

    private lateinit var uuid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Diary"

        entryRecyclerView = findViewById(R.id.rvEntry)
        entryRecyclerView.layoutManager = LinearLayoutManager(this)
        entryRecyclerView.setHasFixedSize(true)

        listEntries = arrayListOf<Entry>()

        getEntryData()
    }


    private fun getEntryData() {
        uuid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/${uuid}/Entries")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listEntries.clear()
                if (snapshot.exists()){
                    for (entrySnap in snapshot.children){
                        val entryData = entrySnap.getValue(Entry::class.java)
                        listEntries.add(entryData!!)
                    }
                    var myAdapter = EntryAdapter(listEntries)
                    binding.rvEntry.adapter = myAdapter

                    myAdapter.setOnItemClickListener(object : EntryAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent =  Intent(this@DiaryActivity, DiaryDetails::class.java)

                            intent.putExtra("entryId", listEntries[position].entryId)
                            intent.putExtra("entryTitle", listEntries[position].entryTitle)
                            intent.putExtra("entryDesc", listEntries[position].entryDesc)
                            intent.putExtra("entryDate", listEntries[position].entryDate)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.addDiary -> {
                    // go to add page
                    var intent = Intent(this, AddDiary::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
