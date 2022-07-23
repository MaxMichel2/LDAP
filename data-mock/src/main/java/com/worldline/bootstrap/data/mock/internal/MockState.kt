package com.worldline.bootstrap.data.mock.internal

/**
 * State for mock methods
 */
enum class MockState {
    SUCCESS, // Use this to always generate a success
    FAILURE, // Use this to always generate a failure (with different codes according to the current time)
    SUCCESS_AND_FAILURE, // Use this to generate a success or a failure according to the current time
    OTHER // Use this for any other scenario
}

const val DEFAULT_VALUE_FOR_MODULO = 2
const val DEFAULT_VALUE_FOR_DIV_SECONDS = 30
