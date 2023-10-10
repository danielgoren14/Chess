import java.util.ArrayList;
import java.util.List;

public class Queen extends Soldier implements AvailableSquaresToGoTo {
    private Color_Black_Or_White color;

    public Queen(Square square, SoldiersNames soldierName) {
        super(square,soldierName);
        this.color = super.getSoldierColor();
    }

    public List<Square> getAvailableSquaresToGoTo () {
        Rook rook;
        Bishop bishop;
        int row = super.getSquare().getRow();
        int column = super.getSquare().getColumn();
        if (this.getSoldierColor().toString().equals("BLACK")) {
            rook = new Rook(new Square(row,column),SoldiersNames.BLACK_ROOK);
            bishop = new Bishop(new Square(row,column), SoldiersNames.BLACK_BISHOP);
        } else {
            rook = new Rook(new Square(row,column), SoldiersNames.WHITE_ROOK);
            bishop = new Bishop(new Square(row,column), SoldiersNames.WHITE_BISHOP);
        }
        List<Square> squares = new ArrayList<>();
        squares.addAll(rook.getAvailableSquaresToGoTo());
        squares.addAll(bishop.getAvailableSquaresToGoTo());
        return squares;
    }


}
