import org.antlr.v4.runtime.misc.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultGraphType;
import org.jgrapht.graph.builder.GraphBuilder;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import org.jungrapht.visualization.VisualizationModel;
import org.jungrapht.visualization.VisualizationViewer;
import org.jungrapht.visualization.control.DefaultGraphMouse;
import org.jungrapht.visualization.layout.algorithms.StaticLayoutAlgorithm;

import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

enum ParentGender {
    FATHER,
    Mother
}

enum TottySide {
    STERNBACH,
    ROTHSTEIN
}

public class JavaWorkaroundForKotlinNullableErrors {



    @NotNull
    public static VisualizationModel<String, Integer> getBuilder1(@NotNull org.jgrapht.Graph<Object, Integer> graph, @NotNull Dimension preferredSize) {

        return
                VisualizationModel.builder(graph)
                        .layoutAlgorithm(new StaticLayoutAlgorithm())
                        .layoutSize(preferredSize)
                        .build();
    }

    public static org.jungrapht.visualization.VisualizationViewer<String, Integer> getBuilder2(VisualizationModel<String, Integer> visualizationModel, DefaultGraphMouse<String, Integer> graphMouse, Dimension preferredSize) {
        return VisualizationViewer.builder(visualizationModel)
                .graphMouse(graphMouse)
                .viewSize(preferredSize)
                .build();
    }

    public static GraphBuilder<Object, Integer, Graph<Object, Integer>> prepareGraphSkeleton() {
        DefaultGraphType graphType = (new DefaultGraphType.Builder()).directed().allowSelfLoops(false).allowMultipleEdges(true).allowCycles(false).weighted(false).build();
        //        b.addVertex("Grandfather");
/*        graph.addEdge("Grandparent", "ParentGender 1");
        graph.addEdge("Grandparent", "ParentGender 2");
        graph.addEdge("Grandparent", "ParentGender 3");
        graph.addEdge("ParentGender 1", "C0");
        graph.addEdge("ParentGender 1", "C1");
        graph.addEdge("ParentGender 1", "C2");
        graph.addEdge("ParentGender 1", "C3");
        graph.addEdge("C2", "H0");
        graph.addEdge("C2", "H1");
        graph.addEdge("H1", "H2");
        graph.addEdge("H1", "H3");
        graph.addEdge("H3", "H4");
        graph.addEdge("H3", "H5");
        graph.addEdge("H5", "H6");
        graph.addEdge("H5", "H7");
        graph.addEdge("ParentGender 2", "E0");
        graph.addEdge("ParentGender 2", "E1");
        graph.addEdge("ParentGender 2", "E2");
        graph.addEdge("ParentGender 3", "D0");
        graph.addEdge("ParentGender 3", "D1");
        graph.addEdge("ParentGender 3", "D2");
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
        graph.addEdge("Great Grandparent", "Grandparent");
        graph.addEdge("Great Grandparent", "Great Uncle/Aunt 1");
        graph.addEdge("Great Grandparent", "Great Uncle 2");
        graph.addEdge("Great Grandparent", "Great Uncle 3");*/
//        addThirdCousin("D2","H111",graph);
//        addThirdCousin("H111","D1",graph);
//        addThirdCousin("C35","D1",graph);
        return GraphTypeBuilder
                .forGraphType(graphType)
                .edgeSupplier(
                        SupplierUtil
                                .createIntegerSupplier()
                )
                .buildGraphBuilder();
    }

}
