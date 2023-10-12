import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WhitePawn extends Soldier implements AvailableSquaresToGoTo {
//    private boolean[] isJumpedTwice = new boolean[8];
    private Color_Black_Or_White color;
    private boolean alreadyMoved;

    public WhitePawn (Square square, SoldiersNames soldierName) {
        super(square, soldierName);


        this.color = super.getSoldierColor();
    }

//    public void checkIfPawnJumpedTwice () {
//
//    }
    public List<Square> getAvailableSquaresToGoTo () {
        if (super.getSquare().getRow() < 6) {
            this.alreadyMoved = true;
        } else {
            this.alreadyMoved = false;
        }
        List<Square> result = new ArrayList<>();
        result.add(this.getOneToward());
        result.add(this.getTwoToward());
        result.add(this.eatLeftUp());
        result.add(this.eatRightUp());
        result.removeAll(Collections.singleton(null));
        super.removeOpponentKingSquareFromAvailableSquares(result);
        return result;
    }

    public Square eatLeftUp () {
        Square square = null;
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() - 1;
        if (row >= 0 && column >= 0) {
            square = super.canEatSoldier(new Square(row,column),this.color);
        }
        return square;
    }

    public Square eatRightUp () {
        Square square = null;
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() + 1;
        if (row >= 0 && column <= 7) {
            square = super.canEatSoldier(new Square(row,column),this.color);
        }
        return square;
    }

    public Square getOneToward () {
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn();
        Square square = null;
        if (row >= 0) {
            if (super.currentSoldiers[row][column] == null) {
                square = new Square(row, column);
            }
        }
        return square;
    }

    public Square getTwoToward () {
        Square square = null;
        if (this.getOneToward() != null && !this.alreadyMoved) {
            int row = super.getSquare().getRow() - 2;
            int column = super.getSquare().getColumn();
            if (row >= 0) {
                if (super.currentSoldiers[row][column] == null) {
                    square = new Square(row, column);
                }
            }
        }
        return square;
    }




}
