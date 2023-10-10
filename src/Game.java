public class Game {
    private ChessBoard chessBoard;
    private static boolean isTurnOfWhite;

    public Game () {
        isTurnOfWhite = true;
        this.chessBoard = new ChessBoard();
    }

//    public void play () {
//        if (isTurnOfWhite) {
//            Soldier soldier = ChessBoard.lastSoldier;
//            if (FollowBoard.getSoldiers()[0][0].equals(new Rook(new Square(0,0),SoldiersNames.BLACK_ROOK))) {
//                System.out.println("hurray");
//            }
//        }
//    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }
}
