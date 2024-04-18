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
        fun toMessage() : Message {
            return Message(
                content = content,
                roomId = roomId,
                senderId = senderId,
                hasAttachment = hasAttachment,
                attachment = attachment.copy(
                    url = if(hasAttachment) createFileUrl(
                        roomId,
                        hasAttachment,
                        attachment
                    ) else "",
                    byteArray = byteArrayOf(),
                    type = if(hasAttachment) getFileExtension(attachment.byteArray) else ""
                )
            )
        }
        fun toResponse(senderName: String, senderPicUrl: String) =
            ChatResponse.MessageResponse(
                content = content,
                roomId = roomId,
                senderName = senderName,
                createdAt = toMessage().createdAt,
                senderPfpURL = senderPicUrl,
                senderId = senderId,
                hasAttachment = hasAttachment,
                attachment = attachment.copy(
                    url = if(hasAttachment) createFileUrl(
                        roomId,
                        hasAttachment,
                        attachment
                    ) else "",
                    byteArray = byteArrayOf(),
                    type = if(hasAttachment) getFileExtension(attachment.byteArray) else ""
                )            )
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


fun createFileUrl(
    roomId: String,
    hasAttachment: Boolean,
    attachment: MessageAttachment
): String {
    val m = UUID.randomUUID().toString()
    val folder = File("files/rooms/${roomId}/${m}")
    val url = if (hasAttachment) {
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val extension = getFileExtension(attachment.byteArray)
        FileUtils.saveByteArrayToFile(
            attachment.byteArray,
            "files/rooms/${roomId}/${m}" + "." + extension
        )
        "${Constants.BASE_URL}/rooms/${roomId}/${m}" + "." + extension

    } else ""
    return url
}

fun getFileExtension(
     data : ByteArray
): String {
    val mimeType = URLConnection.guessContentTypeFromStream(data.inputStream())
    val extension = MimeType(mimeType).subType
    return extension
}

