package com.example.data.requests

import com.example.data.models.material.MaterialFile
import com.example.data.models.material.MaterialFolder
import kotlinx.serialization.Serializable


@Serializable
data class CreateFolderRequest (
    val name: String = "",
    val path: String = "",
){
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
){
    fun toMaterialFile() {
//        val folderId = UUID.randomUUID().toString()
//        val folder = File("files/${postId}")
//        if (!folder.exists()) {
//            folder.mkdirs()
//        }
//        val attachments = request.attachments.map {
//            val file = FileUtils.saveByteArrayToFile(it.file, "files/${postId}/" + it.name)
//            println(file.path)
//            it.copy(
//                file = byteArrayOf(),
//                url = postId+"/"+it.name,
//                type = it.type,
//                name = it.name
//            )
//        }
        MaterialFile(
            name = name,
            type = type,
            url = "",
            path = path,
        )
    }

}