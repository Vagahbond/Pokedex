package com.dedistonks.pokedex.data

import com.dedistonks.pokedex.models.ListAPIResource
import java.lang.Exception

sealed class ResourceMediatorResponse {
    data class Success(val data: ListAPIResource) : ResourceMediatorResponse()
    data class Error(val errorString: String, val exception: Exception) : ResourceMediatorResponse()

}