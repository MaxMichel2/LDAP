package com.worldline.bootstrap.domain.repositories

import com.worldline.bootstrap.domain.model.ExampleResponse

/**
 * Repository used to handle converting Service or UseCase functions into API endpoint requests.
 */
interface HelloWorldRepository {
    suspend fun queryForStringToShow(): ExampleResponse
}
