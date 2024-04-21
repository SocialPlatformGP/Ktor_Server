package com.example.data.responses

import com.example.data.models.material.MaterialFile
import com.example.data.models.material.MaterialFolder
import kotlinx.serialization.Serializable

sealed class MaterialResponse {
    @Serializable
    data class GetMaterialResponses(
        val files: List<MaterialFileResponse> = emptyList(),
        val folders: List<MaterialFolderResponse> = emptyList()
    ) : MaterialResponse()
    @Serializable
    data class DownloadFileResponse(
        val data :ByteArray,
        val fileName :String
    ): MaterialResponse()
}