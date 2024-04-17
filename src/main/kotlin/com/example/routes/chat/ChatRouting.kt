package com.example.routes.chat

import com.example.repository.MessageDataSource
import com.example.room.RoomController
import io.ktor.server.routing.*

fun Route.chatRouting(
    roomController: RoomController,
    messageDataSource: MessageDataSource
) {
    updateRoomName(
        messageDataSource = messageDataSource
    )
    updateRoomAvatar(
        messageDataSource = messageDataSource
    )
    addMembers(
        messageDataSource = messageDataSource
    )
    removeMember(
        messageDataSource = messageDataSource
    )
    getRoomDetails(
        messageDataSource = messageDataSource
    )
    createGroupRoom(
        messageDataSource = messageDataSource
    )
    webSocketRoute(
        roomController = roomController,
    )
    getAllRecentRooms(
        messageDataSource = messageDataSource
    )
    fetchChatMessages(
        messageDataSource = messageDataSource
    )
    checkRoomExist(
        messageDataSource = messageDataSource
    )
}