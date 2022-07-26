package com.demont.ldap.domain.services

import android.text.Editable
import com.demont.ldap.domain.api.HelloWorldService
import com.demont.ldap.domain.model.ExampleResponse
import com.demont.ldap.domain.repositories.HelloWorldRepository
import javax.inject.Inject

/**
 * An implementation of [HelloWorldService] which takes care of mapping the service functions to
 * repository functions (in this case [HelloWorldRepository] functions).
 *
 * @property helloWorldRepository The repository with which to call functions in the **data**
 * module
 */
class HelloWorldServiceImpl @Inject constructor(
    private val helloWorldRepository: HelloWorldRepository
) : HelloWorldService {

    override suspend fun getValueToShow(): ExampleResponse {
        return helloWorldRepository.queryForStringToShow()
    }

    override fun parseValue(text: Editable): String {
        return text.toString().ifEmpty { "No name" }
    }
}
