package com.olx.sample

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.olx.permify.Permify
import com.olx.permify.callback.PermissionCallback
import com.olx.sample.databinding.ActivityNormalBinding

class NormalActivity : AppCompatActivity(), PermissionCallback {

    private lateinit var binding: ActivityNormalBinding

    private val listPermission = listOf<String>(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNormalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvPostNotificationPermission.setOnClickListener {
            Permify.requestPermission(
                activity = this,
                showDialogs = false,
                permissionCallback = this,
                permissions = listOf(Manifest.permission.POST_NOTIFICATIONS)
            )
        }

//        binding.tvReadPhoneStatePermission.setOnClickListener {
//            Permify.requestPermission(
//                activity = this,
//                permissions = listOf(Manifest.permission.READ_PHONE_STATE),
//                requestMessage = "OLX needs following permissions to continue",
//                openSettingMessage = "Please allow following permissions in settings"
//            )
//        }

        binding.tvOpenFragment.setOnClickListener {
            openFragment(binding)
        }
    }

    private fun openFragment(binding: ActivityNormalBinding) {
        binding.frOpenFragment.visibility = View.VISIBLE
        val fragment = PermissionFragment()

        supportFragmentManager.beginTransaction().replace(R.id.fr_open_fragment, fragment).commit()

        findViewById<LinearLayout>(R.id.ll_buttons).visibility = View.GONE
    }

    override fun onResult(
        allGranted: Boolean, grantedList: List<String>, deniedList: List<String>
    ) {
        Log.d("Permify", "All granted: $allGranted")
        Log.d("Permify", "Granted permissions: $grantedList")
        Log.d("Permify", "Denied permissions: $deniedList")
    }
}
