package isep.com.gamemap;

/**
 *
 */
public interface DistanceHeuristic<SquareCell>{
    public int distance(SquareCell c0, SquareCell c1);
}
