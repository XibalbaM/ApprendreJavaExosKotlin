package fr.xibalba.apprendreJavaExos

fun main() {

    val tree = Tree<Character>(mutableListOf())

    var input = ""

    while (input != "exit") {
        input = readln()
        when {
            input.startsWith("add") -> {
                if (input.split(" ").size < 3) {
                    println("Error: invalid input. Usage: add <name> <lifeDates>")
                    continue
                }
                val name = input.split(" ")[1]
                val lifeDates = input.split(" ")[2]
                tree.content.add(TreePart(Character(name, lifeDates), mutableListOf()))
                println("Character added successfully")
            }
            input.startsWith("parent") -> {
                if (input.split(" ").size < 3) {
                    println("Error: invalid input. Usage: parent <childName> <parentName1> <parentName2> ...")
                    continue
                }
                val child = tree.content.find { it.content.name == input.split(" ")[1] }
                if (child == null) {
                    println("Error: child not found")
                    continue
                }
                val parents = input.split(" ").subList(2, input.split(" ").size).map { parentName ->
                    tree.content.find { it.content.name == parentName }
                }.filterNotNull()
                if (parents.isEmpty()) {
                    println("Error: no parent found")
                    continue
                }
                tree.addParents(child, parents)
                println("Parents added successfully")
            }
            input.startsWith("child") -> {
                if (input.split(" ").size < 3) {
                    println("Error: invalid input. Usage: child <parentName> <childName1> <childName2> ...")
                    continue
                }
                val parent = tree.content.find { it.content.name == input.split(" ")[1] }
                if (parent == null) {
                    println("Error: parent not found")
                    continue
                }
                val children = input.split(" ").subList(2, input.split(" ").size).map { childName ->
                    tree.content.find { it.content.name == childName }
                }.filterNotNull()
                if (children.isEmpty()) {
                    println("Error: no child found")
                    continue
                }
                tree.addChildren(parent, children)
                println("Children added successfully")
            }
            input == "display" -> {
                tree.display()
            }
            input.startsWith("ancestors") -> {
                if (input.split(" ").size < 2) {
                    println("Error: invalid input. Usage: ancestors <name>")
                    continue
                }
                val name = input.split(" ")[1]
                val characterTreePart = tree.content.find { it.content.name == name }
                if (characterTreePart == null) {
                    println("Error: character not found")
                    continue
                }
                println(characterTreePart)
            }
            input == "help" -> {
                println("Commands:")
                println("  add <name> <lifeDates>: add a character to the tree")
                println("  parent <childName> <parentName1> <parentName2> ...: add parents to a character")
                println("  child <parentName> <childName1> <childName2> ...: add children to a character")
                println("  display: display the tree")
                println("  ancestors <name>: display the ancestors of a character")
                println("  exit: exit the program")
            }
            input == "exit" -> {
                println("Goodbye!")
            }
            else -> {
                println("Error: invalid input")
            }
        }
    }
}

fun Tree<Character>.display() {
    for (treePart in this.content) {
        println(treePart.content)
        if (treePart.parents.isEmpty()) {
            println("  Parents: none")
        } else {
            println("  Parents: ${treePart.parents.joinToString { it.content.name }}")
        }
        if (this.getChildren(treePart).isEmpty()) {
            println("  Children: none")
        } else {
            println("  Children: ${this.getChildren(treePart).joinToString { it.content.name }}")
        }
    }
}

data class Character(val name: String, val lifeDates: String) {
    override fun toString(): String {
        return "$name ($lifeDates)"
    }
}