package com.demont.ldap.data.network

import com.demont.ldap.data.entity.ExampleResponseData
import retrofit2.http.GET

/**
 * API interface for defining endpoints and their parameters and return objects.
 *
 * Functions should be suspend and annotated with the appropriate HTTP method annotation such as:
 * - `@GET("<your api endpoint>")`
 * - `@POST("<your api endpoint>")`
 * - `@PATCH("<your api endpoint>")`
 *
 * Function parameters can include (but are not restricted to):
 * - `@Path("<api endpoint placeholder>")`
 * - `@Query("<query parameter name>")`
 * - `@Body`
 *
 * Special content types such as **`application/x-www-form-urlencoded`** require additional
 * annotations in the form:
 *
 * ```
 * @FormUrlEncoded
 * @POST("my/encoded/path")
 * suspend fun postMyEncodedRequest(
 *     @Field("<field name>") fieldName: String = "myFieldValue"
 * )
 * ```
 */
interface BootstrapApi {
    @GET("/path")
    suspend fun getPath(): ExampleResponseData
}
