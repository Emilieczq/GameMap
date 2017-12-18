/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;


public interface WeightedDiGraph<Vertex, Edge> extends DiGraph<Vertex, Edge>{
    public void addEdge(Edge e, Vertex src, Vertex dest, int weigth);
    public int getWeight(Edge e);
    

}


