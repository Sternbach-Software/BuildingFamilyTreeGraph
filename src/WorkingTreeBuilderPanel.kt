import JavaWorkaroundForKotlinNullableErrors.prepareGraphSkeleton
import JavaWorkaroundForKotlinNullableErrors.getBuilder2
import org.jgrapht.Graph
import org.jgrapht.graph.builder.GraphBuilder
import org.jungrapht.samples.util.ControlHelpers
import org.jungrapht.samples.util.TreeLayoutSelector
import org.jungrapht.visualization.VisualizationModel
import org.jungrapht.visualization.VisualizationScrollPane
import org.jungrapht.visualization.VisualizationViewer
import org.jungrapht.visualization.control.DefaultGraphMouse
import org.jungrapht.visualization.decorators.EdgeShape
import org.jungrapht.visualization.decorators.PickableElementPaintFunction
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.Box
import javax.swing.JMenuBar
import javax.swing.JPanel

@Suppress("MemberVisibilityCanBePrivate")
internal class WorkingTreeBuilderPanel : JPanel() {

    var greatGreatGrandparentCounter = 0
    var greatGrandparentCounter = 0
    var grandparentCounter = 0
    var parentCounter = 0

    var greatGreatGrandUnlceAuntCounter = 0
    var greatGrandUncleAuntCounter = 0
    var greatUncleAuntCounter = 0
    var uncleAuntCounter = 0

    var oneCousOneRemovCounter = 0
    var oneCousTwoRemovCounter = 0
    var oneCousThreeRemovCounter = 0
    var twoCousOneRemovCounter = 0
    var twoCousTwoRemovCounter = 0


    val secondCousins: MutableMap<String, MutableList<String>> = mutableMapOf()
    val fourthCousins: MutableMap<String, MutableList<String>> = mutableMapOf()
    val firstCousins: MutableMap<String, MutableList<String>> = mutableMapOf()
    val thirdCousins: MutableMap<String, MutableList<String>> = mutableMapOf()

    var graph: org.jgrapht.Graph<Any, Int>

    /**
     * the visual component and renderer for the graph
     */
    var vv: VisualizationViewer<String?, Int?>?

    /**
     * provides a Hyperbolic lens for the view
     */
//    var hyperbolicViewSupport: LensSupport<LensGraphMouse?>?
//    var hyperbolicLayoutSupport: LensSupport<LensGraphMouse?>?
/*
    private fun generateGenealogyGraph(): GraphBuilder<Any, Int, Graph<Any, Int>?>? {
        val graphType: DefaultGraphType =
            DefaultGraphType.Builder().directed().allowSelfLoops(false).allowMultipleEdges(true).allowCycles(false)
                .weighted(false).build()
        val forGraphType = GraphTypeBuilder
            .forGraphType<Any, Any>(graphType)
        val graph: org.jgrapht.graph.builder.GraphBuilder<Any?, Int?, org.jgrapht.Graph<Any?, Int?>?> =
            forGraphType
                .edgeSupplier(
                    SupplierUtil
                        .createIntegerSupplier()
                )
                .buildGraphBuilder()


//        b.addVertex("Grandfather");
        graph.addEdge("Grandfather", "Father 1")
        graph.addEdge("Grandfather", "Father 3")
        graph.addEdge("Grandfather", "Father 2")
        graph.addEdge("Father 1", "C0")
        graph.addEdge("Father 1", "C1")
        graph.addEdge("Father 1", "C2")
        graph.addEdge("Father 1", "C3")
        graph.addEdge("C2", "H0")
        graph.addEdge("C2", "H1")
        graph.addEdge("H1", "H2")
        graph.addEdge("H1", "H3")
        graph.addEdge("H3", "H4")
        graph.addEdge("H3", "H5")
        graph.addEdge("H5", "H6")
        graph.addEdge("H5", "H7")
        graph.addEdge("Father 3", "D0")
        graph.addEdge("Father 3", "D1")
        graph.addEdge("Father 3", "D2")
        graph.addEdge("Father 2", "E0")
        graph.addEdge("Father 2", "E1")
        graph.addEdge("Father 2", "E2")
        graph.addEdge("D0", "F0")
        graph.addEdge("D0", "F1")
        graph.addEdge("D0", "F2")
        graph.addEdge("D1", "G0")
        graph.addEdge("D1", "G1")
        graph.addEdge("D1", "G2")
        graph.addEdge("D1", "G3")
        graph.addEdge("D1", "G4")
        graph.addEdge("D1", "G5")
        graph.addEdge("D1", "G6")
        graph.addEdge("D1", "G7")
        graph.addEdge("Great Grandfather", "Grandfather")
        graph.addEdge("Great Grandfather", "Great Uncle 1")
        graph.addEdge("Great Grandfather", "Great Uncle 2")
        graph.addEdge("Great Grandfather", "Great Uncle 3")
        return graph
    }
*/

    /**
     * create an instance of a simple graph with controls to demo the zoomand hyperbolic features.
     */

    init {
        layout = BorderLayout()
        // create a graph
        val graphSkeleton: GraphBuilder<Any, Int, Graph<Any, Int>?>? = prepareGraphSkeleton()
        val populatedGraph = populateGraph(graphSkeleton)

        graph = populatedGraph!!.build()!!
        val preferredSize = Dimension(600, 600)
        val visualizationModel: VisualizationModel<String?, Int?> =
            JavaWorkaroundForKotlinNullableErrors.getBuilder1(graph, preferredSize)
        val graphMouse = DefaultGraphMouse<String?, Int?>()
        vv = getBuilder2(visualizationModel, graphMouse, preferredSize)
        val ps = vv!!.selectedVertexState
        val pes = vv!!.selectedEdgeState
        vv!!.renderContext
            .setVertexFillPaintFunction(
                PickableElementPaintFunction(ps, Color.red, Color.yellow)
            )
        vv!!.renderContext
            .setVertexLabelFunction { obj: String? -> obj.toString() }
        vv!!.renderContext
            .setEdgeDrawPaintFunction(PickableElementPaintFunction(pes, Color.black, Color.cyan))
        vv!!.renderContext
            .setVertexLabelFunction { obj: String? -> obj.toString() }
        vv!!.renderContext.edgeShapeFunction = EdgeShape.line<String?, Int?>()

        // add a listener for ToolTips
        vv!!.setVertexToolTipFunction { obj: String? -> obj.toString() }
        val visualizationScrollPane = VisualizationScrollPane(vv)
        add(visualizationScrollPane)
        val layoutModel = vv!!.visualizationModel.layoutModel
        val d = Dimension(layoutModel!!.width, layoutModel.height)
        /*hyperbolicViewSupport =
            ViewLensSupport.builder<String?, Int?, LensGraphMouse?>(
                vv
            )
                .lensTransformer(
                    HyperbolicShapeTransformer.builder(d)
                        .delegate(
                            vv!!.renderContext.multiLayerTransformer
                                .getTransformer(MultiLayerTransformer.Layer.VIEW)
                        )
                        .build()
                )
                .lensGraphMouse(DefaultLensGraphMouse<Any?, Any?>())
                .build()
        hyperbolicLayoutSupport =
            LayoutLensSupport.builder<String?, Int?, LensGraphMouse?>(vv)
                .lensTransformer(
                    HyperbolicTransformer.builder<HyperbolicTransformer?>(
                        d
                    )
                        .delegate(
                            vv!!.renderContext
                                .multiLayerTransformer
                                .getTransformer(MultiLayerTransformer.Layer.LAYOUT)
                        )
                        .build()
                )
                .lensGraphMouse(DefaultLensGraphMouse<Any?, Any?>())
                .build()
        val hyperView = JButton("Hyperbolic View")
        hyperView.addActionListener { e: ActionEvent? ->
            hyperbolicLayoutSupport!!.deactivate()
            hyperbolicViewSupport!!.activate(true)
        }
        val hyperLayout = JButton("Hyperbolic Layout")
        hyperLayout.addActionListener { e: ActionEvent? ->
            hyperbolicViewSupport!!.deactivate()
            hyperbolicLayoutSupport!!.activate(true)
        }
        val noLens = JButton("No Lens")
        noLens.addActionListener { e: ActionEvent? ->
            hyperbolicLayoutSupport!!.deactivate()
            hyperbolicViewSupport!!.deactivate()
        }
        noLens.isSelected = true
        val radio = ButtonGroup()
        radio.add(hyperView)
        radio.add(hyperLayout)
        radio.add(noLens)*/
        val menubar = JMenuBar()
        visualizationScrollPane.corner = menubar
        val controls = Box.createHorizontalBox()
        /*val lensBox = LensControlHelper.builder(
            mapOf(
                "Hyperbolic View" to hyperbolicViewSupport,
                "Hyperbolic Layout" to hyperbolicLayoutSupport
            )
        )
            .containerSupplier { Box.createVerticalBox() }
            .build()
            .container<JComponent?>()*/
        controls!!.add(ControlHelpers.getZoomControls("Scale", vv))
//        controls.add(ControlHelpers.getCenteredContainer("Lens Controls", lensBox))
        val layoutControls = JPanel(GridLayout(0, 1))
        layoutControls.add(TreeLayoutSelector.builder<Any?, Any?>(vv).initialSelection(7).build())
        controls.add(ControlHelpers.getCenteredContainer("Layouts", layoutControls))
        add(controls, BorderLayout.SOUTH)
    }

    private fun populateGraph(a: GraphBuilder<Any, Int, Graph<Any, Int>?>?): GraphBuilder<Any, Int, Graph<Any, Int>?>? {
        var graph = a
        graph?.addEdge("Harold",                                              "Totty")
        graph?.addEdge("Minnie",                                              "Harold")
        graph?.addEdge("Leah",                                                "Minnie")
        graph?.addEdge("Yosef Frei",                                          "Leah")
        graph?.addEdge("? Frei",                                              "Yosef Frei")
        graph?.addEdge("Simcha Halevi Frei",                                  "? Frei")
        graph?.addEdge("Mordechai Halevi Frei",                               "Simcha Halevi Frei")
        graph?.addEdge("Drezel Frey Falk",                                    "Mordechai Halevi Frei")
        graph?.addEdge("Rabbi Nosson Nata Landau Falk ABD Dobromil",          "Drezel Frey Falk")
        graph?.addEdge("? Segal",                                             "Drezel Frey Falk")
        graph?.addEdge("Rabbi Moshe Meir Halevi Segal ABD Dobromil",          "? Segal")
        graph?.addEdge("Rabbi Ya’akov Yehoshua Falk Pnei Yehoshua",           "Rabbi Nosson Nata Landau Falk ABD Dobromil")
        graph?.addEdge("Taube Falk",                                          "Rabbi Nosson Nata Landau Falk ABD Dobromil")
        graph?.addEdge("Miriam Falk",                                         "Rabbi Ya’akov Yehoshua Falk Pnei Yehoshua")


/*        graph?.addEdge("Grandmother #1" ,"Father #1")
        graph?.addEdge("Grandmother #1", "Father #2")
        graph?.addEdge("Grandmother #1", "Childless #3")
        graph?.addEdge("Father #1", "Jimmy")
        graph?.addEdge("Father #2", "Sally")
        graph?.addEdge("Jimmy","Son #1")
        graph?.addEdge("Jimmy","Son #2")
        graph?.addEdge("Jimmy","Son #3")*/
        /*b?.addEdge("Grandfather", "Father 1")
        b?.addEdge("Grandfather", "Father 2")
        b?.addEdge("Grandfather", "Father 3")
        b?.addEdge("Father 1", "C0")
        b?.addEdge("Father 1", "C1")
        b?.addEdge("Father 1", "C2")
        b?.addEdge("Father 1", "C3")
        b?.addEdge("C2", "H0")
        b?.addEdge("C2", "H1")
        b?.addEdge("H1", "H2")
        b?.addEdge("H1", "H3")
        b?.addEdge("H3", "H4")
        b?.addEdge("H3", "H5")
        b?.addEdge("H5", "H6")
        b?.addEdge("H5", "H7")
        b?.addEdge("Father 2", "E0")
        b?.addEdge("Father 2", "E1")
        b?.addEdge("Father 2", "E2")
        b?.addEdge("Father 3", "D0")
        b?.addEdge("Father 3", "D1")
        b?.addEdge("Father 3", "D2")
        b?.addEdge("D0", "F0")
        b?.addEdge("D0", "F1")
        b?.addEdge("D0", "F2")
        b?.addEdge("D1", "G0")
        b?.addEdge("D1", "G1")
        b?.addEdge("D1", "G2")
        b?.addEdge("D1", "G3")
        b?.addEdge("D1", "G4")
        b?.addEdge("D1", "G5")
        b?.addEdge("D1", "G6")
        b?.addEdge("D1", "G7")*/
//        b?.addEdge("Great Grandfather", "Grandfather")
//        b?.addEdge("Great Grandfather", "Great Uncle 1")
//        b?.addEdge("Great Grandfather", "Great Uncle 2")
//        b?.addEdge("Great Grandfather", "Great Uncle 3")
/*        addThirdCousin("me", "3rd cousin of Eve", b)
        addThirdCousin("Eve","3rd cousin of Eve", b)
        addSecondCousin("me","Eve",b)*/

        /*addSecondCousin("me", "Eve Pardo", b)
        addSecondCousin("me", "Alan Statman", b)
        addSecondCousin("me", "Carla Alpert", b)
        addSecondCousin("me", "Matthew Smith", b)
        addSecondCousin("me", "Eugenija Nez", b)
        addSecondCousin("me", "Talina Fershter", b)
        addSecondCousin("me", "Vlada Liverant", b)
        addThirdCousin("Eve Pardo", "Alan Statman", b)
        addThirdCousin("Eve Pardo", "Carla Alpert", b)
        addThirdCousin("Eve Pardo", "Matthew Smith", b)
        addThirdCousin("Eve Pardo", "Talina Fershter", b)
        addFourthCousin("Eve Pardo", "Eugenija Nez", b)
        addFourthCousin("Eve Pardo", "Vlada Liverant", b)
        addThirdCousin("Alan Statman", "Carla Alpert", b)
        addFourthCousin("Alan Statman", "Serge Vassenkov", b)
        */return graph
    }

    fun addFirstCousin(
        origin: String,
        destination: String,
        graph: GraphBuilder<Any?, Int?, Graph<Any?, Int?>?>
    ) {
        val uniqueRelationShipIdentifier = destination.hashCode() * origin.hashCode()
        firstCousins[origin]?.plusAssign(destination)
        graph.addEdge(origin, "Parent " + parentCounter++)
        graph.addEdge(
            "Parent " + parentCounter,
            "Uncle/Aunt " + uncleAuntCounter++
        )
        graph.addEdge("Uncle/Aunt " + uncleAuntCounter, destination)
    }

    fun addSecondCousin(
        origin: String,
        destination: String,
        graph: GraphBuilder<Any, Int, Graph<Any, Int>?>?
    ) {
        val uniqueRelationShipIdentifier = destination.hashCode() * origin.hashCode()
        secondCousins[origin]?.plusAssign(destination)
        graph?.addEdge(origin, "Parent " + parentCounter++)
        graph?.addEdge(
            "Parent " + parentCounter,
            "Grandparent " + grandparentCounter++
        )
        graph?.addEdge(
            "Grandparent " + grandparentCounter,
            "Great Uncle/Aunt " + greatUncleAuntCounter++
        )
        graph?.addEdge(
            "Great Uncle/Aunt " + greatUncleAuntCounter,
            "1Cous1Remov " + oneCousOneRemovCounter++
        )
        graph?.addEdge("1Cous1Remov " + oneCousOneRemovCounter, destination)
    }

    fun addThirdCousin(
        origin: String,
        destination: String,
        graph: GraphBuilder<Any, Int, Graph<Any, Int>?>?
    ) {
        thirdCousins[origin]?.plusAssign(destination) // thirdCousins is MutableMap<String(origin person), MutableList<String(cousin)>>
        graph?.addEdge(origin, "Parent ${parentCounter++}") // e.g. Parent 5
        graph?.addEdge(
            "Parent $parentCounter",
            "Grandparent ${grandparentCounter++}"
        )
        graph?.addEdge("Grandparent $grandparentCounter", "Gr.Grandparent ${greatGrandparentCounter++}")
        graph?.addEdge(
            "Gr.Grandparent $greatGrandparentCounter",
            "Gr.Grand Uncle/Aunt ${greatGrandUncleAuntCounter++}"
        )
        graph?.addEdge(
            "Gr.Grand Uncle/Aunt $greatGrandUncleAuntCounter",
            "1Cous2Remov ${oneCousTwoRemovCounter++}"
        )
        graph?.addEdge("1Cous2Remov $oneCousTwoRemovCounter", "2Cous1Remov ${twoCousOneRemovCounter++}")
        graph?.addEdge("2Cous1Remov $twoCousOneRemovCounter", destination)
    }

    fun addFourthCousin(
        origin: String,
        destination: String,
        graph: GraphBuilder<Any, Int, Graph<Any, Int>?>?
    ) {
        val uniqueRelationShipIdentifier = 90
        graph?.addEdge(origin, "Parent $uniqueRelationShipIdentifier")
        graph?.addEdge("Parent $uniqueRelationShipIdentifier", "Grandparent $uniqueRelationShipIdentifier")
        graph?.addEdge("Grandparent $uniqueRelationShipIdentifier", "Gr.Grandparent $uniqueRelationShipIdentifier")
        graph?.addEdge(
            "Gr.Grandparent $uniqueRelationShipIdentifier",
            "2Gr.Grandparent $uniqueRelationShipIdentifier"
        )
        graph?.addEdge(
            "2Gr.Grandparent $uniqueRelationShipIdentifier",
            "2Gr.Grand Uncle/Aunt $uniqueRelationShipIdentifier"
        )
        graph?.addEdge(
            "2Gr.Grand Uncle/Aunt $uniqueRelationShipIdentifier",
            "1Cous3Remov $uniqueRelationShipIdentifier"
        )
        graph?.addEdge("1Cous3Remov $uniqueRelationShipIdentifier", "2Cous2Remov $uniqueRelationShipIdentifier")
        graph?.addEdge("2Cous2Remov $uniqueRelationShipIdentifier", "3Cous1Remov $uniqueRelationShipIdentifier")
        graph?.addEdge("3Cous1Remov $uniqueRelationShipIdentifier", destination)
    }

}