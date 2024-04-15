package com.example.data.requests

import kotlinx.serialization.Serializable

sealed class MaterialRequest {
    @Serializable
    data class GetMaterialInPath(val path: String = "") : MaterialRequest()

    @Serializable
    data class CreateFolderRequest(val name: String = "", val path: String = "") : MaterialRequest() {
        fun toMaterialFolder() = com.example.data.models.MaterialFolder(
            name = name,
            path = path,
        )
    }

    @Serializable
    data class CreateFileRequest(
        val name: String = "",
        val type: String = "",
        val content: ByteArray = byteArrayOf(),
        val path: String = "",
    ) : MaterialRequest()
}