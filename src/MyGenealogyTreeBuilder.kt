import WorkingTreeBuilderPanel
import javax.swing.JFrame
import javax.swing.WindowConstants

object MyGenealogyTreeBuilder {
    @JvmStatic
    fun main(args: Array<String>) {
        val f = JFrame()
        f.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        f.contentPane.add(WorkingTreeBuilderPanel())
        f.pack()
        f.isVisible = true
    }
}