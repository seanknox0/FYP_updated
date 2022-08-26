package ie.wit.fyp_updated.ui.personal.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ie.wit.fyp_updated.R

class MoodAdapter(private val listMoods: ArrayList<Mood>) :
    RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mood_item_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMood = listMoods[position]
        holder.tvMoodDay.text = currentMood.day
        holder.tvMoodDate.text = currentMood.date
        holder.tvMoodLevel.text = currentMood.moodLevel.toString()
    }

    override fun getItemCount(): Int {
        return listMoods.size
    }

    class ViewHolder(itemView: View, clickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvMoodDay : TextView = itemView.findViewById(R.id.tvMoodDay)
        val tvMoodDate : TextView = itemView.findViewById(R.id.tvMoodDate)
        val tvMoodLevel : TextView = itemView.findViewById(R.id.tvMoodLevel)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}