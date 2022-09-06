package com.demont.ldap.domain.services

import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.demont.ldap.domain.model.PreferenceKey
import com.demont.ldap.domain.preferences.PreferenceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class LDAPScreeningService : CallScreeningService() {
    private val scope = CoroutineScope(SupervisorJob())

    @Inject
    lateinit var repository: PreferenceRepository

    override fun onScreenCall(callDetails: Call.Details) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            callDetails.callDirection == Call.Details.DIRECTION_INCOMING
        ) {
            val phoneNumber = callDetails.handle.schemeSpecificPart
            Timber.i("Number: $phoneNumber")
            Timber.i(callDetails.toString())
            //Toast.makeText(baseContext, "Phone number: $phoneNumber", Toast.LENGTH_LONG).show()
            val callerName = when (phoneNumber) {
                "+41763584146" ->  "WENDLINGER Damien"
                "+41767773885"-> "DEMONT Christophe"
                else -> "Inconnu"
                }
            for (i in 0..2) {
                Toast.makeText(applicationContext, "Appel de: $callerName" , Toast.LENGTH_LONG).show()
            }
            scope.launch {
                repository.updatePreference(PreferenceKey.CALLING_PHONE_NUMBER, phoneNumber)
            }
            val callRespond = CallResponse.Builder()
            respondToCall(callDetails, callRespond.build())
        }
    }
}
