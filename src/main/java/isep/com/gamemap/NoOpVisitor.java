package isep.com.gamemap;

/**
 *
 * @param <V>
 */
public class NoOpVisitor<V> implements Visitor<V>{

    @Override
    public boolean visit(V v) {
        return true;
    }
    
}
