package samples;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultGraphType;
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

public class MyGenealogyTreeBuilder {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new WorkingTreeBuilderPanel());
        f.pack();
        f.setVisible(true);
    }
}


