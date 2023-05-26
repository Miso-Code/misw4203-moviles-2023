package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.AlbumModelCreate
import com.example.misw4203moviles2023.data.model.AlbumModelNoTracks
import com.example.misw4203moviles2023.data.model.TrackModel
import com.example.misw4203moviles2023.data.model.TrackModelCreate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumApiClient {
    @GET("/albums")
    suspend fun getAllAlbums(): Response<List<AlbumModel>>

    @GET("/albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Response<AlbumModel>

    @POST("/albums")
    suspend fun createAlbum(@Body album: AlbumModelCreate): Response<AlbumModelNoTracks>

    @DELETE("/albums/{id}")
    suspend fun deleteAlbumById(@Path("id") id: Int): Response<Unit>

    @POST("/albums/{id}/tracks")
    suspend fun addTrackToAlbum(
        @Path("id") albumId: Int,
        @Body track: TrackModelCreate,
    ): Response<TrackModel>

    @DELETE("/albums/{id}/tracks/{trackId}")
    suspend fun deleteTrackFromAlbum(
        @Path("id") albumId: Int,
        @Path("trackId") trackId: Int,
    ): Response<Unit>
}
