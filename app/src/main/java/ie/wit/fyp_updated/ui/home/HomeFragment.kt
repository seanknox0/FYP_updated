package ie.wit.fyp_updated.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.FragmentHomeBinding

// Adapted from the following reference: https://www.youtube.com/watch?v=5VsRFJjyMjU&list=LL&index=6

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    // Creating the view on screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

        // Adapt the view from view model
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        // View binding configured to access layout components
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    // Remove the view from screen
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}