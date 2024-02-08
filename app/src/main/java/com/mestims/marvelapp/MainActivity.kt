package com.mestims.marvelapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import com.mestims.design_system.extensions.viewBinding
import com.mestims.marvelapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavController(binding.toolbarApp)
        installSplashScreen()
        setupPager()
        setupMenu()

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

    private fun setupMenu() {
        setSupportActionBar(binding.toolbarApp)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> {
                        true
                    }

                    else -> false
                }
            }
        }, this)
    }


}