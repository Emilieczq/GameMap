package isep.com.gamemap;

/**
 *
 * @param <Vertex>
 * @param <Edge>
 */
public class VisitableAdjacencyDiGraph<Vertex, Edge> extends AdjacencyDiGraph<Vertex, Edge>
        implements VisitableDiGraph<Vertex, Edge> {

    private Visitor<Vertex> verticesVisitor = new NoOpVisitor<>();
    private Visitor<Edge> edgesVisitor = new NoOpVisitor<>();

    public VisitableAdjacencyDiGraph() {

    }

    public VisitableAdjacencyDiGraph(Visitor<Vertex> vv, Visitor<Edge> ev) {
        verticesVisitor = vv;
        edgesVisitor = ev;
    }

    @Override
    public void setVerticesVisitor(Visitor<Vertex> vv) {
        verticesVisitor = vv;
    }

    @Override
    public Visitor<Vertex> getVerticesVisitor() {
        return verticesVisitor;
    }

    @Override
    public void setEdgesVisitor(Visitor<Edge> ev) {
        edgesVisitor = ev;
    }

    @Override
    public Visitor<Edge> getEdgesVisitor() {
        return edgesVisitor;
    }

    @Override
    public Vertex getDestination(Edge e) {
        edgesVisitor.visit(e);
        Vertex res = super.getDestination(e);
        verticesVisitor.visit(res);
        return res;
    }
}
