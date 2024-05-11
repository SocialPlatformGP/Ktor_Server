package com.example.repository.material

import com.example.data.models.material.MaterialFile
import com.example.data.models.material.MaterialFolder
import com.example.data.requests.MaterialRequest
import com.example.data.responses.MaterialResponse

interface MaterialRepository {
    suspend fun createMaterialFolder(
        MaterialFolder: MaterialFolder
    ):MaterialResponse

    suspend fun createMaterialFile(
        MaterialFile: MaterialFile
    ):MaterialResponse
    suspend fun getMaterialResponse(
        path: String
    ):MaterialResponse

    suspend fun deleteFile(id: String, path: String): MaterialResponse
    suspend fun renameFolder(request: MaterialRequest.RenameFolderRequest): MaterialResponse
}