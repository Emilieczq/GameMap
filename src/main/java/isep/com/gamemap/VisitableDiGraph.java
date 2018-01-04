/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

/**
 *
 * @param <Vertex>
 * @param <Edge>
 */
public interface VisitableDiGraph<Vertex, Edge> extends DiGraph<Vertex, Edge> {
    void setVerticesVisitor(Visitor<Vertex> vv);
    Visitor<Vertex> getVerticesVisitor();
    void setEdgesVisitor(Visitor<Edge> ev);
    Visitor<Edge> getEdgesVisitor();
}
