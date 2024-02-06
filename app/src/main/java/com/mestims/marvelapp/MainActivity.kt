package com.mestims.marvelapp

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mestims.design_system.extensions.viewBinding
import com.mestims.marvelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.navHostContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavController(binding.toolbarApp)
        setSupportActionBar(binding.toolbarApp)
        installSplashScreen()
    }

    private fun setupNavController(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestinations = appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (!isTopLevelDestinations) {
//                toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
//                toolbar.setNavigationOnClickListener {
//                    navController.popBackStack()
//                }
            }
        }
    }


}