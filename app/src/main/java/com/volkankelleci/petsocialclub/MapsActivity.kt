package com.volkankelleci.petsocialclub

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.volkankelleci.petsocialclub.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager:LocationManager
    private lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener= object :LocationListener {
            override fun onLocationChanged(location: Location) {

                val updateLocation=LatLng(location.latitude,location.longitude)
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(updateLocation).title("You're in Here"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(updateLocation,17f))


            }

        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
            !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }
            else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1,1f,locationListener)
            val lastKnownLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (lastKnownLocation!=null){
                val lastKnownLatLng=LatLng(lastKnownLocation.latitude,lastKnownLocation.longitude)
                mMap.addMarker(MarkerOptions().position(lastKnownLatLng).title("Last Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownLatLng,15f))

            }

        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==1){
            if (grantResults.size>0){
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    ==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1,1f,locationListener)
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}