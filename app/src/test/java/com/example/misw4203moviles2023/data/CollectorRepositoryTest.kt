package com.example.misw4203moviles2023.data

import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.data.database.DataBaseService
import com.example.misw4203moviles2023.data.database.entities.CollectorAlbumCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.CollectorEntity
import com.example.misw4203moviles2023.data.database.entities.CollectorPerformerCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.CollectorWithAlbumsAndPerformers
import com.example.misw4203moviles2023.data.database.entities.toDataBase
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.data.network.CollectorService
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.collector.model.Collector
import com.example.misw4203moviles2023.domain.performer.model.Performer
import com.example.misw4203moviles2023.test.TestApplication
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class CollectorRepositoryTest {
    private lateinit var repository: CollectorRepository

    @Mock
    private lateinit var mockService: CollectorService

    @Mock
    private lateinit var mockDBService: DataBaseService

    @Before
    @Test
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mockService = mock(CollectorService::class.java)
        mockDBService = mock(DataBaseService::class.java)

        repository = CollectorRepository(context, mockService, mockDBService)
    }

    @Test
    fun `getAllCollectors should return empty list when api returns null`() = runTest {
        // Given
        whenever(mockService.getCollectors()).thenReturn(emptyList())

        // When
        val result = repository.getAllCollectorsFromApi()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllCollectors should return list when api returns non-null`() = runTest {
        // Given
        val collectorsModel = listOf(
            CollectorModel(
                1,
                "Collector 1",
                "123456789",
                "collector1@email.com",
                emptyList(),
                emptyList(),
            ),
            CollectorModel(
                2,
                "Collector 2",
                "987654321",
                "collector2@email.com",
                emptyList(),
                emptyList(),
            ),
        )

        whenever(mockService.getCollectors()).thenReturn(collectorsModel)

        // When
        val result = repository.getAllCollectorsFromApi()

        // Then
        assertEquals(collectorsModel, result)
    }

    @Test
    fun `getAllCollectorsFromDB should return empty list when db returns null`() = runTest {
        // Given
        whenever(mockDBService.getAllCollectorDao()).thenReturn(emptyList())

        // When
        val result = repository.getAllCollectorsFromDB()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllCollectorsFromDB should return list of collectors when api returns non-null response`() =
        runTest {
            // Given
            val collectorsModel = listOf(
                CollectorEntity(
                    1,
                    "Collector 1",
                    "123456789",
                    "collector1@email.com",
                ),
                CollectorEntity(
                    2,
                    "Collector 2",
                    "987654321",
                    "collector2@email.com",
                ),
            )
            whenever(mockDBService.getAllCollectorDao()).thenReturn(collectorsModel)

            val collectors = listOf(
                Collector(
                    1,
                    "Collector 1",
                    "123456789",
                    "collector1@email.com",
                    emptyList(),
                    emptyList(),
                ),
                Collector(
                    2,
                    "Collector 2",
                    "987654321",
                    "collector2@email.com",
                    emptyList(),
                    emptyList(),
                ),
            )

            // When
            val result = repository.getAllCollectorsFromDB()

            // Then
            assertEquals(collectors, result)
        }

    @Test
    fun `getCollectorById should return default collector when api returns null`() = runTest {
        // Given
        val id = 1
        val defaultCollectorModel = CollectorModel(
            1,
            "Collector 1",
            "123456789",
            "collector1@email.com",
            emptyList(),
            emptyList(),
        )
        whenever(mockService.getCollectorsById(id)).thenReturn(defaultCollectorModel)

        // When
        val result = repository.getCollectorByIdFromApi(id)
        val defaultCollector = Collector(
            1,
            "Collector 1",
            "123456789",
            "collector1@email.com",
        )
        // Then
        assertEquals(defaultCollector, result)
    }

    @Test
    fun `getCollectorById should return collector when api returns non-null response`() = runTest {
        // Given
        val id = 1
        val collectorModel = CollectorModel(
            1,
            "Collector 1",
            "123456789",
            "collector1@email.com",
            emptyList(),
            emptyList(),
        )
        whenever(mockService.getCollectorsById(id)).thenReturn(collectorModel)
        // When
        val collector =
            Collector(
                1,
                "Collector 1",
                "123456789",
                "collector1@email.com",
            )
        val result = repository.getCollectorByIdFromApi(id)

        // Then
        assertEquals(collector, result)
    }

    @Test
    fun `getCollectorById should return null when api returns null`() = runTest {
        // Given
        val id = 1
        whenever(mockService.getCollectorsById(id)).thenReturn(null)

        // When
        val result = repository.getCollectorByIdFromApi(id)

        // Then
        assertEquals(null, result)
    }

    @Suppress("LongMethod")
    @Test
    fun `getCollectorById should store collector in db when api returns non-null response`() =
        runTest {
            // Given
            val id = 1
            val collectorModel = CollectorModel(
                1,
                "Collector 1",
                "123456789",
                "collector1@email.com",
                listOf(
                    AlbumModel(
                        1,
                        "Album 1",
                        "album1.jpg",
                        "01-01-2020",
                        "Label 1",
                        "Genre 1",
                        "Record Label",
                        emptyList(),
                    ),
                ),
                listOf(
                    PerformerModel(
                        1,
                        "Performer 1",
                        "performer1",
                        "performer1.jpg",
                        emptyList(),
                    ),
                ),
            )
            whenever(mockService.getCollectorsById(id)).thenReturn(collectorModel)
            whenever(
                mockDBService.insertCollectorWithAlbumDao(
                    CollectorAlbumCrossRefEntity(
                        1,
                        1,
                    ),
                ),
            ).thenReturn(Unit)
            whenever(
                mockDBService.insertCollectorWithPerformerDao(
                    CollectorPerformerCrossRefEntity(
                        1,
                        1,
                    ),
                ),
            ).thenReturn(Unit)

            // When
            val collector =
                Collector(
                    1,
                    "Collector 1",
                    "123456789",
                    "collector1@email.com",
                    listOf(
                        Album(
                            1,
                            "Album 1",
                            "album1.jpg",
                            "01-01-2020",
                            "Label 1",
                            "Genre 1",
                            "Record Label",
                            emptyList(),
                        ),
                    ),
                    listOf(
                        Performer(
                            1,
                            "Performer 1",
                            "performer1",
                            "performer1.jpg",
                            emptyList(),
                        ),
                    ),
                )
            val result = repository.getCollectorByIdFromApi(id)

            // Then
            assertEquals(collector, result)
            verify(mockDBService).insertCollectorWithAlbumDao(
                CollectorAlbumCrossRefEntity(
                    1,
                    1,
                ),
            )
            verify(mockDBService).insertCollectorWithPerformerDao(
                CollectorPerformerCrossRefEntity(
                    1,
                    1,
                ),
            )
        }

    @Test
    fun `getCollectorByIdFromDb should return collector when db returns non-null response`() =
        runTest {
            // Given
            val id = 1
            val collectorEntity = CollectorEntity(
                1,
                "Collector 1",
                "123456789",
                "collector1@email.com",
            )

            val collectorWithAlbumsAndPerformers = CollectorWithAlbumsAndPerformers(
                collectorEntity,
                emptyList(),
                emptyList(),
            )

            whenever(mockDBService.getCollectorByIdDao(id)).thenReturn(
                collectorWithAlbumsAndPerformers,
            )

            // When
            val collector =
                Collector(
                    1,
                    "Collector 1",
                    "123456789",
                    "collector1@email.com",
                )
            val result = repository.getCollectorByIdFromDb(id)

            // Then
            assertEquals(collector, result)
        }

    @Test
    fun `insertCollectors should insert a collector when db returns non-null response`() = runTest {
        // Given
        val collector = Collector(
            1,
            "Collector 1",
            "123456789",
            "collector1@email.com",
        )
        whenever(mockDBService.insertCollectorDao(collector.toDataBase())).thenReturn(Unit)

        // When
        val result = repository.insertCollectors(listOf(collector))

        // Then
        assertEquals(Unit, result)
        verify(mockDBService).insertCollectorsDao(listOf(collector.toDataBase()))
    }

    @Test
    fun `insertCollector should insert a collector when db returns non-null response`() = runTest {
        // Given
        val collector = Collector(
            1,
            "Collector 1",
            "123456789",
            "collector1@email.com",
        )
        whenever(mockDBService.insertCollectorDao(collector.toDataBase())).thenReturn(Unit)

        // When
        val result = repository.insertCollector(collector)

        // Then
        assertEquals(Unit, result)
        verify(mockDBService).insertCollectorDao(collector.toDataBase())
    }

    @Test
    fun `clearCollectors should clear all collectors when db returns non-null response`() =
        runTest {
            // Given
            whenever(mockDBService.deleteAllCollectorDao()).thenReturn(Unit)

            // When
            val result = repository.clearCollectors()

            // Then
            assertEquals(Unit, result)
            verify(mockDBService).deleteAllCollectorDao()
        }
}
