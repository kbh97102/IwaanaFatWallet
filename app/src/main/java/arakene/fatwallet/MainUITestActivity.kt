package arakene.fatwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import arakene.fatwallet.databinding.MainMenuLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainUITestActivity : AppCompatActivity() {

    private lateinit var binding: MainMenuLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_menu_layout)

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        binding.bottomNavigation.labelVisibilityMode =
            BottomNavigationView.LABEL_VISIBILITY_SELECTED

    }

}