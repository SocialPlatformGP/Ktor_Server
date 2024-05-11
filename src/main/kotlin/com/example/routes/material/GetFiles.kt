package com.example.routes.material

import com.example.data.requests.MaterialRequest
import com.example.repository.material.MaterialRepository
import com.example.utils.EndPoint
import com.example.utils.MaterialError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getFiles(
    materialRepository: MaterialRepository
) {
    post(EndPoint.Media.GetFiles.route) {
        val request = call.receiveNullable<MaterialRequest.GetMaterialInPath>()
        if(request == null){
            println("****************** 00${request}***********************")
            call.respond(HttpStatusCode.BadRequest, MaterialError.SERVER_ERROR)
            return@post
        }
        println("****************** 00${request}***********************")
        val response = materialRepository.getMaterialResponse(request.path.trim())
        println("****************** $response***********************")
        call.respond(
            HttpStatusCode.OK,
            response
        )
    }

}
