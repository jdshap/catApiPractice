
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import java.io.IOException


var client: OkHttpClient = OkHttpClient()

fun run(url: String): String {
    var result = ""
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        else

        result = response.body?.string()?: ""
    }
    return result
}

fun main() {
    val doc: Document = parse(run("https://github.com/jdshap?tab=repositories"))
    val repos = doc.select("h3.wb-break-all a").map {link->
        "https://github.com" + link.attr("href")
    }
    repos.forEach(::println)
}