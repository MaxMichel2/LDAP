package com.worldline.bootstrap.domain.api

import android.text.Editable
import com.worldline.bootstrap.domain.model.ExampleResponse
import com.worldline.bootstrap.domain.services.HelloWorldServiceImpl

/**
 * Service interface containing methods to get and set data via a repository.
 *
 * @see HelloWorldServiceImpl
 */
interface HelloWorldService {
    suspend fun getValueToShow(): ExampleResponse
    fun parseValue(text: Editable): String
}
