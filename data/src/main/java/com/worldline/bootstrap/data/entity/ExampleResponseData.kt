package com.worldline.bootstrap.data.entity

import com.worldline.bootstrap.domain.model.ExampleResponse

data class ExampleResponseData(
    val value: String
)

fun ExampleResponseData.mapToDomain() = ExampleResponse(
    value = value
)
