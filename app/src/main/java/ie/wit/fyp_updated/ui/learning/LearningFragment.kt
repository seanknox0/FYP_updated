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

class LearningFragment : Fragment() {

    private lateinit var learningViewModel: LearningViewModel
    private var _binding: FragmentLearningBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        learningViewModel =
            ViewModelProvider(this).get(LearningViewModel::class.java)

        _binding = FragmentLearningBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.angBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, AngerActivity::class.java)
                it.startActivity(i)
            }
        }

        binding.anxBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, AnxietyActivity::class.java)
                it.startActivity(i)
            }
        }

        binding.stressBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, StressActivity::class.java)
                it.startActivity(i)
            }
        }

        binding.failBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, FailActivity::class.java)
                it.startActivity(i)
            }
        }

        binding.depBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, DepressionActivity::class.java)
                it.startActivity(i)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}