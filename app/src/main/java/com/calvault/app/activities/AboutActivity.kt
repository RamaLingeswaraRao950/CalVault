package com.calvault.app.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.calvault.app.R
import com.calvault.app.databinding.ActivityAboutBinding
import com.calvault.app.databinding.LayoutAboutItemBinding

class AboutActivity : AppCompatActivity() {
    private var DEV_GITHUB_URL = ""
    private var GITHUB_URL = ""
    private lateinit var binding : ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        binding.toolBar.setNavigationOnClickListener {
            finish()
        }

        setupAppVersion()
        setupItems()
    }

    private fun setupAppVersion() {
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            binding.appVersion.text = getString(R.string.version, version)
        } catch (e: Exception) {
            e.printStackTrace()
            binding.appVersion.text = ""
        }
    }

    private fun setupItems() {
        DEV_GITHUB_URL = getString(R.string.github_profile)
        GITHUB_URL = getString(R.string.calculator_hide_files, DEV_GITHUB_URL)
        setupItem(binding.developerItem, R.drawable.ic_info, getString(R.string.developer_details), getString(R.string.dev_name)) {
            openUrl(DEV_GITHUB_URL)
        }
        setupItem(binding.sourceCodeItem, R.drawable.ic_github, getString(R.string.view_source_code), getString(R.string.view_the_source_code_of_the_app)) {
            openUrl(GITHUB_URL)
        }

        setupOptionalUrlItem(
            binding.bmcItem,
            R.drawable.buy_me_a_coffee,
            getString(R.string.buy_me_a_coffee),
            getString(R.string.coffee_url)
        )
        setupOptionalUrlItem(
            binding.kofiItem,
            R.drawable.kofi,
            getString(R.string.ko_fi),
            getString(R.string.ko_fi_url)
        )
        setupOptionalCopyItem(
            binding.paypalItem,
            R.drawable.paypal,
            getString(R.string.paypal),
            getString(R.string.paypal_id)
        )
        setupOptionalCopyItem(
            binding.upiItem,
            R.drawable.upi_pay,
            getString(R.string.upi),
            getString(R.string.upi_id)
        )
        setupOptionalUrlItem(
            binding.instagramItem,
            R.drawable.ic_instagram,
            getString(R.string.instagram),
            getString(R.string.instagram_url),
            getString(R.string.instagram_handle)
        )
        setupOptionalUrlItem(
            binding.telegramItem,
            R.drawable.ic_telegram,
            getString(R.string.telegram),
            getString(R.string.telegram_url),
            getString(R.string.telegram_handle)
        )
        setupOptionalCopyItem(
            binding.emailItem,
            R.drawable.ic_mail,
            getString(R.string.email),
            getString(R.string.contact_email)
        )

    }

    private fun setupItem(includeBinding: LayoutAboutItemBinding, iconRes: Int, title: String, subtitle: String, onClick: () -> Unit) {
        includeBinding.itemIcon.setImageResource(iconRes)
        includeBinding.itemTitle.text = title
        includeBinding.itemSubtitle.text = subtitle
        includeBinding.root.setOnClickListener { onClick() }
    }

    private fun setupOptionalUrlItem(
        includeBinding: LayoutAboutItemBinding,
        iconRes: Int,
        title: String,
        url: String,
        subtitleOverride: String? = null,
    ) {
        if (url.isBlank()) {
            includeBinding.root.visibility = View.GONE
            return
        }
        setupItem(includeBinding, iconRes, title, subtitleOverride ?: url) { openUrl(url) }
    }

    private fun setupOptionalCopyItem(
        includeBinding: LayoutAboutItemBinding,
        iconRes: Int,
        title: String,
        value: String,
    ) {
        if (value.isBlank()) {
            includeBinding.root.visibility = View.GONE
            return
        }
        setupItem(includeBinding, iconRes, title, value) { copyToClipboard(value, title) }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(binding.root, getString(R.string.could_not_open_url), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun copyToClipboard(text: String, label: String) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.root, getString(R.string.copied_to_clipboard), Snackbar.LENGTH_SHORT).show()
    }
}
