package com.example.repository

import com.example.data.models.Message
import com.example.data.models.Room
import com.example.data.models.UserRooms
import com.example.data.requests.ChatRequest
import com.example.data.responses.MessageResponse

interface MessageDataSource {
    suspend fun checkRoomExists(request: ChatRequest.CheckRoomExists): Room

    suspend fun getRoom(roomId: String): Room

    suspend fun addMessageToRoom(message: Message)

    suspend fun getMessagesFromRoom(roomId: String): List<MessageResponse>


}