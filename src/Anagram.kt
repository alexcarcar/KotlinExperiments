import java.util.Locale.getDefault

class Anagram(source: String) {
    private val source = source.lowercase(getDefault())

    fun match1(anagrams: Collection<String>): Set<String> = anagrams.filter {
        it.lowercase(getDefault()) != source && it.lowercase(getDefault()).isAnagramOf1(source)
    }.toSet()

    fun match2(anagrams: Collection<String>): Set<String> = anagrams.filter {
        it.lowercase(getDefault()) != source && it.lowercase(getDefault()).isAnagramOf2(source)
    }.toSet()

    fun String.isAnagramOf1(word: String): Boolean {
        return lowercase(getDefault()).toList().sorted() == word.lowercase(getDefault()).toList().sorted()
    }

    fun String.isAnagramOf2(word: String): Boolean =
        if (isEmpty()) word.isEmpty()
        else first().let { ch ->
            val index = word.indexOf(ch)
            if (index == -1) false
            else drop(1).isAnagramOf2(word.removeRange(index, index + 1))
        }
}

fun main() {
    val anagramChecker = Anagram("listen")

    val result1 = anagramChecker.match1(listOf("enlists", "google", "inlets", "banana", "silent"))
    println(result1) // [inlets, silent]

    anagramChecker.match2(listOf("enlists", "google", "inlets", "banana", "silent"))
    println(result1) // [inlets, silent]
}