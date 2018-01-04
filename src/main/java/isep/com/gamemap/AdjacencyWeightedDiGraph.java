/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
    
    /**
     * 
     * @param v1
     * @param v2
     * @return 
     */
    public Edge getEdge(Vertex v1, Vertex v2){
       for(Edge e:super.getOutgoingEdges(v1)){
           if(super.getDestination(e).equals(v2)){
               return e;
           }
       }
       throw new RuntimeException("Something wrong");
    }
    
    public List<Vertex> shortestPath(Vertex src, Vertex dest){
        Map<Vertex, Integer> vertexToDistance = new HashMap<>();
        Queue<Vertex> toVisit = new PriorityQueue<>(new MappedComparator((HashMap) vertexToDistance));
        Map<Vertex, Vertex> vertexToPrevious = new HashMap<>();

        if (src == null || dest == null || !areConnected(src, dest)) {
            return null;
        }

        List<Vertex> shortestPath = new ArrayList<>();
        Vertex visiting;

        toVisit.add(src);
        vertexToDistance.put(src, 0);
        vertexToPrevious.put(src,null);

        do {
            visiting = toVisit.remove();
            if (vertexToPrevious.containsKey(visiting)) {
                for (Vertex v : getAdjacentVertices(visiting)) {
                    int newDistance = vertexToDistance.get(visiting)
                            + getWeight(getEdge(v, visiting));
                    if ((!vertexToPrevious.containsKey(v))
                            || (newDistance < vertexToDistance.get(v))) {
                        vertexToDistance.put(v, newDistance);
                        vertexToPrevious.put(v, visiting);
                        toVisit.add(v);
                    }
                }
            }
        } while (!(toVisit.isEmpty() || visiting.equals(dest)));
        if (visiting.equals(dest)) {
            for (Vertex v = dest; v != null; v = vertexToPrevious.get(v)) {
                shortestPath.add(v);
            }
        }

        Collections.reverse(shortestPath); // reverse to get the correct order
        return shortestPath;
    }
    

}
