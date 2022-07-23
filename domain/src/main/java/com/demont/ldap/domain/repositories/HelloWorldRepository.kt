package com.demont.ldap.domain.repositories

import com.demont.ldap.domain.model.ExampleResponse

/**
 * Repository used to handle converting Service or UseCase functions into API endpoint requests.
 */
interface HelloWorldRepository {
    suspend fun queryForStringToShow(): ExampleResponse
}
