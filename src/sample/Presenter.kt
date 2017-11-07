package sample

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.util.*


class Presenter {

    private val setupView = SetupView()
    private lateinit var primaryStage: Stage

    fun start(primaryStage: Stage) {
        this.primaryStage = primaryStage

        val scene = Scene(root(), 800.0, 800.0)

        primaryStage.apply {
            title = "Hello"
            setScene(scene)
            show()
        }
    }


    fun initAgain() {
        this.primaryStage = primaryStage

        val scene = Scene(root(), 800.0, 800.0)

        primaryStage.apply {
            title = "Hello"
            setScene(scene)
            show()
        }
    }

    private fun root() : StackPane = StackPane().apply {
        stylesheets.addAll(appResource)
        children.add(startButton())
        id = "pane"
    }

    private fun startButton() : Button = Button().apply {
        id = "repeat"
        text = "START"
        setOnAction { _ ->
            println("Was")
            primaryStage.scene = Scene(setupView.getPane(object: Action {
                override fun invoke() {}

                override fun invoke(data: Data) {
                    primaryStage.scene = Scene(GameBoardsController(data.spotsNumber,
                            data.colorsNumber, data.playerSecret, data.repeat).getPane(), 800.0, 800.0)
                }
            } ), 800.0, 800.0)
        }
    }

    companion object {
        val appResource = this.javaClass.getResource("application.css").toExternalForm()
    }
}