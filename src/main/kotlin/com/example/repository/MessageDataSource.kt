package com.example.repository

import com.example.data.models.chat.Message
import com.example.data.models.chat.Room
import com.example.data.requests.ChatRequest
import com.example.data.requests.PostRequest
import com.example.data.responses.ChatResponse

interface MessageDataSource {
    suspend fun checkRoomExists(request: ChatRequest.RoomExistRequest): Room?
    suspend fun reportMessage(request: ChatRequest.ReportMessage): Boolean
    suspend fun createGroupRoom(request: ChatRequest.CreateGroupRoom): Room

    suspend fun getRoom(roomId: String): Room

    suspend fun addMessageToRoom(message: Message)

    suspend fun getMessagesFromRoom(roomId: String): List<ChatResponse.MessageResponse>

    suspend fun getRecentRooms(request: ChatRequest.GetAllRecentRooms): ChatResponse.GetAllRecentRooms
  suspend  fun updateRoomAvatar(request: ChatRequest.UpdateRoomAvatar): String
    suspend fun updateRoomName(request: ChatRequest.UpdateRoomName): Boolean
    suspend fun addMembers(request: ChatRequest.AddMembers): Boolean
    suspend fun removeMember(request: ChatRequest.RemoveMember): Boolean


}