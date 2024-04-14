package com.example.repository

import com.example.data.models.Message
import com.example.data.models.Room
import com.example.data.requests.ChatRequest
import com.example.data.responses.AuthResponse
import com.example.data.responses.ChatResponse

interface MessageDataSource {
    suspend fun checkRoomExists(request: ChatRequest.RoomExistRequest): Room?

    suspend fun createGroupRoom(request: ChatRequest.CreateGroupRoom): String

    suspend fun getRoom(roomId: String): Room

    suspend fun addMessageToRoom(message: Message)

    suspend fun getMessagesFromRoom(roomId: String): List<ChatResponse.MessageResponse>

    suspend fun getRecentRooms(request: ChatRequest.GetAllRecentRooms): ChatResponse.GetAllRecentRooms


}