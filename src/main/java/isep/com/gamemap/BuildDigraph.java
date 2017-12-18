/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

public class BuildDigraph {
    private static boolean isInGrid(int[][] grid, SquareCell c) {
        return (c.r >=0) && (c.r < grid.length) && 
                (c.c>=0) && (c.c< grid[c.r].length); 
    }
    
    private static VisitableAdjacencyWeightedDiGraph<SquareCell, Integer>
         makeCostGraph(int[][] costGrid) {
             
         }
}
}
