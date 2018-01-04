/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep.com.gamemap;

/**
 *
 */
public interface Visitor<V> {
    boolean visit(V v);
}
