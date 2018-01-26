/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

import java.util.ArrayList;
import java.util.List;

public class VisitableAdjacencyWeightedDiGraphWithHeuristic <Vertex, Edge> 
        extends AdjacencyWeightedDiGraphWithHeuristic <Vertex, Edge>  implements VisitableDiGraph<Vertex, Edge> {

    DistanceHeuristic h;
    private Visitor<Vertex> verticesVisitor = new NoOpVisitor<>();
    private Visitor<Edge> edgesVisitor = new NoOpVisitor<>();

    public VisitableAdjacencyWeightedDiGraphWithHeuristic() {
        super.setHeuristic(h);
    }
    
    public VisitableAdjacencyWeightedDiGraphWithHeuristic(Visitor<Vertex> vv, Visitor<Edge> ev) {
        verticesVisitor = vv;
        edgesVisitor = ev;
    }
    public VisitableAdjacencyWeightedDiGraphWithHeuristic(DistanceHeuristic h) {
            this.h = h;
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
