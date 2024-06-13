package com.example.repository.material

import com.example.data.models.material.MaterialFile
import com.example.data.models.material.MaterialFolder
import com.example.data.requests.MaterialRequest
import com.example.data.responses.MaterialResponse
import com.mongodb.client.model.Filters.and
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
            val files = materialFiles.find(and(MaterialFile::path eq materialFolder.path, MaterialFile::communityId eq materialFolder.communityId)).toList()
            val folders = materialFolders.find(and(MaterialFolder::path eq materialFolder.path,MaterialFolder::communityId eq materialFolder.communityId)).toList()

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
            val files = materialFiles.find(and(MaterialFile::path eq materialFile.path, MaterialFile::communityId eq materialFile.communityId)).toList()
            val folders = materialFolders.find(and(MaterialFolder::path eq materialFile.path, MaterialFolder::communityId eq materialFile.communityId)).toList()
            MaterialResponse.GetMaterialResponses(
                files.map { it.toResponse() },
                folders.map { it.toResponse() }
            )
        }else{
            MaterialResponse.GetMaterialResponses()
        }
    }

    override suspend fun getMaterialResponse(communityId: String, path: String): MaterialResponse {
        val materialFile = materialFiles.find(and(MaterialFile::path eq path, MaterialFile::communityId eq communityId)).toList()
        val materialFolder = materialFolders.find(and(MaterialFolder::path eq path), MaterialFile::communityId eq communityId).toList()

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