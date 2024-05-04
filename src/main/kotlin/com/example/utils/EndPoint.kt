package com.example.utils

sealed class EndPoint(val route: String) {
    object Assignment{
        data object CreateAssignment : EndPoint("/createAssignment")
    }
    object Chat {
        object Room {
            data object RoomExist : EndPoint("/isRoomExist")
            data object CreateGroupRoom : EndPoint("/createGroupRoom")
            data object GetRoomDetails : EndPoint("/getRoomDetails")
            data object UpdateRoomAvatar : EndPoint("/updateRoomAvatar")
            data object UpdateRoomName : EndPoint("/updateRoomName")
            data object AddMembers : EndPoint("/addMembers")
            data object RemoveMember : EndPoint("/removeMember")
        }

        object Messages {
            data object FetchChatMessages : EndPoint("/fetchChatMessages")
            data object ReportMessage : EndPoint("/reportMessage")
        }

        object RecentRooms {
            data object GetAllRecentRooms : EndPoint("/getRecentRooms")
        }

        object WebSocket {
            data object ChatSocket : EndPoint("/chatSocket")
        }
    }

    object Auth {
        data object CheckAvailability : EndPoint("/isEmailAvailable")
        data object GetSignedUser : EndPoint("/getSignedUser")
        data object GetAllUsers : EndPoint("/getAllUsers")
        data object SignUp : EndPoint("/signup")
        data object SignIn : EndPoint("/signin")
        data object GetUsersByIds: EndPoint("/getUsersByIds")
       data object CreateUser : EndPoint("/createUser")
    }
    object Post {
        data object CreatePost : EndPoint("/createPost")
        data object GetAllPosts : EndPoint("/getAllPosts")
        data object DeletePost : EndPoint("/deletePost")
        data object UpdatePost : EndPoint("/updatePost")
        data object GetNewPosts : EndPoint("/getNewPosts")
        data object UpvotePost : EndPoint("/upvotePost")
        data object DownvotePost : EndPoint("/downvotePost")
        data object ReportPost : EndPoint("/reportPost")
        object Tags {
            data object InsertTag : EndPoint("/insertTag")
            data object GetAllTags : EndPoint("/getAllTags")
        }
    }
    object Reply{
        data object CreateReply : EndPoint("/createReply")
        data object FetchReplies : EndPoint("/fetchReplies")
        data object UpdateReply : EndPoint("/updateReply")
        data object UpvoteReply : EndPoint("/upvoteReply")
        data object DownvoteReply : EndPoint("/downvoteReply")
        data object DeleteReply : EndPoint("/deleteReply")
        data object ReportReply : EndPoint("/reportReply")

    }
    object Media {
        data object UploadFolder : EndPoint("/uploadFolder")
        data object UploadFile : EndPoint("/uploadFile")
        data object GetFiles : EndPoint("/get_files")
        data object DeleteFile : EndPoint("/deleteFile")
        data object DownloadFile:EndPoint("/downloadFile")
    }


}