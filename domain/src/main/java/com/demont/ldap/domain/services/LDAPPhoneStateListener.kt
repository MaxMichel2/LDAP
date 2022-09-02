package com.demont.ldap.domain.services

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.demont.ldap.domain.BuildConfig
import timber.log.Timber

class LDAPPhoneStateListener(context: Context) : PhoneStateListener() {
    private var mContext: Context

    // Runnable which send a messages to the CallerService
    private var mOffhookShutdownMessageTask: Runnable
    private var mIdleShutdownMessageTask: Runnable

    companion object {

        /**
         * Label for number Extras parameter.
         */
        val EXTRAS_NUMBER = "number"

        /**
         * Label for call status Extras parameter.
         */
        val EXTRAS_CALL_STATUS = "call_status"
    }

    /**
     * Intent action.
     */
    private val LDAP_SHUTDOWN: String =
        (this::class.java.`package`?.name) + "LDAP_SHUTDOWN"

    init {
        mContext = context

        mIdleShutdownMessageTask = Runnable {
            sendIdleShutdownMessage()
        }

        mOffhookShutdownMessageTask = Runnable {
            sendOffhookShutdownMessage()
        }
    }

    override fun onCallStateChanged(state: Int, phoneNumber: String?) {
        if (BuildConfig.DEBUG) {
            Timber.d("Inside LDAPPhoneStateListener")
        }

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                if (BuildConfig.DEBUG) {
                    Timber.d("Ringing")
                    Timber.d("Number: $phoneNumber")
                }
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                if (BuildConfig.DEBUG) {
                    Timber.d("Offhook")
                }
                val handleOffHook = Handler(Looper.getMainLooper())
                handleOffHook.post(mOffhookShutdownMessageTask)
            }
            TelephonyManager.CALL_STATE_IDLE -> {
                if (BuildConfig.DEBUG) {
                    Timber.d("Idle")
                }
                val handleIdle = Handler(Looper.getMainLooper())
                handleIdle.post(mIdleShutdownMessageTask)
            }
            else -> if (BuildConfig.DEBUG) {
                Timber.d("Other state: $state")
            }
        }
    }

    /**
     * Send the `LDAP_SHUTDOWN` message when phone status changes to idle.
     */
    private fun sendIdleShutdownMessage() {
        val intent = Intent(LDAP_SHUTDOWN)
        intent.putExtra(EXTRAS_CALL_STATUS, TelephonyManager.CALL_STATE_IDLE)

        mContext.sendBroadcast(intent)
    }

    /**
     * Send the `CALLERID_SHUTDOWN` message when phone status changes to Offhook.
     */
    private fun sendOffhookShutdownMessage() {
        val intent = Intent(LDAP_SHUTDOWN)
        intent.putExtra(EXTRAS_CALL_STATUS, TelephonyManager.CALL_STATE_OFFHOOK)

        mContext.sendBroadcast(intent)
    }
}
