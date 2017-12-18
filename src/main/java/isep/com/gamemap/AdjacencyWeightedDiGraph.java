/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

import java.util.HashMap;
import java.util.Map;

public class AdjacencyWeightedDiGraph<Vertex, Edge> extends AdjacencyDiGraph<Vertex, Edge> implements WeightedDiGraph<Vertex, Edge> {
    private Map<Edge, Integer> edgeToWeight= new HashMap<Edge, Integer>();

    public void addEdge(Edge e, Vertex src, Vertex dest) {
        addEdge(e,src,dest, 1);
    }
    @Override
    public void addEdge(Edge e, Vertex src, Vertex dest, int weigth) {
        super.addEdge(e, src, dest);
        edgeToWeight.put(e, weigth);
    }

    @Override
    public int getWeight(Edge e) {
        return edgeToWeight.get(e);
    }
    
    

}
