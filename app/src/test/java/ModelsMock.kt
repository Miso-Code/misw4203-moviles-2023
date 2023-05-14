package com.example.misw4203moviles2023

import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.TrackModel
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.Track
import org.mockito.Mockito

@Suppress("LongParameterList")
fun mockAlbumModel(
    id: Int,
    name: String,
    cover: String,
    releaseDate: String,
    description: String,
    genre: String,
    recordLabel: String,
): AlbumModel {
    val album = Mockito.mock(AlbumModel::class.java)
    Mockito.`when`(album.id).thenReturn(id)
    Mockito.`when`(album.name).thenReturn(name)
    Mockito.`when`(album.cover).thenReturn(cover)
    Mockito.`when`(album.releaseDate).thenReturn(releaseDate)
    Mockito.`when`(album.description).thenReturn(description)
    Mockito.`when`(album.genre).thenReturn(genre)
    Mockito.`when`(album.recordLabel).thenReturn(recordLabel)
    Mockito.`when`(album.tracks).thenReturn(emptyList())
    return album
}

@Suppress("LongParameterList")
fun mockAlbum(
    id: Int,
    name: String,
    cover: String,
    releaseDate: String,
    description: String,
    genre: String,
    recordLabel: String,
): Album {
    val album = Mockito.mock(Album::class.java)
    Mockito.`when`(album.id).thenReturn(id)
    Mockito.`when`(album.name).thenReturn(name)
    Mockito.`when`(album.cover).thenReturn(cover)
    Mockito.`when`(album.releaseDate).thenReturn(releaseDate)
    Mockito.`when`(album.description).thenReturn(description)
    Mockito.`when`(album.genre).thenReturn(genre)
    Mockito.`when`(album.recordLabel).thenReturn(recordLabel)
    Mockito.`when`(album.tracks).thenReturn(emptyList())
    return album
}

fun mockTrackModel(
    id: Int,
    name: String,
    duration: String,
): TrackModel {
    val track = Mockito.mock(TrackModel::class.java)
    Mockito.`when`(track.id).thenReturn(id)
    Mockito.`when`(track.name).thenReturn(name)
    Mockito.`when`(track.duration).thenReturn(duration)
    return track
}

fun mockTrack(
    id: Int,
    name: String,
    duration: String,
): Track {
    val track = Mockito.mock(Track::class.java)
    Mockito.`when`(track.id).thenReturn(id)
    Mockito.`when`(track.name).thenReturn(name)
    Mockito.`when`(track.duration).thenReturn(duration)
    return track
}
