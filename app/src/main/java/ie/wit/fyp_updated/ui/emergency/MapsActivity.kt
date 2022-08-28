package ie.wit.fyp_updated.ui.emergency

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.fyp_updated.R
import ie.wit.fyp_updated.databinding.ActivityMapsBinding

// Adapted from the following reference: https://youtu.be/BO1utHYhsms
//                                       https://youtu.be/FotQIcC91V4

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    // map
    private lateinit var mMap: GoogleMap
    // last location of the user
    private lateinit var lastLocation: Location
    // Fused location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // View binding
    private lateinit var binding: ActivityMapsBinding

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // enable zoom control on the activity
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()

        //list of hard-coded placemarks of hospitals
        val hospital1 = LatLng(52.24062217177415, -7.17235565157701)
        val hospital2 = LatLng(52.24475341889747, -7.0826528483410485)
        val hospital3 = LatLng(52.24791679739438, -7.082890395827738)
        val hospital4 = LatLng(52.24922571562826, -7.078614541067352)
        val hospital5 = LatLng(52.24677867496886, -7.103624372956905)

        // adding a title to the input coordinates
        mMap.addMarker(MarkerOptions().position(hospital1).title("UPMC Whitfield Hospital"))
        mMap.addMarker(MarkerOptions().position(hospital2).title("Regional Waterford Hospital"))
        mMap.addMarker(MarkerOptions().position(hospital3).title("Gate Lodge Clinic"))
        mMap.addMarker(MarkerOptions().position(hospital4).title("University Hospital Waterford"))
        mMap.addMarker(MarkerOptions().position(hospital5).title("St. Otteran's Hospital"))
    }

    // gets the location and starts the map view
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location !=null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)

    }

    override fun onMarkerClick(p0: Marker) = false
}