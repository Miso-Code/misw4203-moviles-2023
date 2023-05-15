package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.test.TestApplication
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class PerformerServiceTest {
    private lateinit var performerService: PerformerService
    private lateinit var apiClient: PerformerApiClient

    @Before
    fun setUp() {
        apiClient = mock(PerformerApiClient::class.java)
        performerService = PerformerService(apiClient)
    }

    @Test
    fun testGetPerformers() = runTest {
        // Given
        val performers = listOf(
            PerformerModel(1, "Performer 1", "cover1.jpg", "2022-01-01", emptyList()),
            PerformerModel(2, "Performer 2", "cover2.jpg", "2022-02-01", emptyList()),
        )
        whenever(apiClient.getPerformers()).thenReturn(Response.success(performers))

        // When
        val result = performerService.getPerformers()

        // Then
        assertEquals(performers, result)
    }

    @Test
    fun testGetPerformerById() = runTest {
        // Given
        val performer = PerformerModel(1, "Performer 1", "cover1.jpg", "2022-01-01", emptyList())
        whenever(apiClient.getPerformerById(performer.id)).thenReturn(Response.success(performer))

        // When
        val result = performerService.getPerformerById(performer.id)

        // Then
        assertEquals(performer, result)
    }
}
