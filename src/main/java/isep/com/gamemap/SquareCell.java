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
public class SquareCell {

    public final int c;
    public final int r;

    public SquareCell(int row, int column) {
        r = row;
        c = column;
    }

    @Override
    public int hashCode() {
        return c ^ r;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof SquareCell)) {
            return false;
        }
        SquareCell otherCell = (SquareCell) other;
        return r == otherCell.r && c == otherCell.c;
    }

    @Override
    public String toString() {
        return "[" + r + "," + c + "]";
    }

    public int manhattanDistanceTo(SquareCell other) {
        return Math.abs(r - other.r) + Math.abs(c - other.c);
    }
}
