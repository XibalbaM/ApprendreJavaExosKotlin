package fr.xibalba.apprendreJavaExos

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

val client = HttpClient(CIO) {
    install(UserAgent) {
        agent = "Chrome/117.0.0.0"
    }
    install(ContentNegotiation) {
        gson {
            disableHtmlEscaping()
        }
    }
}
val gson = Gson()

suspend fun main() {
    val pokemonListResponseBody = client.get("https://pokeapi.co/api/v2/pokemon/").body<JsonObject>()
    val pokemonList = gson.fromJson(pokemonListResponseBody["results"], Array<PokemonUrl>::class.java)
    val pokemon = pokemonList.first()
    println(pokemon.let { "${it.name}: ${it.url}" })
    val pokemonResponseBody = client.get(pokemon.url).body<Pokemon>()
    val moveResponseBody = client.get(pokemonResponseBody.moves.first().move.url).body<Move>()
    println(moveResponseBody.let { "${it.name}: ${it.id}\nEffect: ${it.effectEntries.first().effect}" })
}

data class PokemonUrl(
    val name: String,
    val url: String,
)

data class Pokemon(
    val name: String,
    val moves: List<MoveData>,
)

data class MoveData(
    val move: MoveUrl,
)

data class MoveUrl(
    val name: String,
    val url: String,
)

data class Move(
    val name: String,
    val id: Int,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntry>,
)

data class EffectEntry(
    val effect: String,
    @SerializedName("short_effect")
    val shortEffect: String,
)