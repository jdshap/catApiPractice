import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class CatFact(val text: String)

private val json = Json { ignoreUnknownKeys = true }

suspend fun main() {
    val client = HttpClient(CIO)
    val request: String = client.get("https://cat-fact.herokuapp.com/facts").body()
    val facts: List<CatFact> = json.decodeFromString<List<CatFact>>(request)
    while (continueQuery()) {
        println("\n${facts.random().text}")
    }
}

fun continueQuery(): Boolean {
    println("\nWant a cat fact? Y/N")
    return readln().lowercase().first() == 'y'
}