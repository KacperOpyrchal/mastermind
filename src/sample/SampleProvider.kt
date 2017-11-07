package sample

import javafx.scene.control.Label

class SampleProvider {
    companion object {
        fun getDefaultLabel(content: String): Label {
            val defaultLabel = Label(content)
            defaultLabel.id = "story"
            return defaultLabel
        }
    }
}