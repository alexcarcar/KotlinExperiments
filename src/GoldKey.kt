/**
 * Game Mechanics
 *
 * There are N keys, one of which is gold (at position P).
 *
 * Players take turns: Steve goes first.
 *
 * On each turn, a player selects M consecutive keys that must include the gold key, and reverses their order.
 *
 * The goal is to move the gold key to position X.
 *
 * Players play optimally.
 *
 * A draw is possible if neither player can force a win.
 *
 * Let’s write a Kotlin program that simulates this game and determines the outcome: Steve wins, Harvey wins, or Draw.
 *
 * 🧠 Strategy
 * We’ll use Breadth-First Search (BFS) to explore all reachable positions of the gold key. Each move is a reversal of M consecutive keys containing the gold key. We’ll track whose turn it is and whether a winning path exists.
 *
 */
enum class Result { STEVE, HARVEY, DRAW }

fun solveGame(n: Int, m: Int, p: Int, x: Int): Result {
    val visited = Array(n + 1) { BooleanArray(2) } // visited[position][turn]
    val queue: ArrayDeque<Triple<Int, Int, Int>> = ArrayDeque() // (position, turn, depth)
    queue.add(Triple(p, 0, 0)) // Steve starts, turn = 0

    while (queue.isNotEmpty()) {
        val (pos, turn, depth) = queue.removeFirst()

        if (visited[pos][turn]) continue
        visited[pos][turn] = true

        if (pos == x) {
            return if (turn == 0) Result.STEVE else Result.HARVEY
        }

        val startRange = maxOf(1, pos - m + 1)
        val endRange = minOf(pos, n - m + 1)

        for (start in startRange..endRange) {
            val end = start + m - 1
            val newPos = start + end - pos // reversed position of gold key
            queue.add(Triple(newPos, 1 - turn, depth + 1))
        }
    }

    return Result.DRAW
}

fun main() {
    val useCases = listOf(
        // Format: (N, M, P, X)
        TestData(5, 3, 2, 4),   // Case 1: Steve can win
        TestData(6, 2, 3, 6),   // Case 2: Harvey wins
        TestData(7, 3, 4, 4),   // Case 3: Already at target → Steve wins
        TestData(8, 4, 2, 7),   // Case 4: Draw
        TestData(10, 5, 5, 1)   // Case 5: Steve wins with deep strategy
    )

    for ((index, case) in useCases.withIndex()) {
        val (n, m, p, x) = case
        val result = solveGame(n, m, p, x)
        println("Use Case ${index + 1}: N=$n, M=$m, P=$p, X=$x → Result: ${result.name}")
    }
}

data class TestData(val i: Int, val i2: Int, val i3: Int, val i4: Int)
