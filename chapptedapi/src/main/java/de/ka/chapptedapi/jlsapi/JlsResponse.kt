package de.ka.chapptedapi.jlsapi

import retrofit2.Response

class JlsResponse<T>(response: Response<T>) {

    val body = response.body()

    val code = response.code()
}