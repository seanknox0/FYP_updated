package ie.wit.fyp_updated.ui.personal.diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.fyp_updated.R

class EntryAdapter(private val listEntries: ArrayList<Entry>) :
    RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.diary_item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEntry = listEntries[position]
        holder.tvEntryTitle.text = currentEntry.entryTitle
        holder.tvEntryDesc.text = currentEntry.entryDesc
        holder.tvEntryDate.text = currentEntry.entryDate
    }

    override fun getItemCount(): Int {
        return listEntries.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvEntryTitle : TextView = itemView.findViewById(R.id.tvEntryTitle)
            val tvEntryDesc : TextView = itemView.findViewById(R.id.tvEntryDesc)
            val tvEntryDate : TextView = itemView.findViewById(R.id.tvEntryDate)
    }
}