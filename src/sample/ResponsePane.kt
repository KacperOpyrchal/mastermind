package sample

import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class ResponsePane {

    fun getPane(response: Pair<Int, Int>, repeat: Boolean): VBox = VBox().apply {
        if(repeat){
            children.addAll(getButton("Punkty - " + (response.first*2 + response.second), "czarne"))
        } else children.addAll(getButton("Czarne - " + response.first, "czarne"), getButton("Biale - " + response.second, "biale"))
    }

    fun getButton(text: String, idStr: String) = Button(text).apply {
        id = idStr
    }

}