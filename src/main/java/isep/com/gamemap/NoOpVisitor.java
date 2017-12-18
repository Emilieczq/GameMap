/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

/**
 *
 * @author Zhenqi
 */
public class NoOpVisitor<V> implements Visitor<V>{

    @Override
    public boolean visit(V v) {
        return true;
    }
    
}
