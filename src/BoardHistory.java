import java.util.Arrays;

public class BoardHistory {
    private TurnInformation[] history;

    public BoardHistory () {
        this.history = new TurnInformation[0];
//        this.history = this.addTurnInformationToHistory(turnInformation);
    }

    public void addTurnInformationToHistory(TurnInformation turnInformation) {
        TurnInformation[] temp = new TurnInformation[this.history.length + 1];
        if (this.history.length > 0) {
            for (int i = 0; i < this.history.length; i++) {
                temp[i] = this.history[i];
            }
            temp[this.history.length] = turnInformation;
        } else {
            temp[0] = turnInformation;
        }
        this.history = temp;
    }

    public TurnInformation[] getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "BoardHistory{" +
                "history=" + Arrays.toString(history) +
                '}';
    }
    //    public void addTurn(TurnInformation turnInformation) {
////        Soldier eatedSoldier = null;
////        if (FollowBoard.getSoldiers()[squareSoldierMovedTo.getRow()][squareSoldierMovedTo.getColumn()] != null) {
////            eatedSoldier = FollowBoard.getSoldiers()[squareSoldierMovedTo.getRow()][squareSoldierMovedTo.getColumn()];
////        }
//
//        this.history.add(turnInformation);
//
//    }
}
