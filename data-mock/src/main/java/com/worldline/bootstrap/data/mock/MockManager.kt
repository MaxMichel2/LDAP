package com.worldline.bootstrap.data.mock

import android.content.Context
import com.worldline.bootstrap.data.mock.internal.AssetProvider
import com.worldline.bootstrap.data.mock.internal.MockWebServerDispatcher
import kotlinx.coroutines.*
import okhttp3.mockwebserver.MockWebServer

class MockManager {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Initialize a local mock web server
     *
     * @return The server address (scheme + host + port)
     */
    fun init(context: Context): String = runBlocking {
        val dispatcher = MockWebServerDispatcher(AssetProvider(context.assets))

        val async = scope.async {
            MockWebServer()
                .apply {
                    start()
                    this.dispatcher = dispatcher
                }
                .let { mockWS ->
                    "http://${mockWS.hostName}:${mockWS.port}"
                }
        }

        async.await()
    }
}
