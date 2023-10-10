import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class OriginalAndMirroredChessboards extends JPanel {
    private List<Soldier> soldiers;
    private JButton[][] whiteChessBoard = new JButton[8][8];
    private ImageIcon[][] blackChessBoard = new ImageIcon[8][8];

    public OriginalAndMirroredChessboards() {
        this.soldiers = new ArrayList<>();
        this.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < this.whiteChessBoard.length; i++) {
            for (int j = 0; j < this.whiteChessBoard[0].length; j++) {

                this.whiteChessBoard[i][j] = new JButton(); // Create a JButton for each cell
                this.add(whiteChessBoard[i][j]); // Add the button to the ChessBoard panel

                if ((i + j) % 2 == 0) {
                    this.whiteChessBoard[i][j].setBackground(Color.WHITE);
                } else {
                    this.whiteChessBoard[i][j].setBackground(Color.GREEN);
                }

            }

        }





        ImageIcon blackPawnIcon = Utils.upscaleImage("src/ObjectPhotos/BlackPawn.png",50,50);
        ImageIcon blackRookIcon = Utils.upscaleImage("src/ObjectPhotos/BlackRook.png",30,30);
        ImageIcon whitePawnIcon = Utils.upscaleImage("src/ObjectPhotos/WhitePawn.png", 50,50);
        ImageIcon blackKnightIcon = Utils.upscaleImage("src/ObjectPhotos/BlackKnight.png",30,30);
        ImageIcon blackBishopIcon = Utils.upscaleImage("src/ObjectPhotos/BlackBishop.png",30,30);
        ImageIcon blackQueenIcon = Utils.upscaleImage("src/ObjectPhotos/BlackQueen.png",30,30);
        ImageIcon blackKingIcon = Utils.upscaleImage("src/ObjectPhotos/BlackKing.png",30,30);
        ImageIcon whiteKnightIcon = Utils.upscaleImage("src/ObjectPhotos/WhiteKnight.png", 30,30);
        ImageIcon whiteBishopIcon = Utils.upscaleImage("src/ObjectPhotos/WhiteBishop.png", 30,30);
        ImageIcon whiteQueenIcon = Utils.upscaleImage("src/ObjectPhotos/WhiteQueen.png", 30,30);
        ImageIcon whiteKingIcon = Utils.upscaleImage("src/ObjectPhotos/WhiteKing.png", 30,30);

        this.whiteChessBoard[0][1].setIcon(blackKnightIcon);
        this.whiteChessBoard[0][6].setIcon(blackKnightIcon);
        this.whiteChessBoard[0][2].setIcon(blackBishopIcon);
        this.whiteChessBoard[0][5].setIcon(blackBishopIcon);
        this.whiteChessBoard[0][3].setIcon(blackQueenIcon);
        this.whiteChessBoard[0][4].setIcon(blackKingIcon);


        this.soldiers.add(new Soldier(new Square(0,0),SoldiersNames.BLACK_ROOK));
        this.whiteChessBoard[0][0].setIcon(blackRookIcon);
        this.soldiers.add(new Soldier(new Square(0,7),SoldiersNames.BLACK_ROOK));
        this.whiteChessBoard[0][7].setIcon(blackRookIcon);
        for (int j = 0; j < 8; j++) {
            this.whiteChessBoard[6][j].setIcon(whitePawnIcon);
            this.soldiers.add(new Soldier(new Square(6,j),SoldiersNames.WHITE_PAWN));
            this.whiteChessBoard[1][j].setIcon(blackPawnIcon);
            this.soldiers.add(new Soldier(new Square(1,j),SoldiersNames.BLACK_PAWN));
        }
        this.soldiers.add(new Soldier(new Square(5,0),SoldiersNames.BLACK_KNIGHT));
//        for (int i = 0; i < 8; i++) {
//            int i_index = i;
//            for (int j = 0; j < 8; j++) {
//                int j_index = j;
//                this.chessBoardSquares[i][j].addActionListener((event) -> {
//
//                });
//            }
//        }
    }

}
