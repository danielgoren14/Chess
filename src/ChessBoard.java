import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChessBoard extends JPanel {
    private Square currentBeatingSquare;
    private boolean leftRookMoved;
    private boolean rightRookMoved;
    private boolean isWhiteKingAlreadyMoved;
    private boolean isBlackKingAlreadyMoved;
    private BoardHistory boardHistory;
    private boolean isTurnOfWhite;
    private Soldier lastSoldier;
    private boolean soldierClickedOnce;
    public static final ImageIcon BLACK_PAWN_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackPawn.png", 30, 30);
    public static final ImageIcon WHITE_PAWN_ICON = Utils.upscaleImage("src/ObjectPhotos/WhitePawn.png", 30, 30);
    public static final ImageIcon BLACK_ROOK_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackRook.png", 30, 30);
    public static final ImageIcon WHITE_ROOK_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteRook.png", 30, 30);
    public static final ImageIcon BLACK_KNIGHT_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackKnight.png", 30, 30);
    public static final ImageIcon BLACK_BISHOP_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackBishop.png", 30, 30);
    public static final ImageIcon BLACK_QUEEN_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackQueen.png", 30, 30);
    public static final ImageIcon BLACK_KING_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackKing.png", 30, 30);
    public static final ImageIcon WHITE_KNIGHT_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteKnight.png", 30, 30);
    public static final ImageIcon WHITE_BISHOP_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteBishop.png", 30, 30);
    public static final ImageIcon WHITE_QUEEN_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteQueen.png", 30, 30);
    public static final ImageIcon WHITE_KING_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteKing.png", 30, 30);
//    private FollowBoard followBoard;
    private JButton[][] chessBoard = new JButton[8][8];
    public ChessBoard() {
//        FollowBoard followBoard = new FollowBoard();
        this.currentBeatingSquare = null;
        this.rightRookMoved = false;
        this.leftRookMoved = false;
        this.isWhiteKingAlreadyMoved = false;
        this.isBlackKingAlreadyMoved = false;
        this.boardHistory = new BoardHistory();
        isTurnOfWhite = true;
//        this.FollowBoard = new FollowBoard();
        this.setLayout(new GridLayout(8, 8));
        this.soldierClickedOnce = false;

        for (int i = 0; i < this.chessBoard.length; i++) {
            for (int j = 0; j < this.chessBoard[0].length; j++) {

                chessBoard[i][j] = new JButton(); // Create a JButton for each cell
                this.add(chessBoard[i][j]); // Add the button to the ChessBoard panel

                if ((i + j) % 2 == 0) {
                    chessBoard[i][j].setBackground(Color.WHITE);
                } else {
                    chessBoard[i][j].setBackground(Color.GREEN);
                }

            }

        }

        chessBoard[0][1].setIcon(BLACK_KNIGHT_ICON);
        chessBoard[0][6].setIcon(BLACK_KNIGHT_ICON);
        chessBoard[0][2].setIcon(BLACK_BISHOP_ICON);
        chessBoard[0][5].setIcon(BLACK_BISHOP_ICON);
        chessBoard[0][3].setIcon(BLACK_QUEEN_ICON);
        chessBoard[0][4].setIcon(BLACK_KING_ICON);

        chessBoard[7][1].setIcon(WHITE_KNIGHT_ICON);
        chessBoard[7][6].setIcon(WHITE_KNIGHT_ICON);
        chessBoard[7][2].setIcon(WHITE_BISHOP_ICON);
        chessBoard[7][5].setIcon(WHITE_BISHOP_ICON);
        chessBoard[7][3].setIcon(WHITE_QUEEN_ICON);
        chessBoard[7][4].setIcon(WHITE_KING_ICON);


        chessBoard[7][0].setIcon(WHITE_ROOK_ICON);

        chessBoard[7][7].setIcon(WHITE_ROOK_ICON);

        chessBoard[0][0].setIcon(BLACK_ROOK_ICON);

        chessBoard[0][7].setIcon(BLACK_ROOK_ICON);
        for (int j = 0; j < 8; j++) {
            chessBoard[6][j].setIcon(WHITE_PAWN_ICON);
            chessBoard[1][j].setIcon(BLACK_PAWN_ICON);
        }

//        chessBoard[4][4].setIcon(BLACK_BISHOP_ICON);

        for (int i = 0; i < 8; i++) {
            int row = i;
            for (int j = 0; j < 8; j++) {
                int column = j;
                chessBoard[row][column].addActionListener((event) -> {
                    Square currentSquare = new Square(row, column);
                    this.playTurn(currentSquare);
                });
            }
        }
    }


    private void playTurn (Square currentSquare) {
        Soldier currentClickedSoldier_IfSoldierExists = null;
        if (FollowBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()] != null) {
            currentClickedSoldier_IfSoldierExists = FollowBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()];
        }
        if (this.soldierClickedOnce) {
            Square castlingSquare = null;
            boolean toContinue = true;
            List<Square> availableSquares = lastSoldier.getAvailableSquaresToGoTo();
            Square squareOfEatingKing_ToRemoveIt = null;
            for (Square square : availableSquares) {
                if (FollowBoard.getSoldiers()[square.getRow()][square.getColumn()] != null) {
                    if (FollowBoard.getSoldiers()[square.getRow()][square.getColumn()].getName().equals(SoldiersNames.WHITE_KING)
                            || FollowBoard.getSoldiers()[square.getRow()][square.getColumn()].getName().equals(SoldiersNames.BLACK_KING)) {
                        squareOfEatingKing_ToRemoveIt = new Square(square.getRow(), square.getColumn());
                        break;
                    }
                }
            }
            if (squareOfEatingKing_ToRemoveIt != null) {
                availableSquares.remove(squareOfEatingKing_ToRemoveIt);
            }
            if (currentClickedSoldier_IfSoldierExists == null) {
                if (this.canDoSmallCastling(this.lastSoldier)) {
                    castlingSquare = new Square(this.lastSoldier.getSquare().getRow(), this.lastSoldier.getSquare().getColumn() + 2);
                    if (currentSquare.getRow() == this.lastSoldier.getSquare().getRow() && currentSquare.getColumn() == this.lastSoldier.getSquare().getColumn() + 2) {
                        availableSquares.add(castlingSquare);
                        this.doSmallCastling(availableSquares);
                        if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
                            this.isWhiteKingAlreadyMoved = true;
                        } else {
                            this.isBlackKingAlreadyMoved = true;
                        }
                        toContinue = false;
                    }
                }
//                System.out.println();
                if (this.canDoBigCastling(this.lastSoldier)) {
                    castlingSquare = new Square(this.lastSoldier.getSquare().getRow(), this.lastSoldier.getSquare().getColumn() - 2);
                    if (currentSquare.getRow() == this.lastSoldier.getSquare().getRow() && currentSquare.getColumn() == this.lastSoldier.getSquare().getColumn() - 2) {
                        availableSquares.add(castlingSquare);
                        this.doBigCastling(availableSquares);
                        if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
                            this.isWhiteKingAlreadyMoved = true;
                        } else {
                            this.isBlackKingAlreadyMoved = true;
                        }
                        toContinue = false;
                    }
                }
                if (this.currentBeatingSquare != null) {
                    availableSquares.add(this.currentBeatingSquare);
                }
            }
            if (!availableSquares.contains(null)) {
                Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.WHITE;
//                if (currentClickedSoldier_IfSoldierExists != null) {
//                }

                boolean isSquareToMoveTheSoldierTo_TheUserClicksOn = false;

                for (Square square : availableSquares) {
                    if (square.getRow() == currentSquare.getRow() &&
                            square.getColumn() == currentSquare.getColumn()) {
                        isSquareToMoveTheSoldierTo_TheUserClicksOn = true;
                        break;
                    }
                }
                if (toContinue) {
                    if (isSquareToMoveTheSoldierTo_TheUserClicksOn) {
                        if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
                            isTurnOfWhite = false;
                        } else {
                            isTurnOfWhite = true;
                        }
                        TurnInformation turnInformation = new TurnInformation(this.lastSoldier, currentSquare);
                        System.out.println(turnInformation.toString());
                        this.boardHistory.addTurnInformationToHistory(turnInformation);
                        System.out.println(this.boardHistory.getHistory().length);
                        System.out.println(this.boardHistory.toString());
                        String imageURL = lastSoldier.getAddressName();
                        System.out.println(imageURL);
                        ImageIcon currentImageIcon = Utils.upscaleImage("src/ObjectPhotos/" + imageURL + ".png", 30, 30);
                        chessBoard[currentSquare.getRow()][currentSquare.getColumn()].setIcon(currentImageIcon);
                        chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()].setIcon(null);
                        FollowBoard.setSoldiers(null, lastSoldier.getSquare().getRow(), lastSoldier.getSquare().getColumn());
                        returnTheColorBack(chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
                        if (this.currentBeatingSquare != null) {
                            if (currentSquare.getRow() == this.currentBeatingSquare.getRow()
                                    && currentSquare.getColumn() == this.currentBeatingSquare.getColumn()) {
                                if (currentSquare.getColumn() == this.lastSoldier.getSquare().getColumn() - 1) {
                                    this.chessBoard[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getColumn() - 1].setIcon(null);
                                    FollowBoard.setSoldiers(null, this.lastSoldier.getSquare().getRow(), this.lastSoldier.getSquare().getColumn() - 1);
                                } else {
                                    this.chessBoard[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getColumn() + 1].setIcon(null);
                                    FollowBoard.setSoldiers(null, this.lastSoldier.getSquare().getRow(), this.lastSoldier.getSquare().getColumn() + 1);
                                }
                            }
                        }
                        lastSoldier.getSquare().setRow(currentSquare.getRow());
                        lastSoldier.getSquare().setColumn(currentSquare.getColumn());
                        FollowBoard.setSoldiers(lastSoldier, currentSquare.getRow(), currentSquare.getColumn());
//                        Soldier chosenSoldier;
//                        Soldier newSoldier = null;
//                        if (this.isWhitePawnCameToTheLastRow(this.lastSoldier) || this.isBlackPawnCameToTheLastRow(lastSoldier)) {
//                            ChoosingSoldier choosingSoldier = new ChoosingSoldier(this.lastSoldier);
//                            JFrame frame = new JFrame();
//                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                            frame.setSize(400, 50);
//                            JPanel panel = new JPanel();
//                            frame.add(panel);
//                            frame.add(choosingSoldier);
//                            newSoldier = getChosenSoldier(this.lastSoldier);
//                        }
////                        if () {
////                            choosingSoldier = new ChoosingSoldier(this.lastSoldier);
////                            newSoldier = getChosenSoldier (this.lastSoldier);
////                        }
//
//                        if (newSoldier != null) {
//                            FollowBoard.setSoldiers(newSoldier, currentSquare.getRow(), currentSquare.getColumn());
//                        }
//                        System.out.println(followBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()]);
//                        System.out.println(followBoard.getSoldiers()[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
                        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
                        if (this.lastSoldier.getName().equals(SoldiersNames.WHITE_KING) || this.lastSoldier.getName().equals(SoldiersNames.BLACK_KING)) {
                            if (this.lastSoldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)) {
                                this.isBlackKingAlreadyMoved = true;
                            } else {
                                this.isWhiteKingAlreadyMoved = true;
                            }
                        }
                        if (castlingSquare != null) {
                            this.returnTheColorBack(this.chessBoard[castlingSquare.getRow()][castlingSquare.getColumn()]);
                        }
                        lastSoldier = null;
                        this.currentBeatingSquare = null;
                        this.soldierClickedOnce = false;
                    } else {
                        this.returnTheColorBack(this.chessBoard[currentSquare.getRow()][currentSquare.getColumn()]);
                        clickOnEmptySquareAfterClickingOnASoldier(availableSquares);
                    }
                } else {
                    this.lastSoldier = null;
                }
            } else {
                this.returnTheColorBack(this.chessBoard[currentSquare.getRow()][currentSquare.getColumn()]);
            }

            List<Soldier> allOpponentThreateningPawns = this.getAllOpponentThreateningPawns();
            for (Soldier soldier : allOpponentThreateningPawns) {
                if (soldier != null) {
                    System.out.println(soldier.toString());
                }
            }

            List<Soldier> threateningSoldiers_OnWhiteKing = this.getThreateningSoldiers(searchForWhiteKingSquare(),Color_Black_Or_White.WHITE);
            List<Square> threateningSquares_OnWhiteKing = this.getThreateningSquares(searchForWhiteKingSquare(),Color_Black_Or_White.WHITE);
            for (Soldier soldier : threateningSoldiers_OnWhiteKing) {
                if (soldier != null) {
                    System.out.println(soldier.toString());
                }
            }
            for (Square square : threateningSquares_OnWhiteKing) {
                if (square != null) {
                    System.out.println(square.toString());
                }
            }


            List<Soldier> threateningSoldiers_OnBlackKing = this.getThreateningSoldiers(searchForBlackKingSquare(),Color_Black_Or_White.BLACK);
            List<Square> threateningSquares_OnBlackKing = this.getThreateningSquares(searchForBlackKingSquare(),Color_Black_Or_White.BLACK);
            for (Soldier soldier : threateningSoldiers_OnBlackKing) {
                if (soldier != null) {
                    System.out.println(soldier.toString());
                }
            }
            for (Square square : threateningSquares_OnBlackKing) {
                if (square != null) {
                    System.out.println(square.toString());
                }
            }


        } else {
            if (currentClickedSoldier_IfSoldierExists != null) {
                if ((isTurnOfWhite && currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(Color_Black_Or_White.WHITE) ||
                        (!this.isTurnOfWhite && currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(Color_Black_Or_White.BLACK)))) {
//                    List<Square> availableSquares = Soldier.filterNullSquares(currentClickedSoldier_IfSoldierExists.getAvailableSquaresToGoTo());
                    List<Square> availableSquares = currentClickedSoldier_IfSoldierExists.getAvailableSquaresToGoTo();
                    if (this.canDoSmallCastling(currentClickedSoldier_IfSoldierExists)) {
                        Square smallCastlingSquare = new Square(currentSquare.getRow(),currentSquare.getColumn() + 2);
                        availableSquares.add(smallCastlingSquare);
                    }
                    if (this.canDoBigCastling(currentClickedSoldier_IfSoldierExists)) {
                        Square bigCastlingSquare = new Square(currentSquare.getRow(),currentSquare.getColumn() - 2);
                        availableSquares.add(bigCastlingSquare);
                    }
                    this.soldierClickedOnce = true;

//                    if (currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
//                        isTurnOfWhite = false;
//                    } else {
//                        isTurnOfWhite = true;
//                    }
//                    System.out.println(currentClickedSoldier_IfSoldierExists);
                    this.lastSoldier = currentClickedSoldier_IfSoldierExists;
                    this.currentBeatingSquare = this.getBeatingSquare(currentClickedSoldier_IfSoldierExists);
                    if (this.currentBeatingSquare != null) {
                        availableSquares.add(this.currentBeatingSquare);
                    }
                    if (availableSquares.size() > 0) {
                        System.out.println(availableSquares.toString());
                        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, true);
                        this.intensifyColorOfButton(this.chessBoard[currentSquare.getRow()][currentSquare.getColumn()]);
                    } else {
                        this.soldierClickedOnce = false;
                        this.lastSoldier = null;
                    }
                } else {
                    System.out.println(currentClickedSoldier_IfSoldierExists.getSoldierColor().toString());
                }
            } else {
                System.out.println("null");
            }
        }
    }

    public boolean isBlackPawnCameToTheLastRow (Soldier currentPawn) {
        boolean result = false;
        if (currentPawn.getName().equals(SoldiersNames.BLACK_PAWN)) {
            if (currentPawn.getSquare().getRow() == 7) {
                result = true;
            }
        }
        return result;
    }

    public Soldier getChosenSoldier (Soldier currentPawn) {
        Soldier chosenSoldier = null;
        if (isWhitePawnCameToTheLastRow(currentPawn) || isBlackPawnCameToTheLastRow(currentPawn)) {
            ChoosingSoldier choose = new ChoosingSoldier(currentPawn);
            chosenSoldier = choose.getNewSoldier();
        }
        return chosenSoldier;
    }

    public boolean isWhitePawnCameToTheLastRow(Soldier currentPawn) {
        boolean result = false;
        if (currentPawn.getName().equals(SoldiersNames.WHITE_PAWN)) {
            if (currentPawn.getSquare().getRow() == 0) {
                result = true;
            }
        }
        return result;
    }

    private List<Square> getThreateningSquaresTheKingCanMoveTo () {
        List<Square> result = new ArrayList<>();

        return result;
    }

    public List<Square> getAvailableSquaresSoldierCanMoveWithoutMakeTheKingInThreateningRange (Soldier soldier) {
        List<Square> squaresSoldierCanMove = new ArrayList<>();
        Color_Black_Or_White colorBlackOrWhite = soldier.getSoldierColor();
        if (colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) {
            Square whiteKingSquare = this.searchForWhiteKingSquare();
//            this.getCurrentThreateningSoldierByNavigation();

        } else {
//            Square blackKingSquare = this.searchForBlackKingSquare();

        }


        return squaresSoldierCanMove;
    }


//    private List<Square> getThreateningTrackSquaresOfSoldiersOnKing (Square square,Color_Black_Or_White color_black_or_white) {
//        List<Soldier> threateningSoldiersOnWhiteKing = this.getThreateningSoldiers(square,color_black_or_white);
//        List<Square> threateningTrackSquares = new ArrayList<>();
//
//
//        return threateningTrackSquares;
//    }


    private void makeHistory(Square squareTheSoldierMovesTo) {
        TurnInformation turnInformation = new TurnInformation(this.lastSoldier, squareTheSoldierMovesTo);
        if ((turnInformation.isBigCastlingForBlack() || turnInformation.isSmallCastlingForBlack())
                && this.lastSoldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)) {
            System.out.println(turnInformation.toStringOfCastling());
        } else if ((turnInformation.isBigCastlingForWhite() || turnInformation.isSmallCastlingForWhite())
                && this.lastSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
            System.out.println(turnInformation.toStringOfCastling());
        } else {
            System.out.println(turnInformation.toString());
        }
        this.boardHistory.addTurnInformationToHistory(turnInformation);
        System.out.println(this.boardHistory.getHistory().length);
        System.out.println(this.boardHistory.toString());


    }

    private void doBigCastling(List<Square> squaresToReturnTheirColorBack) {
        Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.WHITE;
        if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)) {
            colorBlackOrWhite = Color_Black_Or_White.BLACK;
        }
        int row = lastSoldier.getSquare().getRow();
        int column = lastSoldier.getSquare().getColumn();
        Soldier rook = FollowBoard.getSoldiers()[row][column - 4];
        this.lastSoldier.getSquare().setRow(row);
        this.lastSoldier.getSquare().setColumn(column - 2);
        rook.getSquare().setRow(row);
        rook.getSquare().setColumn(column - 1);
        if (colorBlackOrWhite.equals(Color_Black_Or_White.BLACK)) {
            this.isTurnOfWhite = true;
            this.chessBoard[row][column - 1].setIcon(BLACK_ROOK_ICON);
            this.chessBoard[row][column - 2].setIcon(BLACK_KING_ICON);
        } else {
            this.isTurnOfWhite = false;
            this.chessBoard[row][column - 1].setIcon(WHITE_ROOK_ICON);
            this.chessBoard[row][column - 2].setIcon(WHITE_KING_ICON);
        }
        this.chessBoard[row][column - 4].setIcon(null);
        this.chessBoard[row][column].setIcon(null);
        FollowBoard.setSoldiers(rook, row, column - 1);
        FollowBoard.setSoldiers(null, row, column - 4);
        this.returnTheColorBack(this.chessBoard[row][column]);
        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(squaresToReturnTheirColorBack, false);
        FollowBoard.setSoldiers(lastSoldier, row, column - 2);
        FollowBoard.setSoldiers(null, row, column);

        this.soldierClickedOnce = false;
    }

    private boolean canDoBigCastling(Soldier king) {
        boolean result = false;
        Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.BLACK;
        if (king.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
            colorBlackOrWhite = Color_Black_Or_White.WHITE;
        }

        if (king.getName().equals(SoldiersNames.BLACK_KING) || king.getName().equals(SoldiersNames.WHITE_KING)) {
            if ((!isWhiteKingAlreadyMoved && colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) ||
                    (!isBlackKingAlreadyMoved && colorBlackOrWhite.equals(Color_Black_Or_White.BLACK))) {
                int row = king.getSquare().getRow();
                int column = king.getSquare().getColumn();
                if (FollowBoard.getSoldiers()[row][column - 1] == null) {
                    if (FollowBoard.getSoldiers()[row][column - 2] == null) {
                        if (FollowBoard.getSoldiers()[row][column - 3] == null) {
                            if (FollowBoard.getSoldiers()[row][column - 4] != null) {
                                if (colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) {
                                    if (FollowBoard.getSoldiers()[row][column - 4].getName().equals(SoldiersNames.WHITE_ROOK)) {
                                        result = true;
                                    }
                                } else {
                                    if (FollowBoard.getSoldiers()[row][column - 4].getName().equals(SoldiersNames.BLACK_ROOK)) {
                                        result = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public Square getBeatingSquare (Soldier currentSoldier) {
        Square square = null;
        if (this.boardHistory.getHistory().length > 0) {
            TurnInformation lastTurnInformation = this.boardHistory.getTurnInformationInASpecificPlace(this.boardHistory.getHistory().length - 1);
            if (canMakeBeatingForCurrentWhitePawnDoingThis(currentSoldier)) {
                square = new Square(currentSoldier.getSquare().getRow() - 1, lastTurnInformation.getSquareTheSoldierMovesTo().getColumn());
            }
            if (canMakeBeatingForCurrentBlackPawnDoingThis(currentSoldier)) {
                square = new Square(currentSoldier.getSquare().getRow() + 1, lastTurnInformation.getSquareTheSoldierMovesTo().getColumn());
            }
        }
        return square;
    }

    public boolean canMakeBeatingForCurrentWhitePawnDoingThis (Soldier currentSoldier) {
        boolean result = false;
        TurnInformation lastTurnInformation = this.boardHistory.getTurnInformationInASpecificPlace(this.boardHistory.getHistory().length - 1);
        if (!lastTurnInformation.isCastlingHappened()) {
            if (this.isTurnOfWhite && lastTurnInformation.getMovingSoldier().getName().equals(SoldiersNames.BLACK_PAWN)) {
                if (currentSoldier.getName().equals(SoldiersNames.WHITE_PAWN)) {
                    int whitePawnStartSquareRow = currentSoldier.getSquare().getRow();
                    int whitePawnStartSquareColumn = currentSoldier.getSquare().getColumn();
                    int blackPawnStartSquareBeforeOneTurnRow = lastTurnInformation.getSquareTheSoldierStartFrom().getRow();
                    int blackPawnStartSquareBeforeOneTurnColumn = lastTurnInformation.getSquareTheSoldierStartFrom().getColumn();
                    int blackPawnFinishSquareRow = lastTurnInformation.getSquareTheSoldierMovesTo().getRow();
//                    int blackPawnFinishSquareColumn = lastTurnInformation.getSquareTheSoldierMovesTo().getColumn();
                    if (blackPawnStartSquareBeforeOneTurnRow == 1) {
                        if (blackPawnFinishSquareRow == 3 && whitePawnStartSquareRow == 3) {
                            if (whitePawnStartSquareColumn == blackPawnStartSquareBeforeOneTurnColumn + 1 || whitePawnStartSquareColumn == blackPawnStartSquareBeforeOneTurnColumn - 1) {
//                                if (squareTheSoldierMovesTo.getRow() == 2 && squareTheSoldierMovesTo.getColumn() == blackPawnFinishSquareColumn) {

                                result = true;
//                                }
                            }
                        }
                    }
                }
            }

        }
        return result;
    }



    public boolean canMakeBeatingForCurrentBlackPawnDoingThis (Soldier currentBeatingSquare) {
        boolean result = false;
        TurnInformation lastTurnInformation = this.boardHistory.getTurnInformationInASpecificPlace(this.boardHistory.getHistory().length - 1);
        if (!this.isTurnOfWhite && lastTurnInformation.getMovingSoldier().getName().equals(SoldiersNames.WHITE_PAWN)) {
            if (currentBeatingSquare.getName().equals(SoldiersNames.BLACK_PAWN)) {
                int blackPawnStartSquareRow = currentBeatingSquare.getSquare().getRow();
                int blackPawnStartSquareColumn = currentBeatingSquare.getSquare().getColumn();
                int whitePawnStartSquareBeforeOneTurnRow = lastTurnInformation.getSquareTheSoldierStartFrom().getRow();
                int whitePawnStartSquareBeforeOneTurnColumn = lastTurnInformation.getSquareTheSoldierStartFrom().getColumn();
                int whitePawnFinishSquareRow = lastTurnInformation.getSquareTheSoldierMovesTo().getRow();
//                int whitePawnFinishSquareColumn = lastTurnInformation.getSquareTheSoldierMovesTo().getColumn();
                if (whitePawnStartSquareBeforeOneTurnRow == 6) {
                    if (whitePawnFinishSquareRow == 4 && blackPawnStartSquareRow == 4) {
                        if (blackPawnStartSquareColumn == whitePawnStartSquareBeforeOneTurnColumn + 1 || blackPawnStartSquareColumn == whitePawnStartSquareBeforeOneTurnColumn - 1) {
//                            if (squareTheSoldierMovesTo.getRow() == 3 && squareTheSoldierMovesTo.getColumn() == whitePawnFinishSquareColumn) {
                            result = true;
//                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public boolean canDoSmallCastling (Soldier king) {
        boolean result = false;
        Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.BLACK;
        if (king.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
            colorBlackOrWhite = Color_Black_Or_White.WHITE;
        }

        if (king.getName().equals(SoldiersNames.BLACK_KING) || king.getName().equals(SoldiersNames.WHITE_KING)) {
            if ((!isWhiteKingAlreadyMoved && colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) ||
                    (!isBlackKingAlreadyMoved && colorBlackOrWhite.equals(Color_Black_Or_White.BLACK))) {
                int row = king.getSquare().getRow();
                int column = king.getSquare().getColumn();
                if (FollowBoard.getSoldiers()[row][column + 1] == null) {
                    if (FollowBoard.getSoldiers()[row][column + 2] == null) {
                        if (FollowBoard.getSoldiers()[row][column + 3] != null) {
                            if (colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) {
                                if (FollowBoard.getSoldiers()[row][column + 3].getName().equals(SoldiersNames.WHITE_ROOK)) {
                                    result = true;
                                }
                            } else {
                                if (FollowBoard.getSoldiers()[row][column + 3].getName().equals(SoldiersNames.BLACK_ROOK)) {
                                    result = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private void doSmallCastling (List<Square> squaresToReturnTheirColorBack) {
        Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.WHITE;
        if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)) {
            colorBlackOrWhite = Color_Black_Or_White.BLACK;
        }
        int row = lastSoldier.getSquare().getRow();
        int column = lastSoldier.getSquare().getColumn();
        Soldier rook = FollowBoard.getSoldiers()[row][column + 3];
        this.lastSoldier.getSquare().setRow(row);
        this.lastSoldier.getSquare().setColumn(column + 2);
        rook.getSquare().setRow(row);
        rook.getSquare().setColumn(column + 1);
        if (colorBlackOrWhite.equals(Color_Black_Or_White.BLACK)) {
            this.isTurnOfWhite = true;
            this.chessBoard[row][column + 1].setIcon(BLACK_ROOK_ICON);
            this.chessBoard[row][column + 2].setIcon(BLACK_KING_ICON);
        } else {
            this.isTurnOfWhite = false;
            this.chessBoard[row][column + 1].setIcon(WHITE_ROOK_ICON);
            this.chessBoard[row][column + 2].setIcon(WHITE_KING_ICON);
        }
        this.chessBoard[row][column + 3].setIcon(null);
        this.chessBoard[row][column].setIcon(null);
        FollowBoard.setSoldiers(rook,row,column + 1);
        FollowBoard.setSoldiers(null,row,column + 3);
        this.returnTheColorBack(this.chessBoard[row][column]);
        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(squaresToReturnTheirColorBack, false);
        FollowBoard.setSoldiers(lastSoldier,row,column + 2);
        FollowBoard.setSoldiers(null,row,column);
        this.soldierClickedOnce = false;
    }

    private void clickOnEmptySquareAfterClickingOnASoldier (List<Square> availableSquares) {
        returnTheColorBack(this.chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
        if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
            this.isTurnOfWhite = true;
        } else {
            this.isTurnOfWhite = false;
        }
        this.soldierClickedOnce = false;
    }

    private Square searchForWhiteKingSquare () {
        Square whiteKingSquare = null;
        for (int i = 0; i < 8; i++) {
            int row = i;
            for (int j = 0; j < 8; j++) {
                int column = j;
                Soldier soldier = FollowBoard.getSoldiers()[row][column];
                if (soldier != null) {
                    if (this.isFromTheSameColor(Color_Black_Or_White.WHITE, soldier) && soldier.getName().equals(SoldiersNames.WHITE_KING)) {
                        whiteKingSquare = new Square(row, column);
                        i = 8;
                        break;
                    }
                }
            }
        }
        return whiteKingSquare;
    }

    private Square searchForBlackKingSquare () {
        Square blackKingSquare = null;
        for (int i = 0; i < 8; i++) {
            int row = i;
            for (int j = 0; j < 8; j++) {
                int column = j;
                Soldier soldier = FollowBoard.getSoldiers()[row][column];
                if (soldier != null) {
                    if (this.isFromTheSameColor(Color_Black_Or_White.BLACK, soldier) && soldier.getName().equals(SoldiersNames.BLACK_KING)) {
                        blackKingSquare = new Square(row, column);
                        i = 8;
                        break;
                    }
                }
            }
        }
        return blackKingSquare;
    }


    private boolean isFromTheSameColor (Color_Black_Or_White colorBlackOrWhite, Soldier soldier) {
        if (soldier.getSoldierColor().equals(colorBlackOrWhite)) {
            return true;
        } else {
            return false;
        }
    }


    public List<Soldier> getThreateningSoldiers (Square square, Color_Black_Or_White color_black_or_white) {
        List<Soldier> threateningSoldiers = new ArrayList<>();
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,1,0));
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,-1,0));
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,1,1));
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,-1,1));
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,0,1));
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,0,-1));
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,-1,-1));
        threateningSoldiers.add(this.getCurrentThreateningSoldierByNavigation(square,color_black_or_white,1,-1));
        return threateningSoldiers;
    }

    public List<Square> getThreateningSquares (Square square, Color_Black_Or_White color_black_or_white) {
        List<Square> squares = new ArrayList<>();
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,1,0));
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,-1,0));
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,1,1));
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,-1,1));
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,0,1));
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,0,-1));
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,-1,-1));
        squares.addAll(this.getCurrentThreateningSquaresByNavigation(square,color_black_or_white,1,-1));
        return squares;
    }

    public List<Square> getCurrentThreateningSquaresByNavigation (Square square, Color_Black_Or_White color_black_or_white, int vertical, int horizontal) {
        List<Square> result = new ArrayList<>();
        List<Square> temp = new ArrayList<>();
        Soldier king = FollowBoard.getSoldiers()[square.getRow()][square.getColumn()];
        int row = king.getSquare().getRow() + horizontal;
        int column = king.getSquare().getColumn() + vertical;
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            while (FollowBoard.getSoldiers()[row][column] == null) {
                if (row > 0 && row < 7 && column < 7 && column > 0) {
                    temp.add(new Square(row,column));
                    row += horizontal;
                    column += vertical;
                } else {
                    break;
                }
            }
            if (FollowBoard.getSoldiers()[row][column] != null) {
                if (color_black_or_white.equals(Color_Black_Or_White.WHITE)) {
                    if (FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_BISHOP)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_QUEEN)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_ROOK)) {
                        result.addAll(temp);
                    }
                } else {
                    if (FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_BISHOP)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_QUEEN)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_ROOK)) {
                        result.addAll(temp);
                    }
                }
            }
        }
        return result;
    }


    public List<Soldier> getAllOpponentThreateningPawns () {
        List<Soldier> threateningSquares = new ArrayList<>();
        Soldier opponentThreateningWhitePawnFromLeftDown = this.getOpponentThreateningWhitePawn(this.searchForWhiteKingSquare(),-1);
        Soldier opponentThreateningWhitePawnFromRightDown = this.getOpponentThreateningWhitePawn(this.searchForWhiteKingSquare(),1);

        Soldier opponentThreateningBlackPawnFromLeftDown = this.getOpponentThreateningBlackPawn(this.searchForBlackKingSquare(),-1);
        Soldier opponentThreateningBlackPawnFromRightDown = this.getOpponentThreateningBlackPawn(this.searchForBlackKingSquare(),1);

        threateningSquares.add(opponentThreateningWhitePawnFromLeftDown);
        threateningSquares.add(opponentThreateningWhitePawnFromRightDown);
        threateningSquares.add(opponentThreateningBlackPawnFromLeftDown);
        threateningSquares.add(opponentThreateningBlackPawnFromRightDown);

        return threateningSquares;
    }
    public Soldier getOpponentThreateningWhitePawn (Square square, int horizontal) {
        Soldier soldier = null;
        int row = square.getRow();
        int column = square.getColumn();
        if (FollowBoard.getSoldiers()[row - 1][column + horizontal] != null) {
            if (FollowBoard.getSoldiers()[row - 1][column + horizontal].getName().equals(SoldiersNames.BLACK_PAWN)) {
                soldier = FollowBoard.getSoldiers()[row - 1][column + horizontal];
            }
        }

        return soldier;
    }

    public Soldier getOpponentThreateningBlackPawn (Square square, int horizontal) {
        Soldier soldier = null;
        int row = square.getRow();
        int column = square.getColumn();

        if (FollowBoard.getSoldiers()[row + 1][column + horizontal] != null) {
            if (FollowBoard.getSoldiers()[row + 1][column + horizontal].getName().equals(SoldiersNames.WHITE_PAWN)){
                soldier = FollowBoard.getSoldiers()[row + 1][column + horizontal];
            }
        }

        return soldier;
    }




    public Soldier getCurrentThreateningSoldierByNavigation(Square square, Color_Black_Or_White color_black_or_white, int vertical, int horizontal) {
        Soldier soldier = null;
        Soldier king = FollowBoard.getSoldiers()[square.getRow()][square.getColumn()];
        int row = king.getSquare().getRow() + horizontal;
        int column = king.getSquare().getColumn() + vertical;
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            while (FollowBoard.getSoldiers()[row][column] == null) {
                if (row > 0 && row < 7 && column < 7 && column > 0) {
                    row += horizontal;
                    column += vertical;
                } else {
                    break;
                }
            }
            if (FollowBoard.getSoldiers()[row][column] != null) {
                if (color_black_or_white.equals(Color_Black_Or_White.WHITE)) {
                    if (FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_BISHOP)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_QUEEN)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_ROOK)) {
                        soldier = FollowBoard.getSoldiers()[row][column];
                    }
                } else {
                    if (FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_BISHOP)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_QUEEN)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_ROOK)) {
                        soldier = FollowBoard.getSoldiers()[row][column];
                    }
                }
            }
        }

        return soldier;

    }

    private Color_Black_Or_White getColorBySoldierName (SoldiersNames soldierName) {
        Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.BLACK;
        String temp = soldierName.toString();
        String result = "";
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '_') {
                break;
            } else {
                result += temp.charAt(i);
            }
        }
        if (result.equals("WHITE")) {
            colorBlackOrWhite = Color_Black_Or_White.WHITE;
        }
        return colorBlackOrWhite;
    }

//    public List<Soldier> getAllTheOpponentSoldiers(Color_Black_Or_White colorBlackOrWhite) {
//        List<Soldier> currentPossibleThreateningSoldiers = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            int row = i;
//            for (int j = 0; j < 8; j++) {
//                int column = j;
//                if (followBoard.getSoldiers()[row][column] != null) {
//                    Soldier soldier = new Soldier(new Square(row,column),followBoard.getSoldiers()[row][column].getName());
//                    if (!soldier.getSoldierColor().equals(colorBlackOrWhite)) {
//                        currentPossibleThreateningSoldiers.add(soldier);
//                    }
//                }
//            }
//        }
//        return currentPossibleThreateningSoldiers;
//    }





    public void passOnAllTheSquaresAndLighteningOrReturnThemBack (List<Square> availableSquares, boolean lighten) {
        for (Square square : availableSquares) {
            int currRow = square.getRow();
            int currColumn = square.getColumn();
            if (lighten) {
                intensifyColorOfButton(chessBoard[currRow][currColumn]);
            } else {
                returnTheColorBack(chessBoard[currRow][currColumn]);
            }
        }
    }

    public void intensifyColorOfButton (JButton button) {
        if (button.getBackground() == Color.WHITE) {
            button.setBackground(new Color(220, 200, 246));
        }
        if (button.getBackground() == Color.GREEN) {
            button.setBackground(new Color(0, 100, 100));
        }
    }

    public void returnTheColorBack (JButton button) {
        if (Objects.equals(button.getBackground(), new Color(220, 200, 246))) {
            button.setBackground(Color.WHITE);
        }
        if (Objects.equals(button.getBackground(), new Color(0, 100, 100))) {
            button.setBackground(Color.GREEN);
        }
    }

    public String filterEnumName (SoldiersNames soldierName) {
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
