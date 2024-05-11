package com.example.repository.material

import com.example.data.models.material.MaterialFile
import com.example.data.models.material.MaterialFolder
import com.example.data.requests.MaterialRequest
import com.example.data.responses.MaterialResponse
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MaterialRepositoryImpl(
    private val db : CoroutineDatabase

) : MaterialRepository {
    private val materialFolders = db.getCollection<MaterialFolder>()
    private val materialFiles = db.getCollection<MaterialFile>()
    override suspend fun createMaterialFolder(
        materialFolder: MaterialFolder
    ): MaterialResponse.GetMaterialResponses {
       val success =  materialFolders.insertOne(materialFolder).wasAcknowledged()
        return if(success){
            val files = materialFiles.find(MaterialFile::path eq materialFolder.path).toList()
            val folders = materialFolders.find(MaterialFolder::path eq materialFolder.path).toList()

            MaterialResponse.GetMaterialResponses(
                files.map { it.toResponse() },
                folders.map { it.toResponse() }
            )
        }else{
            MaterialResponse.GetMaterialResponses()
        }
    }

    override suspend fun createMaterialFile(materialFile: MaterialFile): MaterialResponse {
        val success =  materialFiles.insertOne(materialFile).wasAcknowledged()
        return if(success){
            val files = materialFiles.find(MaterialFile::path eq materialFile.path).toList()
            val folders = materialFolders.find(MaterialFolder::path eq materialFile.path).toList()
            MaterialResponse.GetMaterialResponses(
                files.map { it.toResponse() },
                folders.map { it.toResponse() }
            )
        }else{
            MaterialResponse.GetMaterialResponses()
        }
    }

    override suspend fun getMaterialResponse(path: String): MaterialResponse {

        val materialFile = materialFiles.find(MaterialFile::path eq path).toList()
        val materialFolder = materialFolders.find(MaterialFolder::path eq path).toList()

        return MaterialResponse.GetMaterialResponses(
            materialFile.map { it.toResponse() },
            materialFolder.map { it.toResponse() }
        )
    }

    override suspend fun renameFolder(request: MaterialRequest.RenameFolderRequest): MaterialResponse {
        val materialFolder = materialFolders.findOne(MaterialFolder::id eq request.folderId)
        val success = materialFolders.updateOne(MaterialFolder::id eq request.folderId, materialFolder!!.copy(name = request.newName)).wasAcknowledged()
        return if(success){
            val files = materialFiles.find(MaterialFile::path eq materialFolder.path).toList()
            val folders = materialFolders.find(MaterialFolder::path eq materialFolder.path).toList()

            MaterialResponse.GetMaterialResponses(
                files.map { it.toResponse() },
                folders.map { it.toResponse() }
            )
        }else{
            MaterialResponse.GetMaterialResponses()
        }
    }

    override suspend fun deleteFile(id: String, path: String): MaterialResponse {
        materialFiles.deleteOne(MaterialFile::id eq id).wasAcknowledged()
        val materialFile = materialFiles.find(MaterialFile::path eq path).toList()
        val materialFolder = materialFolders.find(MaterialFolder::path eq path).toList()

        return MaterialResponse.GetMaterialResponses(
            materialFile.map { it.toResponse() },
            materialFolder.map { it.toResponse() }
        )
    }
}