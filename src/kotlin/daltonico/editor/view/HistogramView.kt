package daltonico.editor.view

import daltonico.editor.configs.Configs
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.paint.Color
import tornadofx.*

class HistogramView(val info: IntArray) : View(Configs.lang["histogram"]) {
    override val root = barchart("", CategoryAxis(), NumberAxis()) {
        series("") {
            style {

                barFill = Color.BLUE
            }
            info.forEachIndexed { index, i ->
                this.data.add(XYChart.Data(index.toString(), i))
            }
        }
        isLegendVisible = false


        for (n in lookupAll(".default-color0.chart-bar")) {
            n.style {
                barFill = Color.BLACK
            }
        }
    }
}
