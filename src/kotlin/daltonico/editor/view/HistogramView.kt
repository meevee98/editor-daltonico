package daltonico.editor.view

import daltonico.editor.configs.Configs
import javafx.scene.chart.Axis
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.paint.Color
import tornadofx.*

class HistogramView(val info: IntArray) : View(Configs.lang["histogram"]) {
    private val yAxys = NumberAxis().also {
        it.tickUnit = 10000.0
    }

    override val root = barchart("", CategoryAxis(), yAxys) {
        series("") {
            info.forEachIndexed { index, i ->
                this.data.add(XYChart.Data(index.toString(), i))
            }
        }
        isLegendVisible = false
        barGap = 0.0
        categoryGap = 0.0

        for (n in lookupAll(".default-color0.chart-bar")) {
            n.style {
                barFill = Color.BLACK
            }
        }
    }
}
