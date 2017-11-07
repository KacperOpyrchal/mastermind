package sample

interface Computer {
    fun generateNextGuess() : IntArray

    fun setResponse(response : Pair<Int, Int>)

}