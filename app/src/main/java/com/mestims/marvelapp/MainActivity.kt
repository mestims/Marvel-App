package com.mestims.marvelapp

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mestims.design_system.extensions.viewBinding
import com.mestims.marvelapp.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavController(binding.toolbarApp)
        installSplashScreen()
        setupPager()
    }

    private fun setupNavController(toolbar: Toolbar) {
//        val appBarConfiguration = AppBarConfiguration()
    }

    private fun setupPager() {
        val pagerAdapter = MainAdapter(this)
        binding.pager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (binding.pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }


}