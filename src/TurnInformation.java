public class TurnInformation {
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
        }
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
