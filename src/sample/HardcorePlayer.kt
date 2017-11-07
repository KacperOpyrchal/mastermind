package sample

class HardcorePlayer : Computer {

    var currentGuess    : Guess = Guess.getInitialGuess()
    var currentResponse : Pair<Int, Int> = Pair(0, 0)
    var encodedResponse = 0
    private var initial         : Boolean = true
    private var possibleGuessSet : Set<Guess> = Guess.generatePossibleGuessesSet()

    /**
     * guess
     */
    override fun generateNextGuess() : IntArray {
        if(Guess.NUMBER_OF_COLORS <= 6 && Guess.NUMBER_OF_SPOTS <= 6) generateNextOptimalGuess()
        else generateNextNotOptimalGuess()

        return currentGuess.spots
    }

    /**
     * response
     */
    override fun setResponse(response : Pair<Int, Int>) {
        encodedResponse = response.first * 2 + response.second
        refactorPossibleGuesses()
    }

    private fun generateNextNotOptimalGuess() {
        if (initial) { // TODO get schwifty
            initial = false
            return
        }
        currentGuess = possibleGuessSet.first()
    }

    private fun generateNextOptimalGuess() {

        if (initial) { // TODO get schwifty
            initial = false
            return
        }

        currentGuess = possibleGuessSet.first()
        var minimumLeft = Int.MAX_VALUE

        possibleGuessSet.forEach { guess ->
            var maximumLeft = 0

            Guess.generateAllPossibleResponses().forEach{ response ->

                val numberOfLeft = possibleGuessSet
                        .filter { it.checkCompatibility(guess, response) }
                        .size

                if (maximumLeft < numberOfLeft) maximumLeft = numberOfLeft
            }
            if(minimumLeft > maximumLeft) {
                minimumLeft = maximumLeft
                currentGuess = guess
            }
        }
    }

    private fun refactorPossibleGuesses() {
        val possibleResponses = Guess.decodeResponse(encodedResponse)
        val refactoredUnion = mutableSetOf<Guess>()

        possibleResponses.forEach {
            currentResponse = it
            refactoredUnion += filterPossibleGuesses()
        }
        possibleGuessSet = refactoredUnion
    }

    private fun filterPossibleGuesses() : Set<Guess> {
        return possibleGuessSet
                .filter { it.checkCompatibility(currentGuess, currentResponse) }
                .toSet()
    }

    companion object {

        fun checkGuess(guessArray : IntArray, secretArray : IntArray) : Int {
            val response = Guess(guessArray).generateResponseForSecret(Guess(secretArray))
            return (2 * response.first + response.second)
        }

    }
}