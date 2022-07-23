package com.demont.ldap.data.repositories

import com.demont.ldap.data.db.HelloWorldRepositoryDao
import com.demont.ldap.data.entity.mapToDomain
import com.demont.ldap.data.network.BootstrapApi
import com.demont.ldap.domain.model.ExampleResponse
import com.demont.ldap.domain.repositories.HelloWorldRepository
import javax.inject.Inject

/**
 * Repository implementation used to call API endpoints and map their responses to their domain
 * model counterparts.
 *
 * @property helloWorldRepositoryDao A Room database DAO (Data Access Objects) used to access the
 * database contents
 * @property bootstrapApi The API interface containing the available endpoints to be called
 */
class HelloWorldRepositoryImpl @Inject constructor(
    private val helloWorldRepositoryDao: HelloWorldRepositoryDao,
    private val bootstrapApi: BootstrapApi
) : HelloWorldRepository {

    override suspend fun queryForStringToShow(): ExampleResponse {
        return bootstrapApi.getPath().mapToDomain()
    }
}
