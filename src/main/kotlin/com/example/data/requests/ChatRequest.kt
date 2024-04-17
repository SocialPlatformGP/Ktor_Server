package com.example.data.requests

import com.example.data.models.chat.Message
import com.example.data.models.material.MessageAttachment
import com.example.data.models.chat.Room
import com.example.data.responses.ChatResponse
import com.example.utils.Constants
import com.example.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.File
import java.net.URLConnection
import java.util.*
import javax.activation.MimeType

sealed class ChatRequest {

    @Serializable
    data class FetchMessages(val roomId: String) : ChatRequest()

    @Serializable
    data class SendMessage(
        val content: String = "",
        val roomId: String = "",
        val senderId: String = "",
        val hasAttachment: Boolean = false,
        val attachment: MessageAttachment = MessageAttachment()
    ) : ChatRequest() {
        val message = Message(
            content = content,
            roomId = roomId,
            senderId = senderId,
            hasAttachment = hasAttachment,
            attachment = attachment
        )

    }



    @Serializable
    data class UpdateMessage(
        val messageId: String,
        val roomId: String,
        val updatedContent: String
    )

    @Serializable
    data class DeleteMessage(
        val messageId: String,
        val roomId: String
    )

    @Serializable
    data class CreateRoomRequest(
        val room: Room
    ) : ChatRequest()

    @Serializable
    data class GetRoomRequest(
        val roomId: String,
    ) : ChatRequest()

    @Serializable
    data class RoomExistRequest(
        val senderId: String,
        val receiverId: String,
    ) : ChatRequest()

    @Serializable
    data class GetMessagesFromRoom(
        val roomId: String
    ) : ChatRequest()

    @Serializable
    data class GetAllRecentRooms(
        val userId: String
    ) : ChatRequest()

    @Serializable
    data class CreateGroupRoom(
        val groupName: String,
        val groupAvatar: ByteArray,
        val userIds: List<String>,
        val creatorId: String
    ) : ChatRequest()

    @Serializable
    data class GetRoomDetails(
        val roomId: String
    ) : ChatRequest()

    @Serializable
    data class UpdateRoomAvatar(
        val roomId: String,
        val byteArray: ByteArray
    ) : ChatRequest()

    @Serializable
    data class UpdateRoomName(
        val roomId: String,
        val name: String
    ) : ChatRequest()

    @Serializable
    data class AddMembers(
        val roomId: String,
        val userIds: List<String>
    ) : ChatRequest()

    @Serializable
    data class RemoveMember(
        val roomId: String,
        val userId: String
    ) : ChatRequest()
}

fun ChatRequest.SendMessage.toMessage() : Message {
    val url = createFileUrl()
    return message.copy(attachment = attachment.copy(url = url, byteArray = byteArrayOf(), type = getFileExtension()))
}

private fun ChatRequest.SendMessage.createFileUrl(): String {
    val folder = File("files/rooms/${message.roomId}/${message.id}")
    val url = if (message.hasAttachment) {
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val extension = getFileExtension()
        FileUtils.saveByteArrayToFile(
            message.attachment.byteArray,
            "files/rooms/${message.roomId}/${message.id}" + "." + extension
        )
        "${Constants.BASE_URL}/rooms/${message.roomId}/${message.id}" + "." + extension

    } else ""
    return url
}

private fun ChatRequest.SendMessage.getFileExtension(): String {
    val mimeType = URLConnection.guessContentTypeFromStream(message.attachment.byteArray.inputStream())
    val extension = MimeType(mimeType).subType
    return extension
}

fun ChatRequest.SendMessage.toResponse(senderName: String, senderPicUrl: String) {
    val url = createFileUrl()
    ChatResponse.MessageResponse(
        content = content,
        roomId = roomId,
        createdAt = message.createdAt,
        senderName = senderName,
        senderPfpURL = senderPicUrl,
        id = message.id,
        senderId = senderId,
        hasAttachment = hasAttachment,
        attachment = attachment.copy(url = url, byteArray = byteArrayOf(), type = getFileExtension()),
    )
}