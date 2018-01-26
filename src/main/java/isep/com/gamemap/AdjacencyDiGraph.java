/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Zhenqi
 */
public class AdjacencyDiGraph<Vertex, Edge> implements DiGraph<Vertex, Edge> {

    protected Set<Vertex> vertices = new HashSet<>();
    protected Set<Edge> edges = new HashSet<>(); // Integer =>weight
    protected Map<Vertex, List<Edge>> vertexToEdges = new HashMap<>();
    private Map<Edge, Vertex> edgeToSrc = new HashMap<>();
    private Map<Edge, Vertex> edgeToDest = new HashMap<>();
    private Map<String, Vertex> nameToVertex = new HashMap<>();

    public AdjacencyDiGraph() {
    }

    @Override
    public void addVertex(Vertex v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            vertexToEdges.put(v, new ArrayList<Edge>());
        }
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public void addEdge(Edge e, Vertex src, Vertex dest) {
        addVertex(src);
        addVertex(dest);
        edges.add(e);
        edgeToSrc.put(e, src);
        edgeToDest.put(e, dest);
        vertexToEdges.get(src).add(e);
    }

    @Override
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    @Override
    public List<Vertex> getAdjacentVertices(Vertex src) {
        List<Vertex> res = new ArrayList<>();
        for (Edge e : vertexToEdges.get(src)) {
            res.add(edgeToDest.get(e));
        }
        return res;
    }

    @Override
    public void nameVertex(String name, Vertex v) {
        nameToVertex.put(name, v);
    }

    @Override
    public Vertex getVertexByName(String name) {
        return nameToVertex.get(name);
    }

    // should use java.util.Optional
    @Override
    public String getNameOrNullByVertex(Vertex v) {
        for (Map.Entry<String, Vertex> e : nameToVertex.entrySet()) {
            if (e.getValue().equals(v)) {
                String value = e.getKey();
                return e.getKey();
            }
        }
        return null;
    }

    @Override
    public List<Edge> getOutgoingEdges(Vertex v) {
        return Collections.unmodifiableList(vertexToEdges.get(v));
    }

    @Override
    public Vertex getSource(Edge e) {
        return edgeToSrc.get(e);
    }

    @Override
    public Vertex getDestination(Edge e) {
        return edgeToDest.get(e);
    }

    @Override
    public List<String> getName() {
        return new ArrayList<String>(nameToVertex.keySet());
    }

    @Override
    public boolean areConnected(Vertex src, Vertex dest) {
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> toVisit = new LinkedList<>();
        toVisit.add(src);
        while (!toVisit.isEmpty()) {
            Vertex visiting = toVisit.remove();
            if (visiting.equals(dest)) {
                return true;
            }
            if (visited.add(visiting)) {
                List<Vertex> list = getAdjacentVertices(visiting);
                for (Vertex i : list) {
                    if (!visited.contains(i)) {
                        toVisit.add(i);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean areConnected(String src, String dest) {
        return areConnected(getVertexByName(src), getVertexByName(dest));
    }

    @Override
    public List<Vertex> shortestPath(Vertex src, Vertex dest) { //returns the path with the minimal sum of the weights
        if (src == null || dest == null) {
            return null;
        }

        List<Vertex> shortestPath = new ArrayList<>();

        Queue<Vertex> toVisit = new LinkedList<>();
        Map<Vertex, Vertex> vertexToPrevious = new HashMap<>();

        toVisit.add(src);
        vertexToPrevious.put(src, null);

        Vertex visiting = src;

        while (!(toVisit.isEmpty() || visiting.equals(dest))) {
            visiting = toVisit.remove();
            for (Vertex vertex : getAdjacentVertices(visiting)) {
                if (!vertexToPrevious.containsKey(vertex)) {
                    toVisit.add(vertex);
                    vertexToPrevious.put(vertex, visiting);

                }
            }
        }

        // Get the path from the end to the start
        if (visiting.equals(dest)) {
            for (Vertex v = dest; v != null; v = vertexToPrevious.get(v)) {
                shortestPath.add(v);
            }
        }

        Collections.reverse(shortestPath); // reverse to get the correct order
        return shortestPath;

    }
    
    @Override
    public List<Vertex> shortestPath(String src, String dest) {
        return shortestPath(getVertexByName(src), getVertexByName(dest));
    }

}
