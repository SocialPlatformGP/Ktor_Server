package com.example.data.models

import javax.sound.midi.Receiver
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private
@kotlinx.serialization.Serializable
data class RecentRoom(
    val roomId: String,
    val lastMessage: String,
    val isPrivate: Boolean,
    val sender_id: String,
    val receiver_id: String,
    val pic_url: String, //if the room is a group chat, this will be the group chat image
    val title :String, //if the room is a group chat, this will be the group chat name
    val lastMessageTime: Long,
    ){
    fun toResponse(senderName: String, receiverName: String, senderPicUrl: String, receiverPicUrl: String) = RecentRoomResponse(
        roomId = roomId,
        lastMessage = lastMessage,
        isPrivate = isPrivate,
        senderName = senderName,
        receiverName = receiverName,
        senderPicUrl = senderPicUrl,
        receiverPicUrl = receiverPicUrl,
        pic_url = pic_url,
        title = title,
        lastMessageTime = lastMessageTime
    )
}

data class RecentRoomResponse(
    val roomId: String,
    val lastMessage: String,
    val isPrivate: Boolean,
    val senderName: String,
    val receiverName: String,
    val senderPicUrl: String,
    val receiverPicUrl: String,
    val pic_url: String, //if the room is a group chat, this will be the group chat image
    val title :String, //if the room is a group chat, this will be the group chat name
    val lastMessageTime: Long,
)