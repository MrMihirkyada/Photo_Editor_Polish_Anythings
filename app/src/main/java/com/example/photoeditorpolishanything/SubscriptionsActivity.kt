package com.example.photoeditorpolishanything

import LocaleHelper
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.photoeditorpolishanything.databinding.ActivitySubscriptionsBinding

class SubscriptionsActivity : BaseActivity() {

    lateinit var binding : ActivitySubscriptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.lightBlack)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)


        initview()
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtSubscriptions.text = resources.getString(R.string.Subscriptions)
        binding.txtDiscoversubscriptions.text = resources.getString(R.string.Discoversubscriptions)
        binding.txtSubscriptionsCaption.text = resources.getString(R.string.subscriptionsCaption)
    }

    private fun initview() {
        binding.imgback.setOnClickListener {
            onBackPressed()
        }
    }
}