package com.demont.ldap.domain.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.widget.Toast
import com.demont.ldap.domain.BuildConfig
import com.demont.ldap.domain.preferences.PreferenceRepository
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class LDAPBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: PreferenceRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (BuildConfig.DEBUG) {
            Timber.d("Broadcast receiver called")
        }
        context?.let {

            val telephonyManager: TelephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            val executor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.mainExecutor
            } else {
                Executor {
                    Handler(Looper.getMainLooper()).post(it)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                telephonyManager.registerTelephonyCallback(
                    executor,
                    object : TelephonyCallback(), TelephonyCallback.CallStateListener {
                        override fun onCallStateChanged(state: Int) {
                            Timber.d("State: $state")
                        }
                    }
                )
            } else {
                val ldapPhoneStateListener = LDAPPhoneStateListener(
                    context = context,
                    repository = repository
                )
                telephonyManager.listen(
                    ldapPhoneStateListener,
                    PhoneStateListener.LISTEN_CALL_STATE
                )
            }

            intent?.let {
                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    Toast.makeText(context, "Ringing State", Toast.LENGTH_SHORT).show()
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    Toast.makeText(context, "Received State", Toast.LENGTH_SHORT).show()
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    Toast.makeText(context, "Idle State", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
