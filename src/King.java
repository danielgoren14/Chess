import java.util.ArrayList;
import java.util.List;

public class King extends Soldier implements AvailableSquaresToGoTo {
    private Color_Black_Or_White color;
    public static final String CURRENT_SOLDIER_NAME =  "KING";

    public King (Square square, SoldiersNames soldierName) {
        super(square,soldierName);
        this.color = super.getSoldierColor();
    }

    public List<Square> getAvailableSquaresToGoTo () {
        List<Square> squares = new ArrayList<>();
        squares.add(this.getUp());
        squares.add(this.getDown());
        squares.add(this.getLeft());
        squares.add(this.getRight());
        squares.add(this.getLeftDown());
        squares.add(this.getLeftUp());
        squares.add(this.getRightDown());
        squares.add(this.getRightUp());
        return squares;
    }


    private Square check_Square (int row, int column) {
        Square result;
        if (currentSoldiers[row][column] == null) {
            result = new Square(row,column);
        } else {
            result = this.canEatSoldier(new Square(row,column), this.color);
        }
        return result;
    }

    public Square getLeftUp () {
        Square result = null;
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() - 1;
        if (row >= 0 && column >= 0) {
            result = check_Square(row,column);
        }
        return result;
    }

    public Square getLeftDown () {
        Square result = null;
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() - 1;
        if (row <= 7 && column >= 0) {
            result = check_Square(row,column);
        }
        return result;
    }

    public Square getRightUp () {
        Square result = null;
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() + 1;
        if (row >= 0 && column <= 7) {
            result = check_Square(row,column);
        }
        return result;
    }

    public Square getRightDown () {
        Square result = null;
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() + 1;
        if (row <= 7 && column <= 7) {
            result = check_Square(row,column);
        }
        return result;
    }
    public Square getDown () {
        Square result = null;
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn();
        if (row <= 7) {
            result = check_Square(row,column);
        }
        return result;
    }

    public Square getUp () {
        Square result = null;
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn();
        if (row >= 0) {
            result = check_Square(row, column);
        }
        return result;
    }

    public Square getRight () {
        Square result = null;
        int row = super.getSquare().getRow();
        int column = super.getSquare().getColumn() + 1;
        if (column <= 7) {
            result = check_Square(row, column);
        }
        return result;
    }

    public Square getLeft () {
        Square result = null;
        int row = super.getSquare().getRow();
        int column = super.getSquare().getColumn() - 1;
        if (column >= 0) {
            result = check_Square(row, column);
        }
        return result;
    }

}
