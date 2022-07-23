package com.demont.ldap.domain.api

import android.text.Editable
import com.demont.ldap.domain.model.ExampleResponse
import com.demont.ldap.domain.services.HelloWorldServiceImpl

/**
 * Service interface containing methods to get and set data via a repository.
 *
 * @see HelloWorldServiceImpl
 */
interface HelloWorldService {
    suspend fun getValueToShow(): ExampleResponse
    fun parseValue(text: Editable): String
}
