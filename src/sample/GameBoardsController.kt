package sample

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.*

class GameBoardsController(val spotsNumber: Int, val colorsNumber: Int, val playerSecret: IntArray, val repeat: Boolean) {

    lateinit var playerGameBoard: GameBoard

    lateinit var computerGameBoard: GameBoard

    lateinit var vbox: VBox

    var computerSecret =  IntArray(spotsNumber, { Random().nextInt(colorsNumber-1)})

    lateinit var computer: Computer

    var playerSet = IntArray(spotsNumber, { 0 })

    var endComp = false

    var endPlayer = false

    var notShowed = true

    fun getPane(): VBox {
        vbox = VBox(10.0).apply {
            stylesheets.addAll(Presenter.appResource)
        }

        Guess.NUMBER_OF_COLORS = colorsNumber
        Guess.NUMBER_OF_SPOTS = spotsNumber

        computer = if(repeat) HardcorePlayer() else Player()


        playerGameBoard = GameBoard(spotsNumber, colorsNumber)
        computerGameBoard = GameBoard(spotsNumber, colorsNumber)


        val boards = HBox(10.0).apply {
            children.addAll(playerGameBoard.getPane(), computerGameBoard.getPane())
        }

        boards.alignment = Pos.TOP_CENTER

        computerGameBoard.setColors(computer.generateNextGuess())
        playerGameBoard.setColors(playerSet)

        val secretPane = ColorsPane(spotsNumber, colorsNumber)

        vbox.children.addAll(secretPane.getPane().apply { alignment = Pos.TOP_CENTER }, Button("NEXT").apply {
            id = Strings.DEFAULT_ID
            setOnAction {
                nextCycle()
            }
        }, boards, HBox(10.0).apply {
            alignment = Pos.TOP_CENTER
            children.addAll(Button("Pokaz kod").apply {
            id = Strings.DEFAULT_ID
            alignment = Pos.TOP_CENTER
            setOnAction {
                if(notShowed) {
                    val cPane = ColorsPane(spotsNumber, colorsNumber)
                    vbox.children.add(cPane.getPane().apply { alignment = Pos.TOP_CENTER })
                    cPane.setColors(computerSecret)
                    cPane.disableListeners()
                    notShowed = false
                }
            }}, Button("Wróć").apply {
            id = Strings.DEFAULT_ID
            alignment = Pos.TOP_CENTER
            setOnAction {
                Main.initAgain()
            }})
        })


        secretPane.setColors(playerSecret)
        secretPane.disableListeners()

        vbox.alignment = Pos.TOP_CENTER
        vbox.padding = Insets(10.0)

        return vbox
    }

    fun nextCycle() {
        if(!endPlayer) {
            var odp = Player.checkGuess(playerGameBoard.getGuess(), computerSecret)
            playerGameBoard.addResponsePane(odp, repeat)
            if(odp.first == spotsNumber) {
                endPlayer = true
            }else {
                playerSet = playerGameBoard.getGuess()
                playerGameBoard.addNextPane()
                playerGameBoard.setColors(playerSet)
            }
        }

        if(!endComp) {
            val check = computerGameBoard.getGuess()

            var odp = Player.checkGuess(check, playerSecret)

            computer.setResponse(odp)
            computerGameBoard.addResponsePane(odp, repeat)
            if(odp.first == spotsNumber) {
                endComp = true
            }else {
                computerGameBoard.addNextPane()
                computerGameBoard.setColors(computer.generateNextGuess())
            }
        }
    }
}