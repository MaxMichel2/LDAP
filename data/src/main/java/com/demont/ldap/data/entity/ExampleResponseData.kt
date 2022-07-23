package com.demont.ldap.data.entity

import com.demont.ldap.domain.model.ExampleResponse

data class ExampleResponseData(
    val value: String
)

fun ExampleResponseData.mapToDomain() = ExampleResponse(
    value = value
)
