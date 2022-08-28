package ie.wit.fyp_updated.ui.emergency

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ie.wit.fyp_updated.databinding.FragmentEmergencyBinding

// Adapted from the following references: https://www.youtube.com/watch?v=5VsRFJjyMjU&list=LL&index=6
//                                        https://stackoverflow.com/questions/53355786/kotlin-open-new-activity-inside-of-a-fragment
//                                        https://stackoverflow.com/questions/48294379/how-to-make-an-emergency-call-directly-from-default-phone-dialer-without-any-use

class EmergencyFragment : Fragment() {

    private lateinit var emergencyViewModel: EmergencyViewModel
    private var _binding: FragmentEmergencyBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    // Creating the view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    // Adapt the view from view model
    ): View? {
        emergencyViewModel =
            ViewModelProvider(this).get(EmergencyViewModel::class.java)

        // View binding configured to access layout components
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Button to direct user to HSE urgent help page
        binding.hotlineBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hse.ie/eng/services/list/4/mental-health-services/nosp/help/"))
            startActivity(i)
        }

        // Button to direct user to the IACP find a therapist page
        binding.therapyBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iacp.ie/"))
            startActivity(i)
        }

        // Button to input the emergency services number in the phones numpad
        binding.callBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+999"))
            startActivity(i)
        }

        // Directs user to the map of hospitals view
        binding.mapBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, MapsActivity::class.java)
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