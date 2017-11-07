package sample

const val DEFAULT_COLORS = 6
const val DEFAULT_SPOTS  = 4

class Guess (val spots : IntArray) {

    fun generateResponseForSecret(secretCode : Guess) : Pair<Int, Int> {
        val fullMatch : Int = spots fullMatches secretCode.spots
        val halfMatch : Int = spots halfMatches secretCode.spots
        return Pair(fullMatch, halfMatch - fullMatch)
    }

    fun checkCompatibility(secretCode: Guess, response : Pair<Int, Int>) : Boolean {
        return generateResponseForSecret(secretCode) == response
    }

    companion object {

        var NUMBER_OF_SPOTS = DEFAULT_SPOTS
        var NUMBER_OF_COLORS = DEFAULT_COLORS

        fun getInitialGuess() : Guess = Guess(IntArray(NUMBER_OF_SPOTS,
                { i -> (i / 2) % Guess.NUMBER_OF_COLORS })) //Knuth's initial guess


        fun generatePossibleGuessesSet() : Set<Guess> {
            val combinations = ArrayList<Int>().plus(0 until Guess.NUMBER_OF_COLORS)
            return  (combinations
                    .generatePermutations(Guess.NUMBER_OF_SPOTS)
                    .map { value -> Guess(value.toIntArray()) }
                    .toSet())
        }

        fun decodeResponse(encodedResponse : Int) : List<Pair<Int, Int>> {
            return (0..(encodedResponse / 2))
                    .filter { encodedResponse - it <= Guess.NUMBER_OF_SPOTS }
                    .map { Pair(it, encodedResponse - 2 * it) }
        }

        fun generateAllPossibleResponses() : Set<Pair<Int, Int>> {

            val possibleResponses = mutableSetOf<Pair<Int, Int>>()

            for(i in 0..Guess.NUMBER_OF_SPOTS) {
                (0..(Guess.NUMBER_OF_SPOTS - i)).mapTo(possibleResponses) { Pair(i, it) }
            }
            return possibleResponses
        }
    }
}

fun List<Int>.generatePermutations(size : Int) : List<ArrayList<Int>> {
    if (size == 1) { return this.map { element -> arrayListOf (element) } }

    val permutations : ArrayList<ArrayList<Int>> = arrayListOf()
    this.forEach { element ->
        val permutationsPerElement = this.generatePermutations(size - 1)
        permutationsPerElement.forEach { list -> list.add(element) }
        permutations.addAll(permutationsPerElement)
    }
    return permutations
}

infix fun IntArray.fullMatches(array : IntArray) : Int {
    return (0 until this.size)
            .filter { this[it] == array[it] }
            .size
}

infix fun IntArray.halfMatches(array : IntArray) : Int {
    var matchCounter = 0
    val current = this.copyOf()

    array.forEach { value ->
        for (i in 0 until current.size) {
            if (current[i] == value) {
                matchCounter += 1
                current[i] = -1
                break
            }
        }
    }
    return matchCounter
}