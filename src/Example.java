import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class Example {
    public static void main(String[] args) {
        Graph<Integer, String> g = new SparseMultigraph<>();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge("Edge-A", 1, 2);
        g.addEdge("Edge-B", 2, 3);
        println(g.getEndpoints("Edge-A"));
        println(g.getOutEdges(2));
        println(g.getInEdges(2));
    }
    public static void println(Object a){
        System.out.println(a);
    }
}
