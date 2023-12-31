import java.util.Collections;
import java.util.List;

public class Soldier {
    Soldier[][] currentSoldiers = FollowBoard.getSoldiers();
    private Square square;
    private SOLDIERS_NAMES name;

    public Soldier (Square square, SOLDIERS_NAMES soldierName) {
//        this.isWhiteKingAlreadyMoved = false;
//        this.isBlackKingAlreadyMoved = false;
        this.square = square;
        this.name = soldierName;
    }

//    public void setWhiteKingAlreadyMoved () {
//        this.isWhiteKingAlreadyMoved = true;
//    }

//    public void setBlackKingAlreadyMoved () {
//        this.isBlackKingAlreadyMoved = true;
//    }

//    public boolean isWhiteKingAlreadyMoved() {
//        return isWhiteKingAlreadyMoved;
//    }

//    public boolean isBlackKingAlreadyMoved() {
//        return isBlackKingAlreadyMoved;
//    }

    public Square getSquare() {
        return square;
    }

    public SOLDIERS_NAMES getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Soldier{" +
                "square=" + square +
                ", soldierName=" + name +
                '}';
    }

//    public void setClickedSquareFromTheSameColor(Square square) {
//        ChessBoard.returnTheColorBack(ChessBoard.getChessBoard()[square.getRow()][square.getColumn()]);
////        ChessBoard.passOnAllTheSquaresAndLighteningOrReturnThemBack(squares,false);
//        Soldier soldier = FollowBoard.getSoldiers()[square.getRow()][square.getColumn()];
//        if (soldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
//            ChessBoard.isTurnOfWhite = true;
//        } else {
//            ChessBoard.isTurnOfWhite = false;
//        }
//        ChessBoard.lastSoldier = null;
//        ChessBoard.soldierClickedOnce = false;
//    }


    public void addASquare(List<Square> squares, int row, int column, Soldier soldier) {
        Square currentSquare = this.createFinalSquare(row,column, soldier);
        squares.add(currentSquare);
    }

    public Square createFinalSquare (int row, int column, Soldier soldier) {
        Square square = null;
        if (row <= 7 && row >= 0 && column >= 0 && column <= 7) {
            if (FollowBoard.getSoldiers()[row][column] != null) {
                if (!FollowBoard.getSoldiers()[row][column].getSoldierColor().equals(soldier.getSoldierColor())) {
                    square = new Square(row, column);
                }
            }
        }
        return square;
    }
    public Square canEatSoldier (Square square, COLOR_BLACK_OR_WHITE color) {
        int row = square.getRow();
        int column = square.getColumn();
        boolean canEat = false;
        if (currentSoldiers[row][column] != null) {
            if (!currentSoldiers[row][column].getSoldierColor().equals(color)) {
                canEat = true;
            }
//            else {
////                this.setClickedSquareFromTheSameColor(square);
//            }
//            if (color.toString().equals("BLACK")) {
//                if (currentSoldiers[row][column].getName().toString().contains("WHITE")) {
//                    canEat = true;
//                }
//            } else {
//                if (currentSoldiers[row][column].getName().toString().contains("BLACK")) {
//                    canEat = true;
//                }
//            }
        }
        Square currentSquare = null;
        if (canEat) {
            currentSquare = new Square(row,column);
        }
        return currentSquare;
    }

    public static List<Square> filterNullSquares (List<Square> squareList) {
        squareList.removeAll(Collections.singleton(null));
        return squareList;
    }


    public List<Square> getAvailableSquaresToGoTo () {
        List<Square> squares;
        int row = this.square.getRow();
        int column = this.square.getColumn();
        Soldier soldier = currentSoldiers[row][column];
        squares = soldier.getAvailableSquaresToGoTo();
//        Square squareToRemove = null;
//        for (Square currentSquare : squares) {
//            if (currentSoldiers[currentSquare.getRow()][currentSquare.getColumn()].getName().equals(SoldiersNames.WHITE_KING)
//            && soldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)) {
//                squareToRemove = currentSquare;
//                break;
//            }
//            if (currentSoldiers[currentSquare.getRow()][currentSquare.getColumn()].getName().equals(SoldiersNames.BLACK_KING)
//                    && soldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
//                squareToRemove = currentSquare;
//            }
//        }
//        if (squareToRemove != null) {
//            squares.remove(squareToRemove);
//        }
        return squares;
    }

    public void removeOpponentKingSquareFromAvailableSquares(List<Square> squares) {
//        List<Square> result = squares;
        int row = this.square.getRow();
        int column = this.square.getColumn();
        Soldier soldier = currentSoldiers[row][column];
        Square squareToRemove = null;
        for (Square currentSquare : squares) {
            if (currentSoldiers[currentSquare.getRow()][currentSquare.getColumn()] != null) {
                if (currentSoldiers[currentSquare.getRow()][currentSquare.getColumn()].getName().equals(SOLDIERS_NAMES.WHITE_KING)
                        && soldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)) {
                    squareToRemove = currentSquare;
                    break;
                }
                if (currentSoldiers[currentSquare.getRow()][currentSquare.getColumn()].getName().equals(SOLDIERS_NAMES.BLACK_KING)
                        && soldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
                    squareToRemove = currentSquare;
                }
            }
        }
        if (squareToRemove != null) {
            squares.remove(squareToRemove);
        }
//        return squares;
    }

    public COLOR_BLACK_OR_WHITE getSoldierColor() {
        COLOR_BLACK_OR_WHITE soldierColor = COLOR_BLACK_OR_WHITE.WHITE;
        String colorString = this.getStringColor();
        if (colorString.equals("BLACK")) {
            soldierColor = COLOR_BLACK_OR_WHITE.BLACK;
        }
        return soldierColor;
    }

    private String getStringColor () {
        String result = this.name.toString();
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '_') {
                result = result.substring(0,i);
                break;
            }
        }
        return result;
    }

    public String getAddressName () {
        String temp = this.name.toString();
        String result = "";
        int index = 0;
        result += temp.charAt(0);
        for (int i = 1; i < temp.length(); i++) {
            index = i;
            if (temp.charAt(i) == '_') {
                break;
            } else {
                result += Character.toLowerCase(temp.charAt(i));
            }
        }

        result += temp.charAt(index + 1);
        for (int i = index + 2; i < temp.length(); i++) {
            result += Character.toLowerCase(temp.charAt(i));
        }
        return result;
    }

//    public String getNameWithUpperCase () {
//        String result = this.getName().toString();
//        for (int i = 0; i < result.length(); i++) {
//            Character.toUpperCase(result.charAt(i));
//        }
//        return result;
//    }

//    public String getStringName () {
//        String result = this.name.toString();
//        for (int i = 0; i < result.length(); i++) {
//            if (result.charAt(i) == '_') {
//                result = result.substring(i + 1);
//                break;
//            }
//        }
//        return result;
//    }
}
