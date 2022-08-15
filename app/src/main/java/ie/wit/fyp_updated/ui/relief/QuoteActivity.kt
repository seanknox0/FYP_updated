package ie.wit.fyp_updated.ui.relief

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.fyp_updated.databinding.ActivityQuotesBinding


class QuoteActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityQuotesBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //List of inspiring Quotes
    private val quoteList:MutableList<String> = mutableListOf(
        "'When you have a dream, you've got to grab it and never let go' \n— Carol Burnett",
        "'Nothing is impossible. The word itself says Im possible!' \n— Audrey Hepburn",
        "'There is nothing impossible to they who will try' \n— Alexander the Great",
        "'The bad news is time flies. The good news is you're the pilot' \n— Michael Altshuler",
        "'Keep your face always toward the sunshine, and shadows will fall behind you' \n— Walt Whitman",
        "'Success is not final, failure is not fatal: it is the courage to continue that counts' \n- Winston Churchill",
        "'You define your own life. Don't let other people write your script' \n— Oprah Winfrey",
        "'You are never too old to set another goal or to dream a new dream' \n— Malala Yousafzai",
        "'It is during our darkest moments that we must focus to see the light' \n— Aristotle",
        "'Weaknesses are just strengths in the wrong environment' \n— Marianne Cantwell")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Inspiring Quotes"

        genQuotes()
    }

    private fun genQuotes() {
        binding.tvQuote.setOnClickListener{
            val random: Int = (0 until(quoteList.size)).random()
            binding.tvQuote.text = quoteList[random]
        }
    }
}