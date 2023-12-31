import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bishop extends Soldier implements AvailableSquaresToGoTo{
//    private static final String CURRENT_SOLDIER_NAME = "BISHOP";
    private COLOR_BLACK_OR_WHITE color;

    public Bishop (Square square, SOLDIERS_NAMES soldierName) {
        super(square,soldierName);
        this.color = super.getSoldierColor();
    }

    public COLOR_BLACK_OR_WHITE getColor() {
        return color;
    }

    public List<Square> getAvailableSquaresToGoTo () {
        List<Square> result = new ArrayList<>();
//        result.addAll(this.checkRightUpDiagonal());
//        result.addAll(this.checkLeftUpDiagonal());
//        result.addAll(this.checkRightDownDiagonal());
//        result.addAll(this.checkLeftDownDiagonal());
        result.addAll(this.getSquaresByNavigation(1,1));
        result.addAll(this.getSquaresByNavigation(-1,1));
        result.addAll(this.getSquaresByNavigation(-1,-1));
        result.addAll(this.getSquaresByNavigation(1,-1));
        result.removeAll(Collections.singleton(null));
        super.removeOpponentKingSquareFromAvailableSquares(result);
        return result;
    }

    public List<Square> getSquaresByNavigation (int vertical, int horizontal) {
        List<Square> squares = new ArrayList<>();
        int row = super.getSquare().getRow() + vertical;
        int column = super.getSquare().getColumn() + horizontal;
        Square currentSquare;
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            while (currentSoldiers[row][column] == null) {
                currentSquare = new Square(row,column);
                squares.add(currentSquare);
                if (row == 0 || row == 7 || column == 0 || column == 7) {
                    break;
                } else {
                    row += vertical;
                    column += horizontal;
                }
            }
            super.addASquare(squares,row,column,this);
        }
        return squares;
    }

//    private List<Square> checkLeftDownDiagonal () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow() + 1;
//        int column = super.getSquare().getColumn() - 1;
//        Square currentSquare;
//        if (row <= 7 && column >= 0) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row,column);
//                squares.add(currentSquare);
//                if (row == 7 || column == 0) {
//                    break;
//                } else {
//                    row++;
//                    column--;
//                }
//            }
//            super.addASquare(squares,row,column,this);
//        }
//        return squares;
//    }
//
//
//    private List<Square> checkLeftUpDiagonal () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow() - 1;
//        int column = super.getSquare().getColumn() - 1;
//        Square currentSquare;
//        if (row >= 0 && column >= 0) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row,column);
//                squares.add(currentSquare);
//                if (row == 0 || column == 0) {
//                    break;
//                } else {
//                    row--;
//                    column--;
//                }
//            }
//            super.addASquare(squares,row,column,this);
////            currentSquare = new Square(row,column);
////            Square square = canEatSoldier(currentSquare, this.color);
////            if (square != null) {
////                squares.add(square);
////            }
//        }
//        return squares;
//    }
//
//    private List<Square> checkRightUpDiagonal () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow() - 1;
//        int column = super.getSquare().getColumn() + 1;
//        Square currentSquare;
//        if (row >= 0 && column <= 7) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row, column);
//                squares.add(currentSquare);
//                if (row == 0 || column == 7) {
//                    break;
//                } else {
//                    row--;
//                    column++;
//                }
//            }
//            super.addASquare(squares,row,column,this);
//        }
//
//        return squares;
//    }
//
////    public Square createFinalSquare (int row, int column) {
////        Square square = null;
////        if (row <= 7 && row >= 0 && column >= 0 && column <= 7) {
////            square = new Square(row,column)
////        }
////        return square;
////    }
//
//    private List<Square> checkRightDownDiagonal () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow() + 1;
//        int column = super.getSquare().getColumn() + 1;
//        Square currentSquare;
//        if (row <= 7 && column <= 7) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row,column);
//                squares.add(currentSquare);
//                if (row == 7 || column == 7) {
//                    break;
//                } else {
//                    row++;
//                    column++;
//                }
//            }
//            super.addASquare(squares,row,column,this);
////            currentSquare = new Square(row,column);
////            Square square = canEatSoldier(currentSquare, this.color);
////            if (square != null) {
////                squares.add(square);
////            }
//        }
//        return squares;
//    }

}

