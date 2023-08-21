package fr.xibalba.apprendreJavaExos

import java.nio.file.Files
import java.nio.file.Path

val emojiMapping: MutableMap<String, String> by lazy {
    if (Files.exists(Path.of("emojiMapping.txt"))) {
        Files.readAllLines(Path.of("emojiMapping.txt")).map { it.split(" ") }.associate { it[0] to it[1] }.toMutableMap()
    } else {
        mutableMapOf(
            "smile" to "D83D.DE00",
            "joy" to "D83D.DE02",
            "pleading_face" to "D83E.DD7A",
            "rocket" to "D83D.DE80",
            "cat_face" to "D83D.DC31"
        )
    }
}

fun main() {
    print("Text to decode: ")
    val text = readln()
    val notMappedEmojis = mutableListOf<String>()
    val stringBuilder = StringBuilder()
    var wasPreviousCharEmoji = false
    var wasPreviousCharUnreconizedEmoji = false
    text.toCharArray().forEachIndexed { index, char ->
        if (!wasPreviousCharEmoji && !wasPreviousCharUnreconizedEmoji && char.code > 53248 && text.length > index + 1 && text[index + 1].code > 53248) {
            val emojiCode = emojiCode(char, text[index + 1])
            val emojiName = emojiMapping.filterValues { it == emojiCode }.keys.firstOrNull()
            if (emojiName != null) {
                stringBuilder.append(":$emojiName:")
                wasPreviousCharEmoji = true
            } else {
                stringBuilder.append(char)
                notMappedEmojis.add(char.toString() + text[index + 1].toString())
                wasPreviousCharUnreconizedEmoji = true
            }
        } else {
            if (wasPreviousCharEmoji) {
                wasPreviousCharEmoji = false
            } else {
                stringBuilder.append(char)
                wasPreviousCharUnreconizedEmoji = false
            }
        }
    }
    println("Decoded text: $stringBuilder")
    if (notMappedEmojis.isNotEmpty()) {
        print("Do you want to map missing emojis? (y/N) ")
        val answer = readln()
        if (answer.equals("y", true)) {
            notMappedEmojis.forEach {
                print("Emoji $it: ")
                val emojiName = readln()
                if (emojiName.isNotBlank())
                    emojiMapping[emojiName] = emojiCode(it[0], it[1])
            }
            Files.write(Path.of("emojiMapping.txt"), emojiMapping.map { "${it.key} ${it.value}" })
        }
    }
}

fun emojiCode(char1: Char, char2: Char): String {
    return char1.code.toHexStringWithoutZerosAtStart() + "." + char2.code.toHexStringWithoutZerosAtStart()
}

fun Int.toHexStringWithoutZerosAtStart(): String {
    return this.toString(16).replace(Regex("^0*"), "").uppercase()
}