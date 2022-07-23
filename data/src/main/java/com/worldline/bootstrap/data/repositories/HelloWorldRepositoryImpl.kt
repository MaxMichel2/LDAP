package com.worldline.bootstrap.data.repositories

import com.worldline.bootstrap.data.db.HelloWorldRepositoryDao
import com.worldline.bootstrap.data.entity.mapToDomain
import com.worldline.bootstrap.data.network.BootstrapApi
import com.worldline.bootstrap.domain.model.ExampleResponse
import com.worldline.bootstrap.domain.repositories.HelloWorldRepository
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
