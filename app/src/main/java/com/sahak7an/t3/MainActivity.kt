package com.sahak7an.t3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sahak7an.t3.databinding.ActivityMainBinding
import com.sahak7an.t3.core.ui.SessionManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val graph = navController.navInflater.inflate(R.navigation.nav_graph).apply {
            if (SessionManager.isLoggedIn(this@MainActivity)) {
                setStartDestination(R.id.homeFragment)
            }
        }
        navController.setGraph(graph, null)

        val bottomNav: BottomNavigationView = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
            val isLogin = destination.id == R.id.loginFragment
            bottomNav.visibility = if (isLogin) View.GONE else View.VISIBLE
        }
        // no-op: graph already points to correct start destination
    }
}