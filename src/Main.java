import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new Window();
//        FollowBoard followBoard = new FollowBoard();
//        System.out.println(followBoard.getSoldiers()[1][6].toString());
//        Bishop bishop = new Bishop(new Square(1,6), SoldiersNames.BLACK_BISHOP);
//        System.out.println(bishop.getSoldierColor().toString());
//        System.out.println(bishop.getAvailableSquaresToGoTo().toString());
//        System.out.println("all the board " + followBoard.toString());
//        SoldiersNames soldiersNames = SoldiersNames.BLACK_BISHOP;
//        String filteredName = filterEnumName(soldiersNames);
//        System.out.println(filteredName);
//        ChessBoard chessBoard = new ChessBoard();

    }

    public static String filterEnumName (SoldiersNames soldierName) {
        String result = "";
        String temp = soldierName.toString();
        int index = 0;
        for (int i = 0; i < temp.toString().length(); i++) {
            index ++;
            if (temp.charAt(i) == '_') {
                break;
            }
        }
        temp = temp.substring(index);
        System.out.println(temp.length());
        result += temp.charAt(0);
        char ch;
        for (int i = 1; i < temp.length(); i++) {
            ch = Character.toLowerCase(temp.charAt(i));
            System.out.println(ch);
            result += ch;
        }
        return result;
    }
}