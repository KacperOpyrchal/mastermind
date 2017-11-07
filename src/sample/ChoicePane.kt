package sample

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.VBox

class ChoicePane(val spotsNumber: Int, val colorsNumber: Int, val repeat: Boolean, val callback: Action) {

    val colors: ColorsPane = ColorsPane(spotsNumber, colorsNumber)

    fun getPane(): VBox {
        val vbox = VBox(10.0).apply {
            alignment = Pos.CENTER
            children.addAll(SampleProvider.getDefaultLabel("Kod dla komputera: ").apply { alignment = Pos.TOP_CENTER },
                    colors.getPane().apply { alignment = Pos.TOP_CENTER }, getStartButton(callback))
        }

        return vbox
    }

    private fun getStartButton(callback: Action): Button = Button().apply {
        id = Strings.DEFAULT_ID
        text = Strings.START_BUTTON_LABEL
        setOnAction {
            callback.invoke(Data(spotsNumber, colorsNumber, colors.getGuess(), repeat))
        }
    }

}