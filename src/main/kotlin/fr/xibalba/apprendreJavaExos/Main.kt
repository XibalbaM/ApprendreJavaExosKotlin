package fr.xibalba.apprendreJavaExos

class TitleTag(val level: Int, val content: String)

fun main() {
    println("Welcome to HTML to summary converter !")
    println("Choose your mode : (default 1)")
    println("1 - Enter HTML code in console")
    println("2 - Enter HTML code in file")
    println("3 - Enter URL")
    val modeLine = readln()
    val mode = modeLine.matches(Regex("[123]")).let { if (it) modeLine.toInt() else 1 }
    when (mode) {
        1 -> {
            println("Enter HTML code then \"end\" to validate your input :")
            var html = ""
            var line = readln()
            while (line != "end") {
                html += line
                line = readln()
            }
            printTitlesFromHtml(html)
        }

        2 -> {
            try {
                println("Enter file path :")
                val path = readln()
                val html = java.io.File(path).readText()
                printTitlesFromHtml(html)
            } catch (e: Exception) {
                println("An error occurred while processing the file.")
            }
        }

        3 -> {
            try {
                println("Enter URL :")
                val url = readln()
                val html = java.net.URL(url).readText()
                println(html)
                printTitlesFromHtml(html)
            } catch (e: Exception) {
                println("An error occurred while processing the URL.")
            }
        }
    }
}

fun printTitlesFromHtml(html: String) {
    val titles = extractTitlesFromHtml(html)
    titles.forEach { println("---".repeat(it.level - 1) + it.content) }
}

fun extractTitlesFromHtml(html: String): Sequence<TitleTag> {
    val regex = "<h([1-6])[^>]*>(.+?)</h\\1>".toRegex()
    val matches = regex.findAll(html.replace("\n", ""))
    return matches.map { TitleTag(it.groupValues[1].toInt(), it.groupValues[2]) }
}