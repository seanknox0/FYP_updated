package ie.wit.fyp_updated.ui.learning

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ie.wit.fyp_updated.databinding.FragmentLearningBinding
import ie.wit.fyp_updated.ui.emergency.MapsActivity

// Adapted from the following reference: https://www.youtube.com/watch?v=5VsRFJjyMjU&list=LL&index=6
//                                       https://stackoverflow.com/questions/53355786/kotlin-open-new-activity-inside-of-a-fragment

class LearningFragment : Fragment() {

    private lateinit var learningViewModel: LearningViewModel
    private var _binding: FragmentLearningBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    // Creating the view on screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

        // Adapt the view from view model
    ): View? {
        learningViewModel =
            ViewModelProvider(this).get(LearningViewModel::class.java)

        // View binding configured to access layout components
        _binding = FragmentLearningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Direct user to the anger informational page
        binding.angBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, AngerActivity::class.java)
                it.startActivity(i)
            }
        }

        // Direct user to the anxiety informational page
        binding.anxBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, AnxietyActivity::class.java)
                it.startActivity(i)
            }
        }

        // Direct user to the stress informational page
        binding.stressBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, StressActivity::class.java)
                it.startActivity(i)
            }
        }

        // Direct user to the depression informational page
        binding.depBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, DepressionActivity::class.java)
                it.startActivity(i)
            }
        }

        return root
    }

    // Remove the view from screen
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}