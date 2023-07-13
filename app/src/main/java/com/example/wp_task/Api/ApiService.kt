package com.example.wp_task.Api

import com.example.wp_task.model.Data
import retrofit2.http.GET
import retrofit2.http.Header
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Query

/**
Makes a network call to retrieve random movie data from the most_pop_movies list.

@param listParam the name of the list to retrieve random data from.
@param apiKey the API key used for authentication.
@param apiHost the host name for the API endpoint.
@return an Observable emitting the random data as a Data object.
 */
interface ApiService {
    @GET("titles/random")
    fun getRandom(
        @Query("list") listParam: String,
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String
    ): Observable<Data>
}
