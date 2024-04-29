package com.example.data.requests

import com.example.data.models.material.MaterialFolder
import kotlinx.serialization.Serializable

sealed class MaterialRequest {
    @Serializable
    data class GetMaterialInPath(val path: String = "") : MaterialRequest()

    @Serializable
    data class CreateFolderRequest(
        val name: String = "",
        val path: String = "",
        val communityId: String,
    ): MaterialRequest(){
        fun toMaterialFolder() = MaterialFolder(
            name = name,
            path = path,
            communityId = communityId
        )
    }
    @Serializable
    data class CreateFileRequest(
        val name: String = "",
        val type: String = "",
        val content: ByteArray = byteArrayOf(),
        val path: String = "",
        val communityId: String,
    ): MaterialRequest()

    @Serializable
    data class DeleteFileRequest(
        val fileId: String = "",
        val path: String = ""
    ) : MaterialRequest()

    @Serializable
    data class DownloadFileRequest(
        val url: String = "",
    ) : MaterialRequest()
    @Serializable
    data class RenameFolderRequest(val folderId: String, val newName: String) : MaterialRequest()

}