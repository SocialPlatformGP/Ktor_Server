package com.example.data.requests

import com.example.data.models.chat.Message
import com.example.data.models.material.MessageAttachment
import com.example.data.models.chat.Room
import com.example.data.responses.ChatResponse
import com.example.utils.Constants
import com.example.utils.FileUtils
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.File

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

        @Contextual
        val folder = File("files/rooms/${roomId}/${message.id}")
        val url = if (hasAttachment && attachment.byteArray.isNotEmpty()) {
            if (!folder.exists()) {
                folder.mkdirs()
            }
            FileUtils.saveByteArrayToFile(attachment.byteArray, "files/rooms/${roomId}/" + message.id)
            "${Constants.BASE_URL}/rooms/${roomId}/${message.id}"

        } else ""

        fun toResponse(senderName: String, senderPicUrl: String) = ChatResponse.MessageResponse(
            content = content,
            roomId = roomId,
            createdAt = message.createdAt,
            senderName = senderName,
            senderPfpURL = senderPicUrl,
            id = message.id,
            senderId = senderId,
            hasAttachment = hasAttachment,
            attachment = attachment.copy(url = url, byteArray = byteArrayOf())
        )

        fun toMessage() = message.copy(attachment = attachment.copy(url = url, byteArray = byteArrayOf()))
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

