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

/**
 *
 * @author Zhenqi
 */
public class AdjacencyWeightedDiGraphWithHeuristic<Vertex, Edge> extends AdjacencyWeightedDiGraph<Vertex, Edge> implements WeightedDiGraph<Vertex, Edge> {

    DistanceHeuristic h;
    AdjacencyWeightedDiGraphWithHeuristic(){
    }
    public void setHeuristic(DistanceHeuristic h){
        this.h=h;
    }
    @Override
    public List<Vertex> shortestPath(Vertex src, Vertex dest) {
        Map<Vertex, Integer> vertexToDistance = new HashMap<>();
        Queue<Vertex> toVisit = new PriorityQueue<>(new MappedComparator((HashMap) vertexToDistance));
        Map<Vertex, Vertex> vertexToPrevious = new HashMap<>();

        if (src == null || dest == null) {
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
                    int newDistance = vertexToDistance.get(visiting)-h.distance(visiting, dest)
                            + getWeight(getEdge(v, visiting)) + h.distance(v,dest);
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
