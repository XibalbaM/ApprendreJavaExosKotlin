package fr.xibalba.apprendreJavaExos

import java.text.Normalizer

fun main() {
    val tree = Tree {
        root(Character("Durin l'Immortel", "(Premier Age)"))
        val durinI = branch(Character("Dúrin VI", "1731-1980"), root!!)
        val nainI = branch(Character("Náin I", "1832-1981"), durinI)
        val thrainI = branch(Character("Thráin I", "1934-2190"), nainI)
        val thorinI = branch(Character("Thorin I", "2035-2289"), thrainI)
        val gloin = branch(Character("Glóin", "2136-2385"), thorinI)
        val oin = branch(Character("Óin", "2238-2488"), gloin)
        val nainII = branch(Character("Náin II", "2338-2585"), oin)
        val borin = branch(Character("Borin", "2542-2711"), nainII)
        val farin = branch(Character("Fárin", "2560-2803"), borin)
        val fundin = branch(Character("Fundin", "2662-2799"), farin)
        leaf(Character("Balin", "2763-2994"), fundin)
        leaf(Character("Dwalin", "2772-3112"), fundin)
        val groin = branch(Character("Gróin", "2671-2923"), farin)
        leaf(Character("Óin", "2774-2994"), groin)
        val gloinII = branch(Character("Glóin", "2783-15 Q.A."), groin)
        leaf(Character("Gimli", "2879-3141 (120 Q.A.)"), gloinII)
        val dainI = branch(Character("Dáin I", "2440-2589"), nainII)
        val gror = branch(Character("Grór", "2563-2805"), dainI)
        val nain = branch(Character("Náin", "2665-2799"), gror)
        val dainII = branch(Character("Dáin II", "2767-3019"), nain)
        val thorinIII = branch(Character("Thorin III", "2866"), dainII)
        leaf(Character("Dúrin VII", "Le Dernier"), thorinIII)
        leaf(Character("Frór", "2552-2589"), dainI)
        val trhor = branch(Character("Thrór", "2542-2790"), dainI)
        val thrainII = branch(Character("Thráin II", "2644-2850"), trhor)
        leaf(Character("Thorin II", "2746-2941"), thrainII)
        leaf(Character("Frerin", "2751-2799"), thrainII)
        val dis = branch(Character("Dís", "2760"), thrainII)
        leaf(Character("Fíli", "2859-2941"), dis)
        leaf(Character("Kíli", "2864-2941"), dis)
    }

    var input: String

    do {
        println("\u001b[31mEntrez le nom d'un personnage, 'display' pour afficher l'arbre ou 'exit' pour quitter\u001b[0m")
        input = readln()
        if (input != "exit" && input != "display") {
            tree.tellName(input)
        } else if (input == "display") {
            tree.display()
        }
    } while (input != "exit")
}

fun Tree<Character>.tellName(name: String) {
    val characters = content.filter { it.content.name.removeNonSpacingMarks().lowercase() == name.removeNonSpacingMarks().lowercase() }
    if (characters.isEmpty()) {
        println("Le personnage $name n'existe pas")
    } else if (characters.size == 1) {
        val character = characters.first()
        if (character is TreePart.Root) {
            println("Je suis ${character.content.name}")
        } else {
            println("Je suis ${character.content.name} fils de ${character.getParentOfNull()!!.content.name}")
        }
    } else {
        println("Il y a ${characters.size} personnages nommés ${characters.first().content.name} dans l'arbre")
        val stringBuilder = StringBuilder("Je suis donc ${characters.first().content.name}, fils de ")
        for (character in characters) {
            stringBuilder.append(character.getParentOfNull()!!.content.name)
            stringBuilder.append(" ou de ")
        }
        stringBuilder.delete(stringBuilder.length - 7, stringBuilder.length)
        println(stringBuilder.toString())
    }
}

fun Tree<Character>.display() {
    val stringBuilder = StringBuilder()
    fun display(treePart: TreePart<Character>, depth: Int) {
        stringBuilder.append("--".repeat(depth))
        stringBuilder.append(treePart.content.name)
        stringBuilder.append("\n")
        if (treePart is TreePart.Branch || treePart is TreePart.Root) {
            for (child in content.filter { it.getParentOfNull() == treePart }) {
                display(child, depth + 1)
            }
        }
    }
    display(root, 0)
    println(stringBuilder.toString())
}

data class Character(val name: String, val lifeDates: String)

fun String.removeNonSpacingMarks() =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")