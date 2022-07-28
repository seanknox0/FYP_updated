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

class EmergencyFragment : Fragment() {

    private lateinit var emergencyViewModel: EmergencyViewModel
    private var _binding: FragmentEmergencyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        emergencyViewModel =
            ViewModelProvider(this).get(EmergencyViewModel::class.java)

        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)
        val root: View = binding.root
/*
        val textView: TextView = binding.textEmergency
        emergencyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

 */

        binding.hotlineBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hse.ie/eng/services/list/4/mental-health-services/nosp/help/"))
            startActivity(i)
        }

        binding.therapyBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iacp.ie/"))
            startActivity(i)
        }

        binding.callBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+999"))
            startActivity(i)
        }

        binding.mapBtn.setOnClickListener {
            activity?.let{
                val i = Intent (it, MapsActivity::class.java)
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