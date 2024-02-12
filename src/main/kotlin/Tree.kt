package fr.xibalba.apprendreJavaExos

data class Tree<T>(val content: MutableList<TreePart<T>>, val root: TreePart.Root<T>)

fun <T> Tree(builder: TreeBuilder<T>.() -> Unit): Tree<T> {
    val treeBuilder = TreeBuilder<T>()
    treeBuilder.builder()
    return treeBuilder.build()
}

class TreeBuilder<T> {
    private val content = mutableListOf<TreePart<T>>()
    var root: TreePart.Root<T>? = null

    fun root(content: T) {
        root = TreePart.Root(content)
    }

    fun branch(content: T, parent: TreePart<T>): TreePart.Branch<T> {
        val branch = TreePart.Branch(content, parent)
        this.content += branch
        return branch
    }

    fun leaf(content: T, parent: TreePart<T>): TreePart.Leaf<T> {
        val leaf = TreePart.Leaf(content, parent)
        this.content += leaf
        return leaf
    }

    fun build(): Tree<T> {
        assert(root != null) { "Root is not defined" }
        return Tree(content, root!!)
    }
}

sealed class TreePart<T>(val content: T) {
    class Branch<T>(content: T, val parent: TreePart<T>) : TreePart<T>(content)
    class Leaf<T>(content: T, val parent: TreePart<T>) : TreePart<T>(content)
    class Root<T>(content: T) : TreePart<T>(content)

    fun getParentOfNull(): TreePart<T>? {
        return when (this) {
            is Branch -> this.parent
            is Leaf -> this.parent
            is Root -> null
        }
    }
}