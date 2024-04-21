package com.example.routes.material

import com.example.repository.MaterialRepository
import io.ktor.server.routing.*

fun Route.materialRouting(
    materialRepository: MaterialRepository
) {
    accessFiles()
    getFiles(
        materialRepository = materialRepository
    )
    uploadFile(
        materialRepository = materialRepository
    )
    uploadFolder(
        materialRepository = materialRepository
    )
    deleteFile(
        materialRepository = materialRepository
    )
    downloadFile()
}