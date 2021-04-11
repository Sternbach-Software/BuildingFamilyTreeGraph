package samples;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultGraphType;
import org.jgrapht.graph.builder.GraphBuilder;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import org.jungrapht.samples.util.ControlHelpers;
import org.jungrapht.samples.util.LensControlHelper;
import org.jungrapht.samples.util.TreeLayoutSelector;
import org.jungrapht.visualization.MultiLayerTransformer;
import org.jungrapht.visualization.VisualizationModel;
import org.jungrapht.visualization.VisualizationScrollPane;
import org.jungrapht.visualization.VisualizationViewer;
import org.jungrapht.visualization.control.DefaultGraphMouse;
import org.jungrapht.visualization.control.DefaultLensGraphMouse;
import org.jungrapht.visualization.control.LensGraphMouse;
import org.jungrapht.visualization.decorators.EdgeShape;
import org.jungrapht.visualization.decorators.PickableElementPaintFunction;
import org.jungrapht.visualization.layout.algorithms.StaticLayoutAlgorithm;
import org.jungrapht.visualization.layout.model.LayoutModel;
import org.jungrapht.visualization.selection.MutableSelectedState;
import org.jungrapht.visualization.transform.HyperbolicTransformer;
import org.jungrapht.visualization.transform.LayoutLensSupport;
import org.jungrapht.visualization.transform.LensSupport;
import org.jungrapht.visualization.transform.shape.HyperbolicShapeTransformer;
import org.jungrapht.visualization.transform.shape.ViewLensSupport;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

class WorkingTreeBuilderPanel extends JPanel {
    Graph<Object, Integer> graph;

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<String, Integer> vv;

    /**
     * provides a Hyperbolic lens for the view
     */
    LensSupport<LensGraphMouse> hyperbolicViewSupport;

    LensSupport<LensGraphMouse> hyperbolicLayoutSupport;

    /**
     * create an instance of a simple graph with controls to demo the zoomand hyperbolic features.
     */
    public WorkingTreeBuilderPanel() {
        setLayout(new BorderLayout());
        // create a simple graph for the demo

        GraphBuilder<Object, Integer, Graph<Object, Integer>> b = generateGenealogyGraph();

        graph = b.build();


        Dimension preferredSize = new Dimension(600, 600);

        final VisualizationModel<String, Integer> visualizationModel =
                VisualizationModel.builder(graph)
                        .layoutAlgorithm(new StaticLayoutAlgorithm())
                        .layoutSize(preferredSize)
                        .build();
        final DefaultGraphMouse<String, Integer> graphMouse = new DefaultGraphMouse<>();

        vv =
                VisualizationViewer.builder(visualizationModel)
                        .graphMouse(graphMouse)
                        .viewSize(preferredSize)
                        .build();

        MutableSelectedState<String> ps = vv.getSelectedVertexState();
        MutableSelectedState<Integer> pes = vv.getSelectedEdgeState();
        vv.getRenderContext()
                .setVertexFillPaintFunction(
                        new PickableElementPaintFunction<>(ps, Color.red, Color.yellow));
        vv.getRenderContext().setVertexLabelFunction(Object::toString);
        vv.getRenderContext()
                .setEdgeDrawPaintFunction(new PickableElementPaintFunction<>(pes, Color.black, Color.cyan));

        vv.getRenderContext().setVertexLabelFunction(Object::toString);
        vv.getRenderContext().setEdgeShapeFunction(EdgeShape.line());

        // add a listener for ToolTips
        vv.setVertexToolTipFunction(Object::toString);

        VisualizationScrollPane visualizationScrollPane = new VisualizationScrollPane(vv);
        add(visualizationScrollPane);

        LayoutModel<String> layoutModel = vv.getVisualizationModel().getLayoutModel();
        Dimension d = new Dimension(layoutModel.getWidth(), layoutModel.getHeight());

        hyperbolicViewSupport =
                ViewLensSupport.builder(vv)
                        .lensTransformer(
                                HyperbolicShapeTransformer.builder(d)
                                        .delegate(
                                                vv.getRenderContext().getMultiLayerTransformer().getTransformer(MultiLayerTransformer.Layer.VIEW))
                                        .build())
                        .lensGraphMouse(new DefaultLensGraphMouse())

                        .build();

        hyperbolicLayoutSupport =
                LayoutLensSupport.builder(vv)
                        .lensTransformer(
                                HyperbolicTransformer.builder(d)
                                        .delegate(
                                                vv.getRenderContext()
                                                        .getMultiLayerTransformer()
                                                        .getTransformer(MultiLayerTransformer.Layer.LAYOUT))
                                        .build())
                        .lensGraphMouse(new DefaultLensGraphMouse())

                        .build();

        final JButton hyperView = new JButton("Hyperbolic View");
        hyperView.addActionListener(
                e -> {
                    hyperbolicLayoutSupport.deactivate();
                    hyperbolicViewSupport.activate(true);
                });
        final JButton hyperLayout = new JButton("Hyperbolic Layout");
        hyperLayout.addActionListener(
                e -> {
                    hyperbolicViewSupport.deactivate();
                    hyperbolicLayoutSupport.activate(true);
                });
        final JButton noLens = new JButton("No Lens");
        noLens.addActionListener(
                e -> {
                    hyperbolicLayoutSupport.deactivate();
                    hyperbolicViewSupport.deactivate();
                });
        noLens.setSelected(true);

        ButtonGroup radio = new ButtonGroup();
        radio.add(hyperView);
        radio.add(hyperLayout);
        radio.add(noLens);

        JMenuBar menubar = new JMenuBar();
        visualizationScrollPane.setCorner(menubar);

        Box controls = Box.createHorizontalBox();

        JComponent lensBox =
                LensControlHelper.builder(
                        Map.of(
                                "Hyperbolic View", hyperbolicViewSupport,
                                "Hyperbolic Layout", hyperbolicLayoutSupport))
                        .containerSupplier(Box::createVerticalBox)
                        .build()
                        .container();

        controls.add(ControlHelpers.getZoomControls("Scale", vv));
        controls.add(ControlHelpers.getCenteredContainer("Lens Controls", lensBox));
        JPanel layoutControls = new JPanel(new GridLayout(0, 1));
        layoutControls.add(TreeLayoutSelector.builder(vv).initialSelection(7).build());
        controls.add(ControlHelpers.getCenteredContainer("Layouts", layoutControls));
        add(controls, BorderLayout.SOUTH);
    }

    public GraphBuilder<Object, Integer, Graph<Object, Integer>> generateGenealogyGraph() {
        DefaultGraphType graphType = (new DefaultGraphType.Builder()).directed().allowSelfLoops(false).allowMultipleEdges(true).allowCycles(false).weighted(false).build();
        GraphBuilder<Object, Integer, Graph<Object, Integer>>
                graph =
                GraphTypeBuilder
                        .forGraphType(graphType)
                        .edgeSupplier(
                                SupplierUtil
                                        .createIntegerSupplier()
                        )
                        .buildGraphBuilder();













//        b.addVertex("Grandfather");
        graph.addEdge("Grandfather", "Father 1");
        graph.addEdge("Grandfather", "Father 3");
        graph.addEdge("Grandfather", "Father 2");
        graph.addEdge("Father 1", "C0");
        graph.addEdge("Father 1", "C1");
        graph.addEdge("Father 1", "C2");
        graph.addEdge("Father 1", "C3");
        graph.addEdge("C2", "H0");
        graph.addEdge("C2", "H1");
        graph.addEdge("H1", "H2");
        graph.addEdge("H1", "H3");
        graph.addEdge("H3", "H4");
        graph.addEdge("H3", "H5");
        graph.addEdge("H5", "H6");
        graph.addEdge("H5", "H7");
        graph.addEdge("Father 3", "D0");
        graph.addEdge("Father 3", "D1");
        graph.addEdge("Father 3", "D2");
        graph.addEdge("Father 2", "E0");
        graph.addEdge("Father 2", "E1");
        graph.addEdge("Father 2", "E2");
        graph.addEdge("D0", "F0");
        graph.addEdge("D0", "F1");
        graph.addEdge("D0", "F2");
        graph.addEdge("D1", "G0");
        graph.addEdge("D1", "G1");
        graph.addEdge("D1", "G2");
        graph.addEdge("D1", "G3");
        graph.addEdge("D1", "G4");
        graph.addEdge("D1", "G5");
        graph.addEdge("D1", "G6");
        graph.addEdge("D1", "G7");
        graph.addEdge("Great Grandfather", "Grandfather");
        graph.addEdge("Great Grandfather", "Great Uncle 1");
        graph.addEdge("Great Grandfather", "Great Uncle 2");
        graph.addEdge("Great Grandfather", "Great Uncle 3");











        return graph;
    }
}
