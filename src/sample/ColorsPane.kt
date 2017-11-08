package sample

import javafx.scene.control.Button
import javafx.scene.layout.HBox


class ColorsPane(val spotsNumber: Int, val colorsNumber: Int) {

    val buttons = mutableListOf<ColorButton>()

    fun getPane(): HBox {
        val hbox = HBox(10.0).apply {
            stylesheets.addAll(Presenter.appResource)
        }

        for (i in 0..(spotsNumber-1)) {
            getColorButton(i).let {
                buttons.add(it)
                hbox.children.add(it)
            }
        }

        return hbox
    }

    fun setColors(colors: IntArray) {
        for (i in 0 until buttons.size) {
            buttons[i].styleID = colors[i]
            buttons[i].text = buttons[i].styleID.toString()
            buttons[i].id = Strings.COLOR_ID + buttons[i].styleID
        }
    }

    fun disableListeners() {
        buttons.forEach { it.setOnAction {  } }
    }

    fun getGuess(): IntArray {
        return IntArray(buttons.size, {i -> buttons[i].styleID})
    }

    private fun getColorButton(i: Int) = ColorButton(0).apply {
        id = Strings.COLOR_ID + "0"

        setOnAction {
            styleID++
            styleID %= colorsNumber
            id = Strings.COLOR_ID + styleID
            text = styleID.toString()
        }
    }

    class ColorButton(var styleID: Int): Button(styleID.toString())

}
