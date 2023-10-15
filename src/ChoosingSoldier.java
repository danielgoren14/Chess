import javax.swing.*;
import java.awt.*;

public class ChoosingSoldier extends JPanel {
    private JButton[] buttons = new JButton[4];
    private Soldier currentPawn;
    private Soldier newSoldier;

    public ChoosingSoldier (Soldier currentPawn) {
        this.currentPawn = currentPawn;
//        this.newSoldier = null;
        this.setLayout(new GridLayout(1, 4));
//        Square square = this.currentPawn.getSquare();
        for (int i = 0; i < 4; i++) {
            this.buttons[i] = new JButton();
            this.add(this.buttons[i]);
        }
        if (this.currentPawn.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
            for (int i = 0; i < this.buttons.length; i++) {
                this.buttons[i].setBackground(Color.GREEN);
            }
            this.buttons[0].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.WHITE_KNIGHT);
            });
            this.buttons[1].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.WHITE_BISHOP);
            });
            this.buttons[2].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.WHITE_ROOK);
            });
            this.buttons[3].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.WHITE_QUEEN);
            });
        } else {
            for (int i = 0; i < this.buttons.length; i++) {
                this.buttons[i].setBackground(Color.WHITE);
            }
            this.buttons[0].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.BLACK_KNIGHT);
            });
            this.buttons[1].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.BLACK_BISHOP);
            });
            this.buttons[2].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.BLACK_ROOK);
            });
            this.buttons[3].addActionListener((event) -> {
                this.newSoldier = this.getClickedSoldier(SoldiersNames.BLACK_QUEEN);
            });
        }
    }

    public Soldier getClickedSoldier (SoldiersNames soldiersNames) {
        Square square = this.currentPawn.getSquare();
        if (soldiersNames.toString().contains("KNIGHT")) {
            return new Knight(square,soldiersNames);
        } else if (soldiersNames.toString().contains("BISHOP")) {
            return new Bishop(square,soldiersNames);
        } else if (soldiersNames.toString().contains("ROOK")) {
            return new Rook(square,soldiersNames);
        } else {
            return new Queen(square,soldiersNames);
        }
    }


    public Soldier getNewSoldier() {
        return newSoldier;
    }
}
