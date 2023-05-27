package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.test.TestApplication
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class CollectorServiceTest {
    private lateinit var collectorService: CollectorService
    private lateinit var apiClient: CollectorApiClient

    @Before
    fun setUp() {
        apiClient = mock(CollectorApiClient::class.java)
        collectorService = CollectorService(apiClient)
    }

    @Test
    fun testGetCollectors() = runTest {
        // Given
        val collectors = listOf(
            CollectorModel(1, "Collector 1", "3308981", "collector@email.com"),
            CollectorModel(2, "Collector 2", "3308982", "collector@email.com"),
        )
        whenever(apiClient.getCollectors()).thenReturn(Response.success(collectors))

        // When
        val result = collectorService.getCollectors()

        // Then
        assertEquals(collectors, result)
    }

    @Test
    fun testGetCollectorById() = runTest {
        // Given
        val collector = CollectorModel(1, "Collector 1", "3308984", "collector@email.com")
        whenever(apiClient.getCollectorById(collector.id)).thenReturn(Response.success(collector))
        whenever(apiClient.getCollectorAlbumsById(collector.id)).thenReturn(
            Response.success(
                emptyList(),
            ),
        )
        whenever(apiClient.getCollectorPerformersById(collector.id)).thenReturn(
            Response.success(
                emptyList(),
            ),
        )

        // When
        val result = collectorService.getCollectorsById(collector.id)

        // Then
        assertEquals(collector, result)
    }
}
