package com.example.photoeditorpolishanything

import LocaleHelper
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.photoeditorpolishanything.databinding.ActivityPolishProPaymentBinding

class Polish_Pro_PaymentActivity : BaseActivity() {

    lateinit var binding : ActivityPolishProPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolishProPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initView()
    }

    private fun initView()
    {
        binding.imgback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtRestore.text = resources.getString(R.string.Restore)
        binding.txtJoinFullExperience.text = resources.getString(R.string.JoinFullExperience)
        binding.txtAppName.text = resources.getString(R.string.app_name)
        binding.txtPro.text = resources.getString(R.string.Pro)
        binding.txtAccessAllfeatures.text = resources.getString(R.string.AccessAllfeatures)
        binding.txtNewfilters.text = resources.getString(R.string.Newfiltersandeffectaddedweekly)
        binding.txtNoadsevermore.text = resources.getString(R.string.Noadsevermore)
        binding.txtAccessAllfeaturenoAds.text = resources.getString(R.string.AccesstoAllFeaturesNoAds)
        binding.txt7DayFreeTrial.text = resources.getString(R.string.sevenDayFreeTrial)
        binding.txtThan1000Year.text = resources.getString(R.string.ThanOneThousandYear)
        binding.txtOneTimePurchase.text = resources.getString(R.string.ThenTwoThousandFiftyOneTimePurchase)
        binding.txtCaption.text = resources.getString(R.string.Caption)
    }
}