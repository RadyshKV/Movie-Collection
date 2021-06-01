package com.example.moviecollection.view

import ADDRESS_LINE_INDEX
import INIT_LAT
import INIT_LON
import LINE_WIDTH
import LOCATION_MAX_RESULT
import MAP_ZOOM
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.moviecollection.R
import com.example.moviecollection.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.fragment_map.*
import java.io.IOException

class GoogleMapsFragment : Fragment() {
    private lateinit var map: GoogleMap
    private val markers = mutableListOf<Marker>()
    private lateinit var binding: FragmentMapBinding


    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }

        val initialPlace = LatLng(INIT_LAT, INIT_LON)
        val marker = map.addMarker(
            MarkerOptions().position(initialPlace).title(getString(R.string.marker_start))
        )
        marker?.let { markers.add(it) }
        map.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
        map.setOnMapLongClickListener { latlng ->
            getAddressAsync(latlng)
            drawLine()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initSearchByAddress()
    }

    private fun initSearchByAddress() = with(binding) {
        buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = searchAddress.text.toString()
            Thread {
                try {
                    val addresses =
                        geoCoder.getFromLocationName(searchText, LOCATION_MAX_RESULT)
                    if (addresses.isNotEmpty()) {
                        goToAddress(addresses, it, searchText)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun getAddressAsync(location: LatLng) {
        context?.let {
            val geoCoder = Geocoder(it)
            Thread {
                try {
                    val addresses =
                        geoCoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            LOCATION_MAX_RESULT
                        )
                    textAddress.post {
                        textAddress.text = addresses.first().getAddressLine(ADDRESS_LINE_INDEX)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun goToAddress(addresses: MutableList<Address>, view: View, searchText: String) {
        val location = LatLng(
            addresses.first().latitude,
            addresses.first().longitude
        )
        view.post {
            setMarker(location, searchText)
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    MAP_ZOOM
                )
            )
        }
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val prev: LatLng = markers[last - 1].position
            val cur: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(prev, cur)
                    .color(Color.RED)
                    .width(LINE_WIDTH)
            )
        }
    }

    private fun setMarker(location: LatLng, searchText: String) {
        val marker = map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
        )
        marker?.let { markers.add(it) }
    }

    companion object {
        fun newInstance() = GoogleMapsFragment()
    }

}