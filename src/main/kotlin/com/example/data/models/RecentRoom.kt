package com.example.data.models

import javax.sound.midi.Receiver
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private
@kotlinx.serialization.Serializable
data class RecentRoom(
    val roomId: String,
    val lastMessage: String="",
    val isPrivate: Boolean = true,
    val sender_id: String = "", //if the room is a group chat, this will be the group chat image
    val receiver_id: String = "", //if the room is a group chat, this will be the group chat name
    val pic_url: String = "", //if the room is a group chat, this will be the group chat image
    val title :String = "", //if the room is a group chat, this will be the group chat name
    val lastMessageTime: Long = 0,
    ){
    fun toResponse(senderName:String,title: String,pic_url: String) = RecentRoomResponse(
        roomId = roomId,
        lastMessage = lastMessage,
        isPrivate = isPrivate,
        senderName = senderName,
        pic_url = pic_url,
        title = title,
        lastMessageTime = lastMessageTime
    )
}
