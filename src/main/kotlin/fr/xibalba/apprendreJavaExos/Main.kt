package fr.xibalba.apprendreJavaExos

val emojiMapping = mapOf(
    "smile" to "D83D.DE00",
    "joy" to "D83D.DE02",
    "pleading_face" to "D83E.DD7A",
    "rocket" to "D83D.DE80",
    "cat_face" to "D83D.DC31"
)

fun main() {
    print("Text to decode: ")
    val text = readln()
    val stringBuilder = StringBuilder()
    var wasPreviousCharEmoji = false
    text.toCharArray().forEachIndexed { index, char ->
        if (!wasPreviousCharEmoji && char.code > 53248 && text.length > index + 1 && text[index + 1].code > 53248) {
            val emojiCode = char.code.toHexStringWithoutZerosAtStart() + "." + text[index + 1].code.toHexStringWithoutZerosAtStart()
            val emojiName = emojiMapping.filterValues { it == emojiCode }.keys.firstOrNull()
            if (emojiName != null) {
                stringBuilder.append(":$emojiName:")
                wasPreviousCharEmoji = true
            } else {
                stringBuilder.append(char)
            }
        } else {
            if (wasPreviousCharEmoji) {
                wasPreviousCharEmoji = false
            } else {
                stringBuilder.append(char)
            }
        }
    }
    println("Decoded text: $stringBuilder")
}

fun Int.toHexStringWithoutZerosAtStart(): String {
    return this.toString(16).replace(Regex("^0*"), "").uppercase()
}