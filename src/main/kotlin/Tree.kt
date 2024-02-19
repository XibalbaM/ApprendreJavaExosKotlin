package fr.xibalba.apprendreJavaExos

open class Tree<T>(val content: MutableList<TreePart<T>>) {
    fun getChildren(parent: TreePart<T>): List<TreePart<T>> {
        return content.filter { it.parents.contains(parent) }
    }

    fun add(content: T): TreePart<T> {
        if (content in this.content.map { it.content })
            throw IllegalArgumentException("This content is already in the tree")
        val branch = TreePart(content, mutableListOf())
        this.content += branch
        return branch
    }

    fun addParents(child: TreePart<T>, parents: List<TreePart<T>>) {
        child.parents.addAll(parents)
    }

    fun addChildren(parent: TreePart<T>, children: List<TreePart<T>>) {
        for (child in children) {
            child.parents.add(parent)
        }
    }
}

fun <T> Tree(builder: TreeBuilder<T>.() -> Unit): Tree<T> {
    val treeBuilder = TreeBuilder<T>()
    treeBuilder.builder()
    return treeBuilder.build()
}

class TreeBuilder<T> {
    private val content = mutableListOf<TreePart<T>>()

    fun append(content: T): TreePart<T> {
        val branch = TreePart(content, mutableListOf())
        this.content += branch
        return branch
    }

    fun append(content: T, parents: List<TreePart<T>>): TreePart<T> {
        val branch = TreePart(content, parents.toMutableList())
        this.content += branch
        return branch
    }

    fun addParents(child: TreePart<T>, parents: List<TreePart<T>>) {
        child.parents.addAll(parents)
    }

    fun addChildren(parent: TreePart<T>, children: List<TreePart<T>>) {
        for (child in children) {
            child.parents.add(parent)
        }
    }

    fun build(): Tree<T> {
        return Tree(content)
    }
}

data class TreePart<T>(val content: T, val parents: MutableList<TreePart<T>>) {
    override fun toString(): String {
        var result = "$content"
        for (parent in parents) {
            result += "\n <- $parent"
        }
        return result
    }
}