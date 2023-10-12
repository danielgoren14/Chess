import java.util.Arrays;

public class BoardHistory {
    private TurnInformation[] history;

    public BoardHistory () {
        this.history = new TurnInformation[0];
//        this.history = this.addTurnInformationToHistory(turnInformation);
    }

    public TurnInformation getTurnInformationInASpecificPlace (int index) {
        return this.history[index];
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
        String  result = "BoardHistory{"+
                "history=";
        for (int i = 0; i < this.history.length; i++) {
            result += getToStringAccordingly(this.history[i]);
        }
        result += '}';
        return result;
    }

    public String getToStringAccordingly (TurnInformation currentTurnInformation) {
        String result = "";
        if (currentTurnInformation.isCastlingHappened()) {
            result += currentTurnInformation.toStringOfCastling();
        } else {
            result += currentTurnInformation.toString();
        }
        return result;
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
