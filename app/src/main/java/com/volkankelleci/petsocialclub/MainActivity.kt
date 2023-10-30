package com.volkankelleci.petsocialclub


import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.volkankelleci.petsocialclub.CustomFragmentFactory
import com.volkankelleci.petsocialclub.databinding.ActivityMainBinding  // ViewBinding için oluşturulan dosya
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
 // ViewBinding nesnesi
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentFactory: CustomFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.fragmentFactory = fragmentFactory

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}