public class TurnInformation {
    private boolean isCastlingHappened;
    private boolean isSmallCastlingForBlack;
    private boolean isBigCastlingForBlack;
    private boolean isSmallCastlingForWhite;
    private boolean isBigCastlingForWhite;
    private Soldier movingSoldier;
    private Square squareTheSoldierStartFrom;
    private Soldier eatedSoldier;
    private Square squareTheSoldierMovesTo;



    public TurnInformation (Soldier movingSoldier, Square squareTheSoldierMovesTo) {
        this.squareTheSoldierStartFrom = new Square(movingSoldier.getSquare().getRow(), movingSoldier.getSquare().getColumn());
        this.eatedSoldier = null;
        this.movingSoldier = movingSoldier;
        this.squareTheSoldierMovesTo = squareTheSoldierMovesTo;
        if (FollowBoard.getSoldiers()[this.squareTheSoldierMovesTo.getRow()][this.squareTheSoldierMovesTo.getColumn()] != null) {
            this.eatedSoldier = FollowBoard.getSoldiers()[this.squareTheSoldierMovesTo.getRow()][this.squareTheSoldierMovesTo.getColumn()];
//            this.movingSoldier.getSquare().setRow(squareTheSoldierMovesTo.getRow());
//            this.movingSoldier.getSquare().setColumn(squareTheSoldierMovesTo.getColumn());
        }
        if (this.movingSoldier.getName().toString().contains("KING") && (squareTheSoldierMovesTo.getRow() == this.movingSoldier.getSquare().getRow()
                && squareTheSoldierMovesTo.getColumn() == this.movingSoldier.getSquare().getColumn() + 2)) {
            if (this.movingSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)) {
                this.isSmallCastlingForBlack = true;
            } else {
                this.isSmallCastlingForWhite = true;
            }
            this.isCastlingHappened = true;
        } else if (this.movingSoldier.getName().toString().contains("KING") && (squareTheSoldierMovesTo.getRow() == this.movingSoldier.getSquare().getRow()
                && squareTheSoldierMovesTo.getColumn() == this.movingSoldier.getSquare().getColumn() - 2)) {
            if (this.movingSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)) {
                this.isBigCastlingForBlack = true;
            } else {
                this.isBigCastlingForWhite = true;
            }
            this.isCastlingHappened = true;
        }
    }

    public Square getSquareTheSoldierMovesTo() {
        return squareTheSoldierMovesTo;
    }

    public Soldier getMovingSoldier() {
        return movingSoldier;
    }

    public Square getSquareTheSoldierStartFrom() {
        return squareTheSoldierStartFrom;
    }

    public Soldier getEatedSoldier() {
        return eatedSoldier;
    }

    public boolean isCastlingHappened() {
        return isCastlingHappened;
    }

    public boolean isSmallCastlingForBlack() {
        return isSmallCastlingForBlack;
    }

    public boolean isBigCastlingForBlack() {
        return isBigCastlingForBlack;
    }

    public boolean isSmallCastlingForWhite() {
        return isSmallCastlingForWhite;
    }

    public boolean isBigCastlingForWhite() {
        return isBigCastlingForWhite;
    }

    public String toStringOfCastling() {
        return "TurnInformation{" +
                "isSmallCastlingForBlack=" + isSmallCastlingForBlack +
                ", isBigCastlingForBlack=" + isBigCastlingForBlack +
                ", isSmallCastlingForWhite=" + isSmallCastlingForWhite +
                ", isBigCastlingForWhite=" + isBigCastlingForWhite +
                '}';
    }

    @Override
    public String toString() {
        return "TurnInformation{" +
                "movingSoldier=" + movingSoldier +
                ", squareTheSoldierStartFrom=" + squareTheSoldierStartFrom +
                ", eatedSoldier=" + eatedSoldier +
                ", squareTheSoldierMovesTo=" + squareTheSoldierMovesTo +
                '}';
    }
}
