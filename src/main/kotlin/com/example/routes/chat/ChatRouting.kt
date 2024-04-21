package com.example.routes.chat

import com.example.repository.MessageDataSource
import com.example.room.RoomController
import io.ktor.server.routing.*

/**
 * This function sets up the routing for the chat part of the application.
 * It includes routes for updating room name, updating room avatar, adding members, removing a member,
 * getting room details, creating a group room, setting up a web socket route, getting all recent rooms,
 * fetching chat messages, and checking if a room exists.
 *
 * @param roomController The controller used for handling room-related operations.
 * @param messageDataSource The data source used for accessing the message data.
 */
fun Route.chatRouting(
    roomController: RoomController,
    messageDataSource: MessageDataSource
) {
    /**
     * Route for updating the name of a room.
     */
    updateRoomName(
        messageDataSource = messageDataSource
    )
    /**
     * Route for updating the avatar of a room.
     */
    updateRoomAvatar(
        messageDataSource = messageDataSource
    )
    /**
     * Route for adding members to a room.
     */
    addMembers(
        messageDataSource = messageDataSource
    )
    /**
     * Route for removing a member from a room.
     */
    removeMember(
        messageDataSource = messageDataSource
    )
    /**
     * Route for getting the details of a room.
     */
    getRoomDetails(
        messageDataSource = messageDataSource
    )
    /**
     * Route for creating a group room.
     */
    createGroupRoom(
        messageDataSource = messageDataSource
    )
    /**
     * Route for setting up a web socket.
     */
    webSocketRoute(
        roomController = roomController,
    )
    /**
     * Route for getting all recent rooms.
     */
    getAllRecentRooms(
        messageDataSource = messageDataSource
    )
    /**
     * Route for fetching chat messages.
     */
    fetchChatMessages(
        messageDataSource = messageDataSource
    )
    /**
     * Route for checking if a room exists.
     */
    checkRoomExist(
        messageDataSource = messageDataSource
    )
}