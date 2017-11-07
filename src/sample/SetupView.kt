package sample

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import sample.SampleProvider.Companion.getDefaultLabel

class SetupView {

    lateinit var colors: Spinner<Int>
    lateinit var spots: Spinner<Int>
    lateinit var repeat: CheckBox

    lateinit var pane: VBox

    fun getPane(callback: Action): VBox {
        pane = VBox(Values.DEFAULT_GAP).apply {
            stylesheets.addAll(Presenter.appResource)
            id = Strings.PANE_ID
            alignment = Pos.CENTER
            children.addAll(getSetupPane(), getStartButton(callback))
            padding = Insets(Values.DEFAULT_GAP)
        }

        return pane
    }

    private fun getSetupPane(): GridPane = GridPane().apply {
        padding = Insets(Values.DEFAULT_GAP)
        vgap = Values.DEFAULT_GAP
        hgap = Values.DEFAULT_GAP
        alignment = Pos.CENTER
        addColumn(0,
                getDefaultLabel(Strings.COLORS_LABEL),
                getDefaultLabel(Strings.SPOTS_LABEL),
                getDefaultLabel(Strings.REPEAT_LABEL))
        colors = getColorsSpinner()
        spots = getSpotsSpinner()
        repeat = getRepeatCheckBox()
        addColumn(1, colors, spots, repeat)
    }

    private fun getRepeatCheckBox(): CheckBox = CheckBox().apply {
        id = Strings.DEFAULT_ID
        isSelected = false
    }

    private fun getColorsSpinner(): Spinner<Int> = Spinner<Int>().apply {
        id = Strings.DEFAULT_ID
        valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(Values.MIN_VALUE,
                Values.MAX_VALUE,
                Values.DEFAULT_VALUE_COLORS)
    }

    private fun getSpotsSpinner(): Spinner<Int> = Spinner<Int>().apply {
        id = Strings.DEFAULT_ID
        valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(Values.MIN_VALUE,
                Values.MAX_VALUE,
                Values.DEFAULT_VALUE_SPOTS)
    }

    private fun getStartButton(callback: Action): Button = Button().apply {
        id = Strings.DEFAULT_ID
        text = Strings.START_BUTTON_LABEL
        setOnAction {

            val choicePane = ChoicePane(spots.value, colors.value, repeat.isSelected, callback)

            pane.children.clear()//.removeAll(colors, spots, repeat)
            pane.children.add(choicePane.getPane())
        }
    }
}
