package com.example.misw4203moviles2023.data

import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.data.database.DataBaseService
import com.example.misw4203moviles2023.data.database.entities.PerformerAlbumCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerWithAlbums
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.data.network.PerformerService
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.performer.model.Performer
import com.example.misw4203moviles2023.domain.performer.model.toDomain
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
class PerformerRepositoryTest {
    private lateinit var repository: PerformerRepository

    @Mock
    private lateinit var mockService: PerformerService

    @Mock
    private lateinit var mockDBService: DataBaseService

    @Before
    @Test
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mockService = mock(PerformerService::class.java)
        mockDBService = mock(DataBaseService::class.java)

        repository = PerformerRepository(context, mockService, mockDBService)
    }

    @Test
    fun `getAllPerformers should return empty list when api returns null`() = runTest {
        // Given
        whenever(mockService.getPerformers()).thenReturn(emptyList())

        // When
        val result = repository.getPerformersFromApi()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllPerformers should return list when api returns non-null`() = runTest {
        // Given
        val performersModel = listOf(
            PerformerModel(
                1,
                "Performer 1",
                "123456789",
                "performer1.jpg",
                emptyList(),
            ),
            PerformerModel(
                2,
                "Performer 2",
                "123456789",
                "performer2.jpg",
                emptyList(),
            ),
        )

        whenever(mockService.getPerformers()).thenReturn(performersModel)

        // When
        val result = repository.getPerformersFromApi()

        // Then
        assertEquals(performersModel.map { it.toDomain() }, result)
    }

    @Test
    fun `getAllPerformersFromDB should return empty list when db returns null`() = runTest {
        // Given
        whenever(mockDBService.getAllPerformerDao()).thenReturn(emptyList())

        // When
        val result = repository.getPerformersFromDB()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllPerformersFromDB should return list of performers when api returns non-null response`() =
        runTest {
            // Given
            val performersModel = listOf(
                PerformerWithAlbums(
                    PerformerEntity(
                        1,
                        "Performer 1",
                        "123456789",
                        "performer1@email.com",
                    ),
                    emptyList(),
                ),
                PerformerWithAlbums(
                    PerformerEntity(
                        2,
                        "Performer 2",
                        "987654321",
                        "performer2@email.com",
                    ),
                    emptyList(),
                ),
            )
            whenever(mockDBService.getAllPerformerDao()).thenReturn(performersModel)

            val performers = listOf(
                Performer(
                    1,
                    "Performer 1",
                    "123456789",
                    "performer1@email.com",
                    emptyList(),
                ),
                Performer(
                    2,
                    "Performer 2",
                    "987654321",
                    "performer2@email.com",
                    emptyList(),
                ),
            )

            // When
            val result = repository.getPerformersFromDB()

            // Then
            assertEquals(performers, result)
        }

    @Test
    fun `getPerformerById should return default performer when api returns null`() = runTest {
        // Given
        val id = 1
        val defaultPerformerModel = PerformerModel(
            1,
            "Performer 1",
            "123456789",
            "performer1@email.com",
            emptyList(),
        )
        whenever(mockService.getPerformerById(id)).thenReturn(defaultPerformerModel)

        // When
        val result = repository.getPerformerByIdFromApi(id)
        val defaultPerformer = Performer(
            1,
            "Performer 1",
            "123456789",
            "performer1@email.com",
            emptyList(),
        )
        // Then
        assertEquals(defaultPerformer, result)
    }

    @Test
    fun `getPerformerById should return performer when api returns non-null response`() = runTest {
        // Given
        val id = 1
        val performerModel = PerformerModel(
            1,
            "Performer 1",
            "123456789",
            "performer1@email.com",
            emptyList(),
        )
        whenever(mockService.getPerformerById(id)).thenReturn(performerModel)
        // When
        val performer = Performer(
            1,
            "Performer 1",
            "123456789",
            "performer1@email.com",
            emptyList(),
        )
        val result = repository.getPerformerByIdFromApi(id)

        // Then
        assertEquals(performer, result)
    }

    @Test
    fun `getPerformerById should return null when api returns null`() = runTest {
        // Given
        val id = 1
        whenever(mockService.getPerformerById(id)).thenReturn(null)

        // When
        val result = repository.getPerformerByIdFromApi(id)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `getPerformerByIdFromDb should return performer when db returns non-null response`() =
        runTest {
            // Given
            val id = 1
            val performerEntity = PerformerWithAlbums(
                PerformerEntity(
                    1,
                    "Performer 1",
                    "123456789",
                    "performer1@email.com",
                ),
                emptyList(),
            )

            whenever(mockDBService.getPerformerByIdDao(id)).thenReturn(
                performerEntity,
            )

            // When
            val performer = Performer(
                1,
                "Performer 1",
                "123456789",
                "performer1@email.com",
                emptyList(),
            )
            val result = repository.getPerformerByIdFromDB(id)

            // Then
            assertEquals(performer, result)
        }

    @Test
    fun `insertPerformers should insert a performer when db returns non-null response`() = runTest {
        // Given
        val performer = PerformerEntity(
            1,
            "Performer 1",
            "123456789",
            "performer1@email.com",
        )
        whenever(mockDBService.insertPerformerDao(listOf(performer))).thenReturn(Unit)

        // When
        val result = repository.insertAllPerformer(listOf(performer.toDomain()))

        // Then
        assertEquals(Unit, result)
        verify(mockDBService).insertPerformerDao(listOf(performer))
    }

    @Test
    fun `insertPerformers should insert a performer and albums when db returns non-null response`() =
        runTest {
            // Given
            val performer = Performer(
                1,
                "Performer 1",
                "123456789",
                "performer1.jpg",
                listOf(
                    Album(
                        1,
                        "Album 1",
                        "album1.jpg",
                        "2021-01-01",
                        "description",
                        "Genre",
                        "Label",
                        emptyList(),
                    ),
                ),
            )
            whenever(
                mockDBService.insertAlbumsDao(performer.albums.map { it.toDatabase() }),
            ).thenReturn(
                Unit,
            )
            whenever(
                mockDBService.insertPerformerWithAlbumDao(
                    PerformerAlbumCrossRefEntity(1, 1),
                ),
            ).thenReturn(
                Unit,
            )

            // When
            val result = repository.insertAllPerformer(listOf(performer))

            // Then
            assertEquals(Unit, result)
            verify(mockDBService).insertAlbumsDao(performer.albums.map { it.toDatabase() })
            verify(mockDBService).insertPerformerWithAlbumDao(
                PerformerAlbumCrossRefEntity(
                    1,
                    1,
                ),
            )
        }

    @Test
    fun `clearPerformers should clear all performers when db returns non-null response`() =
        runTest {
            // Given
            whenever(mockDBService.deleteAllPerformerDao()).thenReturn(Unit)

            // When
            val result = repository.clearAllPerformer()

            // Then
            assertEquals(Unit, result)
            verify(mockDBService).deleteAllPerformerDao()
        }

    @Test
    fun `clearAllPerformerWithAlbum should clear all performers when db returns non-null response`() =
        runTest {
            // Given
            whenever(mockDBService.deleteAllPerformerDao()).thenReturn(Unit)
            whenever(mockDBService.deleteAlbumsDao()).thenReturn(Unit)

            // When
            val result = repository.clearAllPerformerWithAlbum()

            // Then
            assertEquals(Unit, result)
            verify(mockDBService).deleteAllPerformerDao()
            verify(mockDBService).deleteAlbumsDao()
        }
}
