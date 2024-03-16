package com.leperlier.quinquis.lentz.imdb.utils

import retrofit2.Response
import java.io.IOException


internal fun <T : Any> Response<T>.parse(): Result<T> {
    return if (isSuccessful) {
        body()?.let {
            Result.Succes(it)
        } ?: run {
            Result.Error(
                exception = NoDataException(),
                message = "Aucune donnée",
                code = 404
            )
        }
    } else {
        Result.Error(
            exception = Exception(),
            message = message(),
            code = code()
        )
    }
}

class NoDataException: Exception()