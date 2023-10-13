import javax.swing.*;

public class Window extends JFrame {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    public Window () {
        FollowBoard followBoard = new FollowBoard();
        JFrame frame = new JFrame("Chess Board");
        ChessBoard chessBoard = new ChessBoard();
        frame.add(chessBoard);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
