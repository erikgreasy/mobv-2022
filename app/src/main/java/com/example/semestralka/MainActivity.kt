package com.example.semestralka

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.semestralka.api.UserResponse
import com.example.semestralka.data.LoggedUser
import com.example.semestralka.data.PreferencesData
import com.example.semestralka.databinding.ActivityMainBinding
import com.example.semestralka.viewmodel.AuthViewModel
import com.example.semestralka.viewmodel.AuthViewModelFactory
import com.example.semestralka.viewmodel.LocateViewModel
import com.example.semestralka.viewmodel.LocateViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.coroutines.*
import retrofit2.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory()
    }

    private val locateViewModel: LocateViewModel by viewModels {
        LocateViewModelFactory(this)
    }

    private lateinit var sharedPreferencesData: PreferencesData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferencesData = PreferencesData(applicationContext)

        val loggedUser = sharedPreferencesData.getLoggedUser()

        Log.e("ASD", loggedUser.toString())

        if(loggedUser != null) {
            authViewModel.isLoggedIn.value = true
            authViewModel.loggedUser.value = UserResponse(
                loggedUser.uid,
                loggedUser.access,
                loggedUser.refresh
            )
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        val bottomNav = binding.bottonnav
        bottomNav.setupWithNavController(navController)

        authViewModel.isLoggedIn.observe(this, Observer {
            if(it) {
                binding.bottonnav.isVisible = true
                navController.navigate(R.id.action_bar_list)

                sharedPreferencesData.setLoggedUser(LoggedUser(
                    authViewModel.loggedUser.value?.uid!!,
                    authViewModel.loggedUser.value?.access!!,
                    authViewModel.loggedUser.value?.refresh!!,
                ))

                Log.e("ASDASD", sharedPreferencesData.getLoggedUser().toString())
            } else {
                binding.bottonnav.isVisible = false
                navController.navigate(R.id.loginFragment)

                sharedPreferencesData.removeLoggedUser()
            }
        })

        val logoutBtn: BottomNavigationItemView = findViewById(R.id.logout)

        logoutBtn.setOnClickListener {
            authViewModel.logout()
        }

//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            Log.e("GREASY", "navigation")
//            Log.e("GREASY", destination.id.toString())
//            Log.e("GREASY", R.id.loginFragment.toString())
//
//            // check if trying to access non auth views without being logged in
//            if(
//                authViewModel.isLoggedIn.value == false && destination.id != R.id.loginFragment
//            ) {
//                navController.navigate(R.id.action_login)
//                false
//            }
//        }

        setupActionBarWithNavController(navController)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode != 1) {
            // TODO HANDLE
            return
        }

        if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Prosím skontrolujte povolenie prístupu k polohe v nastaveniach", Toast.LENGTH_SHORT).show()
            return
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
            return
        }

        // permision granted
        locateViewModel.getLocation()
    }
}