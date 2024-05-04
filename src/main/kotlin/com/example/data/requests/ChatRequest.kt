package com.example.data.requests

import com.example.data.models.chat.Message
import com.example.data.models.material.MessageAttachment
import com.example.data.models.chat.Room
import com.example.data.responses.ChatResponse
import com.example.utils.Constants
import com.example.utils.FileUtils
import kotlinx.serialization.Serializable
import java.io.File
import java.net.URLConnection
import java.util.*

sealed class ChatRequest {

    @Serializable
    data class FetchMessages(val roomId: String) : ChatRequest()

    @Serializable
    data class ReportMessage(
        val messageId: String,
        val roomId: String,
        val reporterId: String
    ) : ChatRequest()
    @Serializable
    data class SendMessage(
        val content: String = "",
        val roomId: String = "",
        val senderId: String = "",
        val hasAttachment: Boolean = false,
        val attachment: MessageAttachment = MessageAttachment()
    ) : ChatRequest() {
        fun toMessage(): Message {
            return Message(
                content = content,
                roomId = roomId,
                senderId = senderId,
                hasAttachment = hasAttachment,
                attachment = attachment.copy(
                    url = if (hasAttachment) createFileUrl(
                        roomId,
                        hasAttachment,
                        attachment
                    ) else "",
                    byteArray = byteArrayOf(),
                    type = if (hasAttachment) attachment.type else ""
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
                    url = if (hasAttachment) createFileUrl(
                        roomId,
                        hasAttachment,
                        attachment
                    ) else "",
                    byteArray = byteArrayOf(),
                    type = if (hasAttachment) attachment.type else ""

                )
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
        FileUtils.saveByteArrayToFile(
            attachment.byteArray,
            "files/rooms/${roomId}/${m}" + "-" + attachment.name
        )
        "rooms/${roomId}/${m}" + "-" + attachment.name

    } else ""
    return url
}

fun getFileExtension(
    name: String
): String {
    val mimeType: String = URLConnection.guessContentTypeFromName(name) ?: "*/*"
    println("\n\n\n\n\n MimeType: $mimeType\n\n\n\n\n\n")
    return mimeType
}

fun getMimeTypeFromFileName(fileName: String): String {
    val extension = fileName.substringAfterLast('.', "")
    return when (extension.lowercase()) {
        "pdf" -> "application/pdf"
        "txt" -> "text/plain"
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "gif" -> "image/gif"
        "bmp" -> "image/bmp"
        "mp3" -> "audio/mpeg"
        "wav" -> "audio/wav"
        "ogg" -> "audio/ogg"
        "mp4" -> "video/mp4"
        "avi" -> "video/x-msvideo"
        "mov" -> "video/quicktime"
        "wmv" -> "video/x-ms-wmv"
        "html", "htm" -> "text/html"
        "css" -> "text/css"
        "js" -> "application/javascript"
        "json" -> "application/json"
        "xml" -> "application/xml"
        "zip" -> "application/zip"
        "rar" -> "application/x-rar-compressed"
        "tar" -> "application/x-tar"
        "7z" -> "application/x-7z-compressed"
        "doc", "docx" -> "application/msword"
        "xls", "xlsx" -> "application/vnd.ms-excel"
        "ppt", "pptx" -> "application/vnd.ms-powerpoint"
        "csv" -> "text/csv"
        "rtf" -> "application/rtf"
        "flac" -> "audio/flac"
        "xlsb" -> "application/vnd.ms-excel.sheet.binary.macroEnabled.12"
        "xlsm" -> "application/vnd.ms-excel.sheet.macroEnabled.12"
        "xltm" -> "application/vnd.ms-excel.template.macroEnabled.12"
        "xltx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.template"
        "xlw" -> "application/vnd.ms-excel"
        "potx" -> "application/vnd.openxmlformats-officedocument.presentationml.template"
        "ppam" -> "application/vnd.ms-powerpoint.addin.macroEnabled.12"
        "pptm" -> "application/vnd.ms-powerpoint.presentation.macroEnabled.12"
        "sldm" -> "application/vnd.ms-powerpoint.slide.macroEnabled.12"
        "ppsm" -> "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"
        "potm" -> "application/vnd.ms-powerpoint.template.macroEnabled.12"
        "thmx" -> "application/vnd.ms-officetheme"
        "ai" -> "application/postscript"
        "eps" -> "application/postscript"
        "psd" -> "image/vnd.adobe.photoshop"
        "tif", "tiff" -> "image/tiff"
        "svg" -> "image/svg+xml"
        "mpg", "mpeg" -> "video/mpeg"
        "woff" -> "font/woff"
        "woff2" -> "font/woff2"
        "ttf" -> "font/ttf"
        "otf" -> "font/otf"
        "webm" -> "video/webm"
        "apk" -> "application/vnd.android.package-archive"
        "exe" -> "application/octet-stream"
        // Add more cases for other file types if needed
        else -> "application/octet-stream" // Default MIME type for unknown file types
    }
}