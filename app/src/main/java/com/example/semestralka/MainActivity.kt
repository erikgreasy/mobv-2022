package com.example.semestralka

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory
import com.example.semestralka.ui.viewmodel.LocateViewModel
import com.example.semestralka.ui.viewmodel.LocateViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.coroutines.*
import retrofit2.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory()
    }

    private val locateViewModel: LocateViewModel by viewModels {
        LocateViewModelFactory(this, authViewModel)
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