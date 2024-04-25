package com.example.utils

import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
@Serializable
sealed interface Error

@Serializable
sealed interface DataError : Error {
    @Serializable
    enum class Network(val userMessage :String) : DataError {
        NO_INTERNET_OR_SERVER_DOWN("No internet connection or server down"),
        BAD_REQUEST("Bad request"),
        UNAUTHORIZED("Unauthorized"),
        FORBIDDEN("Forbidden"),
        NOT_FOUND("Not found"),
        METHOD_NOT_ALLOWED("Method not allowed"),
        TIMEOUT("Timeout"),
        PAYLOAD_TOO_LARGE("Payload too large"),
        SERVER_ERROR("Server error"),
        SERIALIZATION_ERROR("Serialization error"),
        UNKNOWN("Unknown"),
        FILE_NOT_FOUND("File not found"),
        FOLDER_NOT_DELETED("Folder not deleted"),
        FILE_NOT_DELETED("File not deleted"),
        CANT_UPLOAD_FILE("Can't upload file"),
        CANT_CREATE_FOLDER("Can't create folder"),
        CANT_GET_FILES_AT_PATH("Can't get files at path"),
        FILE_ALREADY_EXISTS("File already exists"),
        FAILED_TO_SERIALIZE_THE_REQUEST("Failed to serialize the request"),
    }
}

@Serializable
sealed interface DataSuccess {
    @Serializable
    enum class Reply(val message: String) : DataSuccess {
        CREATED_SUCCESSFULLY(" Reply Created successfully"),
        UPDATED_SUCCESSFULLY(" Reply Updated successfully"),
        DELETED_SUCCESSFULLY(" Reply Deleted successfully"),
        UPVOTED_SUCCESSFULLY(" Reply Upvoted successfully"),
        DOWNVOTED_SUCCESSFULLY(" Reply Downvoted successfully")
    }
    @Serializable
    enum class User(val message: String) : DataSuccess {
        CREATED_SUCCESSFULLY(" User Created successfully"),
        UPDATED_SUCCESSFULLY(" User Updated successfully"),
        DELETED_SUCCESSFULLY(" User Deleted successfully"),

    }
}
