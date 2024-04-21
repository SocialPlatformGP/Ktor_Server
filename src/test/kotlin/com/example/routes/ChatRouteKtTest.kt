package com.example.routes

import com.example.module
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class ChatRouteKtTest {

    @Test
    fun testPostIsroomexist() = testApplication {
        application {
            module()
        }

    }
}