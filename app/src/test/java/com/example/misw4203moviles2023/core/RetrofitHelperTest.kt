package com.example.misw4203moviles2023.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelperTest {

    // Declare a mock Retrofit object
    @Mock
    lateinit var retrofit: Retrofit

    @Before
    fun setup() {
        // Initialize the mock object
        MockitoAnnotations.openMocks(this)

        // Define the behavior of the mock object
        val url =
            okhttp3.HttpUrl.Builder().scheme("https").host("back-vynils.herokuapp.com").build()
        `when`(retrofit.baseUrl()).thenReturn(url)
        `when`(retrofit.converterFactories()).thenReturn(listOf(GsonConverterFactory.create()))
    }

    @Test
    fun testGetRetrofit() {
        // Call the method under test
        val result = RetrofitHelper.getRetrofit()

        // Assert that the result is not null
        assertEquals(retrofit.baseUrl().toString(), result.baseUrl().toString())
        assertTrue(result.converterFactories().any { it is GsonConverterFactory })
    }
}
