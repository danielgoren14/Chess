import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rook extends Soldier implements AvailableSquaresToGoTo{
    public static final String CURRENT_SOLDIER_NAME = "ROOK";
    private COLOR_BLACK_OR_WHITE color;

    public Rook (Square square, SOLDIERS_NAMES soldierName) {
        super(square, soldierName);
        this.color = super.getSoldierColor();
    }

    public List<Square> getAvailableSquaresToGoTo () {
        List<Square> result = new ArrayList<>();
//        result.addAll(this.right());
//        result.addAll(this.up());
//        result.addAll(this.left());
//        result.addAll(this.down());
        result.addAll(this.getSquaresByNavigation(1,0));
        result.addAll(this.getSquaresByNavigation(0,1));
        result.addAll(this.getSquaresByNavigation(-1,0));
        result.addAll(this.getSquaresByNavigation(0,-1));
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
                currentSquare = new Square(row, column);
                squares.add(currentSquare);
                row += vertical;
                column += horizontal;
                if (row < 0 || row > 7 || column < 0 || column > 7) {
                    break;
                }
            }
            super.addASquare(squares, row, column, this);
        }
        return squares;
    }

//    public List<Square> down () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow() + 1;
//        int column = super.getSquare().getColumn();
//        Square currentSquare;
//        if (row <= 7) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row,column);
//                squares.add(currentSquare);
//                if (row == 7) {
//                    break;
//                } else {
//                    row++;
//                }
//            }
//            super.addASquare(squares,row,column,this);
////            currentSquare = new Square(row,column);
////            Square square = getSquareWithSoldier_IfBelongsToOtherColor(currentSquare);
////            if (square != null) {
////                squares.add(square);
////            }
//        }
//        return squares;
//    }
//
//    public List<Square> up () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow() - 1;
//        int column = super.getSquare().getColumn();
//        Square currentSquare;
//        if (row >= 0) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row,column);
//                squares.add(currentSquare);
//                if (row == 0) {
//                    break;
//                } else {
//                    row--;
//                }
//            }
//            super.addASquare(squares,row,column,this);
////            currentSquare = new Square(row,column);
////            Square square = getSquareWithSoldier_IfBelongsToOtherColor(currentSquare);
////            if (square != null) {
////                squares.add(square);
////            }
//        }
//        return squares;
//    }
//
//    public List<Square> left () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow();
//        int column = super.getSquare().getColumn() - 1;
//        Square currentSquare;
//        if (column >= 0) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row,column);
//                squares.add(currentSquare);
//                if (column == 0) {
//                    break;
//                } else {
//                    column--;
//                }
//            }
//            super.addASquare(squares,row,column,this);
////            currentSquare = new Square(row,column);
////            Square square = getSquareWithSoldier_IfBelongsToOtherColor(currentSquare);
////            if (square != null) {
////                squares.add(square);
////            }
//        }
//        return squares;
//    }
//
//    public List<Square> right () {
//        List<Square> squares = new ArrayList<>();
//        int row = super.getSquare().getRow();
//        int column = super.getSquare().getColumn() + 1;
//        Square currentSquare;
//        if (column <= 7) {
//            while (currentSoldiers[row][column] == null) {
//                currentSquare = new Square(row,column);
//                squares.add(currentSquare);
//                if (column == 7) {
//                    break;
//                } else {
//                    column++;
//                }
//            }
//            super.addASquare(squares,row,column,this);
////            currentSquare = new Square(row,column);
////            Square square = getSquareWithSoldier_IfBelongsToOtherColor(currentSquare);
////            if (square != null) {
////                squares.add(square);
////            }
//        }
//        return squares;
//    }

//    private Square getSquareWithSoldier_IfBelongsToOtherColor (Square square) {
//        boolean canEat = false;
//        int row = square.getRow();
//        int column = square.getColumn();
//        if (this.color.toString().equals("BLACK")) {
//            if (currentSoldiers[row][column] != null) {
//                if (currentSoldiers[row][column].getName().toString().contains("WHITE")) {
//                    System.out.println(CURRENT_SOLDIER_NAME + " " + this.color + " can eat a " + Color_Black_Or_White.WHITE + " " + currentSoldiers[row][column].getName());
//                    canEat = true;
//                } else {
//                    System.out.println("that's the limit, " + this.color.toString() + " soldier can not eat a " + this.color.toString() + " soldier");
//                }
//            }
//        } else {
//            if (currentSoldiers[row][column] != null) {
//                if (currentSoldiers[row][column].getName().toString().contains("BLACK")) {
//                    System.out.println(CURRENT_SOLDIER_NAME + " " + this.color + " can eat a " + Color_Black_Or_White.BLACK + " " + currentSoldiers[row][column].getName());
//                    canEat = true;
//                } else {
//                    System.out.println("that's the limit, " + this.color.toString() + " soldier can not eat a " + this.color.toString() + " soldier");
//                }
//            }
//        }
//        Square currentSquare = null;
//        if (canEat) {
//            currentSquare = new Square(row,column);
//        }
//        return currentSquare;
//    }
}
