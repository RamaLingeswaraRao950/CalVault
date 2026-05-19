package com.calvault.app.activities

import android.content.Intent
import android.os.Bundle
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
    }

    private fun setupItem(includeBinding: LayoutAboutItemBinding, iconRes: Int, title: String, subtitle: String, onClick: () -> Unit) {
        includeBinding.itemIcon.setImageResource(iconRes)
        includeBinding.itemTitle.text = title
        includeBinding.itemSubtitle.text = subtitle
        includeBinding.root.setOnClickListener { onClick() }
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
}
