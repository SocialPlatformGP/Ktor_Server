package com.example.data.requests

import com.example.data.models.material.MaterialFolder
import kotlinx.serialization.Serializable

sealed class MaterialRequest {
    @Serializable
    data class GetMaterialInPath(val path: String = "") : MaterialRequest()

    @Serializable
    data class CreateFolderRequest(val name: String = "", val path: String = "") : MaterialRequest() {
        fun toMaterialFolder() = MaterialFolder(
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