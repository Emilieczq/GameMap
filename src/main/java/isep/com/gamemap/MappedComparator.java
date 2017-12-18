/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author Zhenqi
 */
public class MappedComparator<Vertex> implements Comparator<Vertex>{

    private HashMap<Vertex, Integer> object = new HashMap<>();
    
    MappedComparator(HashMap<Vertex, Integer> object){
        this.object = object;
    }
    
    @Override
    public int compare(Vertex v1, Vertex v2) {
        return object.get(v1).compareTo(object.get(v2));
    }
    
}
