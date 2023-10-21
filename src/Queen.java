import java.util.ArrayList;
import java.util.List;

public class Queen extends Soldier implements AvailableSquaresToGoTo {
    private COLOR_BLACK_OR_WHITE color;

    public Queen(Square square, SOLDIERS_NAMES soldierName) {
        super(square,soldierName);
        this.color = super.getSoldierColor();
    }

    public List<Square> getAvailableSquaresToGoTo () {
        Rook rook;
        Bishop bishop;
        int row = super.getSquare().getRow();
        int column = super.getSquare().getColumn();
        if (this.getSoldierColor().toString().equals("BLACK")) {
            rook = new Rook(new Square(row,column), SOLDIERS_NAMES.BLACK_ROOK);
            bishop = new Bishop(new Square(row,column), SOLDIERS_NAMES.BLACK_BISHOP);
        } else {
            rook = new Rook(new Square(row,column), SOLDIERS_NAMES.WHITE_ROOK);
            bishop = new Bishop(new Square(row,column), SOLDIERS_NAMES.WHITE_BISHOP);
        }
        List<Square> squares = new ArrayList<>();
        squares.addAll(rook.getAvailableSquaresToGoTo());
        squares.addAll(bishop.getAvailableSquaresToGoTo());
        super.removeOpponentKingSquareFromAvailableSquares(squares);
        return squares;
    }


}
