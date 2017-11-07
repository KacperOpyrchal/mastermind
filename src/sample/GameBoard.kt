package sample

import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class GameBoard(val spotsNumber: Int, val colorsNumber: Int) {

    var activePane = ColorsPane(spotsNumber, colorsNumber)
    val vbox = VBox(10.0)
    var hbox = HBox(20.0)

    fun getPane() = ScrollPane().apply {
        stylesheets.addAll(Presenter.appResource)
        padding = Insets(10.0)
        hbox.children.add(activePane.getPane())
        vbox.children.add(hbox)
        content = vbox
    }

    fun addResponsePane(response: Pair<Int, Int>, repeat: Boolean) {
        hbox.children.add(ResponsePane().getPane(response, repeat))
    }

    fun addNextPane() {
        activePane.disableListeners()
        activePane = ColorsPane(spotsNumber, colorsNumber)
        hbox = HBox(20.0).apply {
            children.addAll(activePane.getPane())
        }
        vbox.children.add(hbox)
    }

    fun getGuess(): IntArray {
        return activePane.getGuess()
    }

    fun setColors(colors: IntArray) {
        activePane.setColors(colors)
    }

}