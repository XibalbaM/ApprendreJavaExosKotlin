package fr.xibalba.apprendreJavaExos

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.math.floor
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    runBlocking {
        (1..10).map {
            async { sendSpaceship(floor(Math.random() * 20).toInt().toDuration(DurationUnit.SECONDS), "Mars", "Spaceship $it") }
        }.awaitAll()
        println("All spaceships have arrived at Mars !")
    }
}

suspend fun sendSpaceship(duration: Duration, destination: String, spaceshipName: String) {
    println("$spaceshipName is going to $destination, it will take $duration to get there.")
    delay(duration)
    println("$spaceshipName has arrived at $destination !")
}