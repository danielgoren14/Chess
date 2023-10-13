import java.util.ArrayList;
import java.util.List;

public class FollowBoard {
    private static Soldier[][] soldiers = new Soldier[8][8];

    public FollowBoard () {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                soldiers[i][j] = null; // Initialize all elements to null
            }
        }
        for (int i = 0; i < 8; i++) {
            soldiers[1][i] = new BlackPawn(new Square(1,i), SoldiersNames.BLACK_PAWN);
            new Soldier(new Square(1,i),SoldiersNames.BLACK_PAWN);
            soldiers[6][i] = new WhitePawn(new Square(6,i),SoldiersNames.WHITE_PAWN);
        }

//        soldiers[3][3] = new Soldier(new Square(3,3),SoldiersNames.BLACK_ROOK);

        soldiers[0][0] = new Rook(new Square(0,0),SoldiersNames.BLACK_ROOK);
        soldiers[0][7] = new Rook(new Square(0,7),SoldiersNames.BLACK_ROOK);

        soldiers[7][0] = new Rook(new Square(7,0),SoldiersNames.WHITE_ROOK);
        soldiers[7][7] = new Rook(new Square(7,7),SoldiersNames.WHITE_ROOK);

        soldiers[0][1] = new Knight(new Square(0,1),SoldiersNames.BLACK_KNIGHT);
        soldiers[0][6] = new Knight(new Square(0,6),SoldiersNames.BLACK_KNIGHT);

        soldiers[7][1] = new Knight(new Square(7,1),SoldiersNames.WHITE_KNIGHT);
        soldiers[7][6] = new Knight(new Square(7,6),SoldiersNames.WHITE_KNIGHT);

        soldiers[0][2] = new Bishop(new Square(0,2),SoldiersNames.BLACK_BISHOP);
        soldiers[0][5] = new Bishop(new Square(0,5),SoldiersNames.BLACK_BISHOP);

        soldiers[7][2] = new Bishop(new Square(7,2),SoldiersNames.WHITE_BISHOP);
        soldiers[7][5] = new Bishop(new Square(7,5),SoldiersNames.WHITE_BISHOP);

        soldiers[0][3] = new Queen(new Square(0,3),SoldiersNames.BLACK_QUEEN);

        soldiers[0][4] = new King(new Square(0,4),SoldiersNames.BLACK_KING);

        soldiers[7][3] = new Queen(new Square(7,3),SoldiersNames.WHITE_QUEEN);

        soldiers[7][4] = new King(new Square(7,4),SoldiersNames.WHITE_KING);

//        soldiers[4][4] = new Bishop(new Square(4,4), SoldiersNames.BLACK_BISHOP);
    }

    public static void setSoldiers(Soldier soldier, int row, int column) {
        soldiers[row][column] = soldier;
    }

    public String toString () {
        String result = "";
        for (int i = 0; i < soldiers.length; i++) {
            for (int j = 0; j < soldiers[0].length; j++) {
                result += soldiers[i][j];
            }
            result += "\n";
        }
        return result;
    }
    public static Soldier[][] getSoldiers() {
        return soldiers;
    }
}
