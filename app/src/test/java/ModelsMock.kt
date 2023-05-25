package com.example.misw4203moviles2023

import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.Track
import com.example.misw4203moviles2023.domain.collector.model.Collector
import com.example.misw4203moviles2023.domain.performer.model.Performer
import org.mockito.Mockito

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

fun mockPerformer(
	id: Int,
	name: String,
	description: String,
	image: String,
): Performer {
	val performer = Mockito.mock(Performer::class.java)
	Mockito.`when`(performer.id).thenReturn(id)
	Mockito.`when`(performer.name).thenReturn(name)
	Mockito.`when`(performer.description).thenReturn(description)
	Mockito.`when`(performer.description).thenReturn(image)
	Mockito.`when`(performer.albums).thenReturn(emptyList())
	return performer
}

fun mockCollector(id: Int, name: String, telephone: String, email: String): Collector {
	val collector = Mockito.mock(Collector::class.java)
	Mockito.`when`(collector.id).thenReturn(id)
	Mockito.`when`(collector.name).thenReturn(name)
	Mockito.`when`(collector.telephone).thenReturn(telephone)
	Mockito.`when`(collector.email).thenReturn(email)
	return collector
}
