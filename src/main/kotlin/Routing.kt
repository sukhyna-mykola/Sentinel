package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        // Маршрут для отримання тайлів
        get("/tiles/{z}/{x}/{y}.png") {
            // Отримуємо параметри
            val z = call.parameters["z"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing Z")
            val x = call.parameters["x"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing X")
            val y = call.parameters["y"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing Y")

            // Шлях до тайлу
            val tilePath = "tiles/$z/$x/$y.png"
            val tileFile = File(tilePath)

            // Перевіряємо, чи існує файл
            if (tileFile.exists()) {
                call.respondFile(tileFile)
            } else {
                call.respond(HttpStatusCode.NotFound, "Tile not found")
            }
        }
    }
}
