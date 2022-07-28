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
import com.google.firebase.database.*
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.ActivityDiaryBinding


class DiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    private lateinit var entryRecyclerView: RecyclerView

    private lateinit var listEntries: ArrayList<Entry>

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Diary"

        //add dummy data
        /*listEntries.add(Entry("Feeling quite happy today!", "I get to meet all of my old school friends today and were going out for food", ""))
        listEntries.add(Entry("I messed up", "I think I failed my final year exams, they went horribly and now im sad", ""))
        listEntries.add(Entry("Im very anxious", "I am worried about a presentation at work later today, I think I need to prepare more", ""))*/

        entryRecyclerView = findViewById(R.id.rvEntry)
        entryRecyclerView.layoutManager = LinearLayoutManager(this)
        entryRecyclerView.setHasFixedSize(true)

        listEntries = arrayListOf<Entry>()

        getEntryData()
    }

    private fun getEntryData() {
        dbRef = FirebaseDatabase.getInstance("https://fyp-login-signup-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Entries")

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


    /*inner class MyEntriesAdapter(context: Context, private val entries: ArrayList<Entry>): BaseAdapter() {

        var listEntries=ArrayList<Entry>()

        constructor(listEntriesAdapter:ArrayList<Entry>): super(){
            this.listEntries=listEntries
        }

        //This is the view holder
        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            internal val ticketTitle = itemView.findViewById<View>(R.id.tvTitle)
            internal val ticketDesc = itemView.findViewById<View>(R.id.tvDesc)
        }

        //This is where you return your own ViewHolder with your layout
        fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(R.layout.ticket, parent, false)
            return MyViewHolder(itemView)
        }

        private val layoutInflater = LayoutInflater.from(context)

        override fun getView(pos: Int, view: View?, viewG: ViewGroup?): View? {
            val viewHolder: ViewHolder
            val ticketView: View?

            if (view == null) {
                ticketView = layoutInflater.inflate(R.layout.ticket, viewG, false)
                viewHolder = ViewHolder(ticketView)
                ticketView.tag = viewHolder
            }

            else {
                ticketView = view
                viewHolder = ticketView.tag as ViewHolder
            }

            viewHolder.entryTitle.text = entries[pos].entryTitle
            viewHolder.entryDesc.text = entries[pos].entryDesc

            return ticketView
        }

        override fun getItem(pos: Int): Any {
            return 0
        }

        override fun getItemId(pos: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return entries.size
        }
    }

    private class ViewHolder(view: View?){
        val entryTitle = view?.findViewById(R.id.tvTitle) as TextView
        val entryDesc = view?.findViewById(R.id.tvDesc) as TextView
    }
    */
}
