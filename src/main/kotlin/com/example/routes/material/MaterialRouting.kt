package com.example.routes.material

import com.example.repository.MaterialRepository
import io.ktor.server.routing.*

/**
 * This function sets up the routing for the material part of the application.
 * It includes routes for accessing files, getting files, uploading a file, uploading a folder,
 * deleting a file, and downloading a file.
 *
 * @param materialRepository The repository used for accessing the material data.
 */
fun Route.materialRouting(
    materialRepository: MaterialRepository
) {
    /**
     * Route for accessing files.
     */
    accessFiles()
    renameFolder(
        materialRepository = materialRepository
    )
    /**
     * Route for getting files.
     */
    getFiles(
        materialRepository = materialRepository
    )
    /**
     * Route for uploading a file.
     */
    uploadFile(
        materialRepository = materialRepository
    )
    /**
     * Route for uploading a folder.
     */
    uploadFolder(
        materialRepository = materialRepository
    )
    /**
     * Route for deleting a file.
     */
    deleteFile(
        materialRepository = materialRepository
    )
    /**
     * Route for downloading a file.
     */
    downloadFile()
}