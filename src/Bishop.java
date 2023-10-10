import java.util.ArrayList;
import java.util.List;

public class Bishop extends Soldier implements AvailableSquaresToGoTo{
//    private static final String CURRENT_SOLDIER_NAME = "BISHOP";
    private Color_Black_Or_White color;

    public Bishop (Square square, SoldiersNames soldierName) {
        super(square,soldierName);
        this.color = super.getSoldierColor();
    }

    public Color_Black_Or_White getColor() {
        return color;
    }

    public List<Square> getAvailableSquaresToGoTo () {
        List<Square> result = new ArrayList<>();
        result.addAll(this.checkRightUpDiagonal());
        result.addAll(this.checkLeftUpDiagonal());
        result.addAll(this.checkRightDownDiagonal());
        result.addAll(this.checkLeftDownDiagonal());
        return result;
    }

    private List<Square> checkLeftDownDiagonal () {
        List<Square> squares = new ArrayList<>();
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() - 1;
        Square currentSquare;
        if (row <= 7 && column >= 0) {
            while (currentSoldiers[row][column] == null) {
                currentSquare = new Square(row,column);
                squares.add(currentSquare);
                if (row == 7 || column == 0) {
                    break;
                } else {
                    row++;
                    column--;
                }
            }
            this.addASquare(squares,row,column,this);
//            currentSquare = new Square(row,column);
//            Square square = canEatSoldier(currentSquare, this.color);
//            if (square != null) {
//                squares.add(square);
//            }
        }
        return squares;
    }


    private List<Square> checkLeftUpDiagonal () {
        List<Square> squares = new ArrayList<>();
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() - 1;
        Square currentSquare;
        if (row >= 0 && column >= 0) {
            while (currentSoldiers[row][column] == null) {
                currentSquare = new Square(row,column);
                squares.add(currentSquare);
                if (row == 0 || column == 0) {
                    break;
                } else {
                    row--;
                    column--;
                }
            }
            this.addASquare(squares,row,column,this);
//            currentSquare = new Square(row,column);
//            Square square = canEatSoldier(currentSquare, this.color);
//            if (square != null) {
//                squares.add(square);
//            }
        }
        return squares;
    }

    private List<Square> checkRightUpDiagonal () {
        List<Square> squares = new ArrayList<>();
        int row = super.getSquare().getRow() - 1;
        int column = super.getSquare().getColumn() + 1;
        Square currentSquare;
        if (row >= 0 && column <= 7) {
            while (currentSoldiers[row][column] == null) {
                currentSquare = new Square(row, column);
                squares.add(currentSquare);
                if (row == 0 || column == 7) {
                    break;
                } else {
                    row--;
                    column++;
                }
            }
            this.addASquare(squares,row,column,this);
//            Square square = canEatSoldier(currentSquare, this.color);
//            if (square != null) {
//                squares.add(square);
//            }
        }

        return squares;
    }

//    public Square createFinalSquare (int row, int column) {
//        Square square = null;
//        if (row <= 7 && row >= 0 && column >= 0 && column <= 7) {
//            square = new Square(row,column)
//        }
//        return square;
//    }

    private List<Square> checkRightDownDiagonal () {
        List<Square> squares = new ArrayList<>();
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() + 1;
        Square currentSquare;
        if (row <= 7 && column <= 7) {
            while (currentSoldiers[row][column] == null) {
                currentSquare = new Square(row,column);
                squares.add(currentSquare);
                if (row == 7 || column == 7) {
                    break;
                } else {
                    row++;
                    column++;
                }
            }
            this.addASquare(squares,row,column,this);
//            currentSquare = new Square(row,column);
//            Square square = canEatSoldier(currentSquare, this.color);
//            if (square != null) {
//                squares.add(square);
//            }
        }
        return squares;
    }

}

