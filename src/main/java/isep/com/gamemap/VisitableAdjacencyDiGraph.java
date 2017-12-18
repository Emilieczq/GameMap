/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

/**
 *
 * @author Zhenqi
 */
public class VisitableAdjacencyDiGraph<Vertex, Edge> extends AdjacencyDiGraph<Vertex, Edge> 
    implements VisitableDiGraph<Vertex, Edge>{
    private Visitor<Vertex> verticesVisitor = new NoOpVisitor<Vertex>();
    private Visitor<Edge> edgesVisitor = new NoOpVisitor<Edge>();

    public VisitableAdjacencyDiGraph() {
        
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
    
    public VisitableAdjacencyDiGraph(Visitor<Vertex> vv, Visitor<Edge> ev) {
        verticesVisitor = vv;
        edgesVisitor = ev;
    }
    public Vertex getDestination(Edge e){
        edgesVisitor.visit(e);
        Vertex res = super.getDestination(e);
        verticesVisitor.visit(res);
        return res;
    }
}
