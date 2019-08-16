package com.shakespace.firstlinecode.chapter08network.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("repos/{name}/{repo}/contributors")
    fun getContributors(@Path("name") name: String, @Path("repo") repo: String): Deferred<List<Contributor>>
}