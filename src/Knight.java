import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knight extends Soldier implements AvailableSquaresToGoTo {
    public Knight (Square square, SOLDIERS_NAMES soldierName) {
        super(square,soldierName);
    }

    public List<Square> getAvailableSquaresToGoTo () {
        List<Square> squares = new ArrayList<>();
        squares.add(this.getTwoUp_OneRight());

        squares.add(this.getTwoUp_OneLeft());

        squares.add(this.getTwoRight_OneUp());

        squares.add(this.getTwoRight_OneDown());

        squares.add(this.getTwoDown_OneRight());


        squares.add(this.getTwoDown_OneLeft());

        squares.add(this.getTwoLeft_OneDown());
        squares.add(this.getTwoLeft_OneUp());
        squares.removeAll(Collections.singleton(null));
        super.removeOpponentKingSquareFromAvailableSquares(squares);
        return squares;
    }

    private Square check_Square (int row, int column) {
        Square result = null;
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            if (currentSoldiers[row][column] == null) {
                result = new Square(row, column);
            } else {
                result = super.canEatSoldier(new Square(row, column), super.getSoldierColor());
            }
        }
        return result;
    }

    private Square getTwoUp_OneRight () {
        Square result = null;
        int row = super.getSquare().getRow() - 2;
        int column = super.getSquare().getColumn() + 1;
        result = check_Square(row,column);
        return result;
    }

    private Square getTwoUp_OneLeft () {
        Square result = null;
        int row = super.getSquare().getRow() - 2;
        int column = super.getSquare().getColumn() - 1;
        result = check_Square(row,column);
        return result;
    }
    private Square getTwoRight_OneUp () {
        Square result = null;
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() + 2;
        result = check_Square(row,column);
        return result;
    }

    private Square getTwoRight_OneDown () {
        Square result = null;
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() + 2;
        result = check_Square(row,column);
        return result;
    }
    private Square getTwoDown_OneRight () {
        Square result = null;
        int row = super.getSquare().getRow() + 2;
        int column = super.getSquare().getColumn() + 1;
        result = check_Square(row,column);
        return result;
    }

    private Square getTwoDown_OneLeft () {
        Square result = null;
        int row = super.getSquare().getRow() + 2;
        int column = super.getSquare().getColumn() - 1;
        result = check_Square(row,column);
        return result;
    }
    private Square getTwoLeft_OneDown() {
        Square result = null;
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() - 2;
        result = check_Square(row,column);
        return result;
    }

    private Square getTwoLeft_OneUp () {
        Square result = null;
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() - 2;
        result = check_Square(row,column);
        return result;
    }




}
