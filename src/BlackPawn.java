import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BlackPawn extends Soldier implements AvailableSquaresToGoTo {
//    private boolean[] isJumpedTwice = new boolean[8];

    private Color_Black_Or_White color;
    private boolean alreadyMoved;

    public BlackPawn (Square square, SoldiersNames soldierName) {
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
