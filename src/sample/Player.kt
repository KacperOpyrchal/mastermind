package sample

open class Player : Computer {

    private var currentGuess    : Guess = Guess.getInitialGuess()
    private var currentResponse : Pair<Int, Int> = Pair(0, 0)
    private var initial         : Boolean = true
    private var possibleGuessSet : Set<Guess> = Guess.generatePossibleGuessesSet()

    /**
     * guess
     */
    override fun generateNextGuess() : IntArray {
//        generateNextOptimalGuess()
        generateNextNotOptimalGuess()
        return currentGuess.spots
    }

    /**
     * response
     */
    override fun setResponse(response : Pair<Int, Int>) {
        currentResponse = response
        refactorPossibleGuesses()
    }

    private fun refactorPossibleGuesses() {
        possibleGuessSet = filterPossibleGuesses()
    }

    private fun filterPossibleGuesses() : Set<Guess> {
        return possibleGuessSet
                .filter { it.checkCompatibility(currentGuess, currentResponse) }
                .toSet()
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

    companion object {

        fun checkGuess(guessArray : IntArray, secretArray : IntArray) : Pair<Int, Int> {
            return Guess(guessArray).generateResponseForSecret(Guess(secretArray))
        }

    }
}