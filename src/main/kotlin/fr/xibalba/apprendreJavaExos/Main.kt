package fr.xibalba.apprendreJavaExos

import kotlin.io.path.*

@OptIn(ExperimentalPathApi::class)
fun main() {
    println("Welcome to HTML tag stats !")
    print("Please enter project path : ")
    val path = Path(readln())
    if (path.exists()) {
        if (path.isDirectory()) {
            val htmlFiles = path.walk().filter { it.toString().endsWith(".html") }.map { it.toFile().readText() }.toList().toTypedArray()
            printStats(htmlFiles)
        } else {
            val html = path.readText()
            printStats(arrayOf(html))
        }
    }
}

fun printStats(html: Array<String>) {
    val tags = mutableMapOf<String, Int>()
    for (file in html) {
        val fileTags = extractTagsFromHtml(file)
        for (tag in fileTags) {
            if (tags.containsKey(tag.key)) {
                tags[tag.key] = tags[tag.key]!! + tag.value
            } else {
                tags[tag.key] = tag.value
            }
        }
    }
    println("Tag\t\t\t\t\tOccurrences")
    println("============================================")
    for (tag in tags) {
        println("${tag.key}\t\t\t\t\t${tag.value}")
    }
}

fun extractTagsFromHtml(html: String): Map<String, Int> {
    val regex = "<([a-z0-9-]+)[^>]*>.*</\\1>".toRegex()
    val matches = regex.findAll(html.replace("\n", ""))
    return matches.map { it.groupValues[1] }.groupingBy { it }.eachCount()
}