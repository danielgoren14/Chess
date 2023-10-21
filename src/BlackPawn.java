import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackPawn extends Soldier implements AvailableSquaresToGoTo {
//    private boolean[] isJumpedTwice = new boolean[8];

//    private boolean[] alreadyMoved = new boolean[8];
//    private Square[] currentSquares = new Square[8];
    private COLOR_BLACK_OR_WHITE color;
    private boolean alreadyMoved;

    public BlackPawn (Square square, SOLDIERS_NAMES soldierName) {
        super(square, soldierName);


        this.color = super.getSoldierColor();
    }

//    public void getClickedSquareFromTheSameColor (Square square) {
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

//    public Square beatingLeftDown () {
//        Square result = null;
//        Soldier currentSoldier = ChessBoard.lastSoldier;
//        if (currentSoldier.getName().equals(SoldiersNames.WHITE_PAWN)) {
//            int rowLastSoldier = currentSoldier.getSquare().getRow();
//            int rowLastColumn = currentSoldier.getSquare().getColumn();
//
//            int row = super.getSquare().getRow() - 1;
//            int column = super.getSquare().getColumn() - 1;
//            if (row >= 0 && column >= 0) {
//                if (row == rowLastSoldier + 1 && column == rowLastColumn) {
//                    result = new Square(row,column);
//                }
//            }
//        }
//
//        return result;
//    }

//    public Square beatingLeftRight () {
////        Soldier currentSoldier = ChessBoard.lastSoldier;
//        int rowLastSoldier = currentSoldier.getSquare().getRow();
//        int rowLastColumn = currentSoldier.getSquare().getColumn();
//        Square result = null;
//        int row = super.getSquare().getRow() + 1;
//        int column = super.getSquare().getColumn() - 1;
//        if (row <= 7 && column >= 0) {
//            if (row == rowLastSoldier && column == rowLastColumn) {
//                result = new Square(row,column);
//            }
//        }
//        return result;
//    }
    public List<Square> getAvailableSquaresToGoTo () {
//        if (this.currentSquares[super.getSquare().getRow()] != null) {
//            this.currentSquares[super.getSquare().getRow()] = super.getSquare();
//            this.alreadyMoved[super.getSquare().getColumn()] = true;
//        } else {
//            this.alreadyMoved[super.getSquare().getColumn()] = false;
//        }
        if (super.getSquare().getRow() > 1) {
            this.alreadyMoved = true;
        } else {
            this.alreadyMoved = false;
        }
        List<Square> result = new ArrayList<>();
        result.add(this.getOneDown());
        result.add(this.getTwoDown());
        result.add(this.eatLeftDown());
        result.add(this.eatRightDown());
        result.removeAll(Collections.singleton(null));
        super.removeOpponentKingSquareFromAvailableSquares(result);
        return result;
    }


    public Square eatLeftDown() {
        Square square = null;
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() - 1;
        if (row <= 7 && column >= 0) {
            square = super.canEatSoldier(new Square(row,column),this.color);
        }
        return square;
    }

    public Square eatRightDown() {
        Square square = null;
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn() + 1;
        if (row <= 7 && column <= 7) {
            square = super.canEatSoldier(new Square(row,column),this.color);
        }
        return square;
    }

    public Square getOneDown() {
        int row = super.getSquare().getRow() + 1;
        int column = super.getSquare().getColumn();
        Square square = null;
        if (row <= 7) {
            if (super.currentSoldiers[row][column] == null) {
                square = new Square(row, column);
            }
        }
        return square;
    }

    public Square getTwoDown () {
        Square square = null;
        if (this.getOneDown() != null && !this.alreadyMoved) {
            int row = super.getSquare().getRow() + 2;
            int column = super.getSquare().getColumn();
            if (row <= 7) {
                if (super.currentSoldiers[row][column] == null) {
                    square = new Square(row, column);
                }
            }
        }
        return square;
    }




}
