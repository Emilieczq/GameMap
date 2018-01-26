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
public interface DiGraph<Vertex, Edge> {
    public void addVertex(Vertex v);
    public List<Vertex> getVertices();

    public void addEdge(Edge e,  Vertex src, Vertex dest);
    public List<Edge> getEdges(); //Integer => weight

    public List<Vertex> getAdjacentVertices(Vertex v);
    public List<Edge> getOutgoingEdges(Vertex v);
    public Vertex getSource(Edge e);
    public Vertex getDestination(Edge e);
    
    public void nameVertex(String name, Vertex v);
    public Vertex getVertexByName(String name);
    public String getNameOrNullByVertex(Vertex v);
    public List<String> getName();

    public boolean areConnected(Vertex src, Vertex dest);
    public boolean areConnected(String src, String dest);

    public List<Vertex> shortestPath(Vertex src, Vertex dest);
    public List<Vertex> shortestPath(String src, String dest); 
    
}
