package com.demont.ldap.domain.services

import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.annotation.RequiresApi
import com.demont.ldap.domain.model.PreferenceKey
import com.demont.ldap.domain.preferences.PreferenceRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

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

            scope.launch {
                repository.updatePreference(PreferenceKey.CALLING_PHONE_NUMBER, phoneNumber)
            }
        }
    }
}
