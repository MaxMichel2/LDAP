package com.demont.ldap.domain.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.widget.Toast
import java.util.concurrent.Executor
import timber.log.Timber

class LDAPBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.i("Broadcast receiver called")
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
                            Timber.i("State: $state")
                        }
                    }
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
