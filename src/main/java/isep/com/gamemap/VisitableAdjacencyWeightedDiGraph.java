/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

import java.util.List;

/**
 *
 * @author Zhenqi
 */
public class VisitableAdjacencyWeightedDiGraph<Vertex, Edge> extends AdjacencyWeightedDiGraph<Vertex, Edge>
        implements VisitableDiGraph<Vertex, Edge> {

    private Visitor<Vertex> verticesVisitor = new NoOpVisitor<Vertex>();
    private Visitor<Edge> edgesVisitor = new NoOpVisitor<Edge>();

    public VisitableAdjacencyWeightedDiGraph() {
    }
    
    public void setVerticesVisitor(Visitor<Vertex> vv) {
        verticesVisitor = vv;
    }

    public Visitor<Vertex> getVerticesVisitor() {
        return verticesVisitor;
    }

    public void setEdgesVisitor(Visitor<Edge> ev) {
        edgesVisitor = ev;
    }

    public Visitor<Edge> getEdgesVisitor() {
        return edgesVisitor;
    }

    public VisitableAdjacencyWeightedDiGraph(Visitor<Vertex> vv, Visitor<Edge> ev) {
        verticesVisitor = vv;
        edgesVisitor = ev;
    }

    public Vertex getDestination(Edge e) {
        edgesVisitor.visit(e);
        Vertex res = super.getDestination(e);
        verticesVisitor.visit(res);
        return res;
    }

}
