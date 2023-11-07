import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ChessBoard extends JPanel {
    private boolean isWhiteKingInCheck;
    private boolean isBlackKingInCheck;
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
    private JButton[][] chessBoard = new JButton[8][8];

    public ChessBoard() {
        this.isWhiteKingInCheck = false;
        this.isBlackKingInCheck = false;
        this.currentBeatingSquare = null;
        this.rightRookMoved = false;
        this.leftRookMoved = false;
        this.isWhiteKingAlreadyMoved = false;
        this.isBlackKingAlreadyMoved = false;
        this.boardHistory = new BoardHistory();
        isTurnOfWhite = true;
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


    private void changeScreen (Square currentSquare
            , boolean isSquareToMoveTheSoldierTo_BelongsToAvailableSquaresAnd_TheUserClicksOn
            , List<Square> availableSquares
            , Square castlingSquare) {

        if (isSquareToMoveTheSoldierTo_BelongsToAvailableSquaresAnd_TheUserClicksOn) {
            isTurnOfWhite = !lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE);
            TurnInformation turnInformation = new TurnInformation(this.lastSoldier, currentSquare);
            System.out.println(turnInformation.toString());
            this.boardHistory.addTurnInformationToHistory(turnInformation);
            System.out.println(this.boardHistory.getHistory().length);
//                        System.out.println(this.boardHistory.toString());
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
            this.lastSoldier.getSquare().setRow(currentSquare.getRow());
            this.lastSoldier.getSquare().setColumn(currentSquare.getColumn());
            FollowBoard.setSoldiers(lastSoldier, currentSquare.getRow(), currentSquare.getColumn());
            this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
            if (this.lastSoldier.getName().equals(SOLDIERS_NAMES.WHITE_KING) || this.lastSoldier.getName().equals(SOLDIERS_NAMES.BLACK_KING)) {
                if (this.lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)) {
                    this.isBlackKingAlreadyMoved = true;
                } else {
                    this.isWhiteKingAlreadyMoved = true;
                }
            }
            if (castlingSquare != null) {
                this.returnTheColorBack(this.chessBoard[castlingSquare.getRow()][castlingSquare.getColumn()]);
            }
            this.lastSoldier = null;
            this.currentBeatingSquare = null;
            this.soldierClickedOnce = false;
        } else {
            this.returnTheColorBack(this.chessBoard[currentSquare.getRow()][currentSquare.getColumn()]);
            clickOnEmptySquareAfterClickingOnASoldier(availableSquares);
        }
    }

    private void playTurn (Square currentSquare) {
        Soldier currentClickedSoldier_IfSoldierExists = null;
        if (FollowBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()] != null) {
            currentClickedSoldier_IfSoldierExists = FollowBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()];
        }
        if (isCurrentKingInCheckBy_Bishop_Rook_Queen(COLOR_BLACK_OR_WHITE.WHITE)) {
            System.out.println("white king threatened by bishop or rook or queen");
            System.out.println(getThreatenedSquaresBy_Bishop_Rook_QueenIncludingTheAttackingSoldiers(COLOR_BLACK_OR_WHITE.WHITE).toString());
        }
        if (isCurrentKingInCheckBy_Bishop_Rook_Queen(COLOR_BLACK_OR_WHITE.BLACK)) {
            System.out.println("black king threatened by bishop or rook or queen");
            System.out.println(getThreatenedSquaresBy_Bishop_Rook_QueenIncludingTheAttackingSoldiers(COLOR_BLACK_OR_WHITE.BLACK).toString());
        }
        if (this.soldierClickedOnce) {
            Square castlingSquare = null;
            boolean toContinue = true;
            List<Square> availableSquares = lastSoldier.getAvailableSquaresToGoTo();
            Square squareOfEatingKing_ToRemoveIt = null;
            for (Square square : availableSquares) {
                if (FollowBoard.getSoldiers()[square.getRow()][square.getColumn()] != null) {
                    if (FollowBoard.getSoldiers()[square.getRow()][square.getColumn()].getName().equals(SOLDIERS_NAMES.WHITE_KING)
                            || FollowBoard.getSoldiers()[square.getRow()][square.getColumn()].getName().equals(SOLDIERS_NAMES.BLACK_KING)) {
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
                        if (lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
                            this.isWhiteKingAlreadyMoved = true;
                        } else {
                            this.isBlackKingAlreadyMoved = true;
                        }
                        toContinue = false;
                    }
                }
                if (this.canDoBigCastling(this.lastSoldier)) {
                    castlingSquare = new Square(this.lastSoldier.getSquare().getRow(), this.lastSoldier.getSquare().getColumn() - 2);
                    if (currentSquare.getRow() == this.lastSoldier.getSquare().getRow() && currentSquare.getColumn() == this.lastSoldier.getSquare().getColumn() - 2) {
                        availableSquares.add(castlingSquare);
                        this.doBigCastling(availableSquares);
                        if (lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
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
            if (availableSquares.size() >= 1) {
                boolean isSquareToMoveTheSoldierTo_BelongsToAvailableSquaresAnd_TheUserClicksOn = false;

                for (Square square : availableSquares) {
                    if (square.getRow() == currentSquare.getRow() &&
                            square.getColumn() == currentSquare.getColumn()) {
                        isSquareToMoveTheSoldierTo_BelongsToAvailableSquaresAnd_TheUserClicksOn = true;
                        break;
                    }
                }
                if (toContinue) {
                    this.changeScreen(currentSquare, isSquareToMoveTheSoldierTo_BelongsToAvailableSquaresAnd_TheUserClicksOn, availableSquares,
                            castlingSquare);
                } else {
                    this.returnTheColorBack(this.chessBoard[currentSquare.getRow()][currentSquare.getColumn()]);
                }
            }

            List<Soldier> allOpponentThreateningPawns = this.getAllOpponentThreateningPawns();
            for (Soldier soldier : allOpponentThreateningPawns) {
                if (soldier != null) {
                    System.out.println(soldier.toString());
                }
            }
        } else {
            if (currentClickedSoldier_IfSoldierExists != null) {
                if ((isTurnOfWhite && currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE) ||
                        (!this.isTurnOfWhite && currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)))) {
                    List<Square> availableSquares = currentClickedSoldier_IfSoldierExists.getAvailableSquaresToGoTo();
                    if (this.canDoSmallCastling(currentClickedSoldier_IfSoldierExists)) {
                        Square smallCastlingSquare = new Square(currentSquare.getRow(), currentSquare.getColumn() + 2);
                        availableSquares.add(smallCastlingSquare);
                    }
                    if (this.canDoBigCastling(currentClickedSoldier_IfSoldierExists)) {
                        Square bigCastlingSquare = new Square(currentSquare.getRow(), currentSquare.getColumn() - 2);
                        availableSquares.add(bigCastlingSquare);
                    }
                    this.soldierClickedOnce = true;
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
        if (currentPawn.getName().equals(SOLDIERS_NAMES.BLACK_PAWN)) {
            if (currentPawn.getSquare().getRow() == 7) {
                result = true;
            }
        }
        return result;
    }

    public boolean isWhitePawnCameToTheLastRow(Soldier currentPawn) {
        boolean result = false;
        if (currentPawn.getName().equals(SOLDIERS_NAMES.WHITE_PAWN)) {
            if (currentPawn.getSquare().getRow() == 0) {
                result = true;
            }
        }
        return result;
    }

    private void makeHistory(Square squareTheSoldierMovesTo) {
        TurnInformation turnInformation = new TurnInformation(this.lastSoldier, squareTheSoldierMovesTo);
        if ((turnInformation.isBigCastlingForBlack() || turnInformation.isSmallCastlingForBlack())
                && this.lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)) {
            System.out.println(turnInformation.toStringOfCastling());
        } else if ((turnInformation.isBigCastlingForWhite() || turnInformation.isSmallCastlingForWhite())
                && this.lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
            System.out.println(turnInformation.toStringOfCastling());
        } else {
            System.out.println(turnInformation.toString());
        }
        this.boardHistory.addTurnInformationToHistory(turnInformation);
        System.out.println(this.boardHistory.getHistory().length);
        System.out.println(this.boardHistory.toString());
    }

    private void doBigCastling(List<Square> squaresToReturnTheirColorBack) {
        COLOR_BLACK_OR_WHITE colorBlackOrWhite = COLOR_BLACK_OR_WHITE.WHITE;
        if (lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)) {
            colorBlackOrWhite = COLOR_BLACK_OR_WHITE.BLACK;
        }
        int row = lastSoldier.getSquare().getRow();
        int column = lastSoldier.getSquare().getColumn();
        Soldier rook = FollowBoard.getSoldiers()[row][column - 4];
        this.lastSoldier.getSquare().setRow(row);
        this.lastSoldier.getSquare().setColumn(column - 2);
        rook.getSquare().setRow(row);
        rook.getSquare().setColumn(column - 1);
        if (colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.BLACK)) {
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
        COLOR_BLACK_OR_WHITE colorBlackOrWhite = COLOR_BLACK_OR_WHITE.BLACK;
        if (king.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
            colorBlackOrWhite = COLOR_BLACK_OR_WHITE.WHITE;
        }

        if (king.getName().equals(SOLDIERS_NAMES.BLACK_KING) || king.getName().equals(SOLDIERS_NAMES.WHITE_KING)) {
            if ((!isWhiteKingAlreadyMoved && colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.WHITE)) ||
                    (!isBlackKingAlreadyMoved && colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.BLACK))) {
                int row = king.getSquare().getRow();
                int column = king.getSquare().getColumn();
                if (FollowBoard.getSoldiers()[row][column - 1] == null) {
                    if (FollowBoard.getSoldiers()[row][column - 2] == null) {
                        if (FollowBoard.getSoldiers()[row][column - 3] == null) {
                            if (FollowBoard.getSoldiers()[row][column - 4] != null) {
                                if (colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.WHITE)) {
                                    if (FollowBoard.getSoldiers()[row][column - 4].getName().equals(SOLDIERS_NAMES.WHITE_ROOK)) {
                                        result = true;
                                    }
                                } else {
                                    if (FollowBoard.getSoldiers()[row][column - 4].getName().equals(SOLDIERS_NAMES.BLACK_ROOK)) {
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
            if (this.isTurnOfWhite && lastTurnInformation.getMovingSoldier().getName().equals(SOLDIERS_NAMES.BLACK_PAWN)) {
                if (currentSoldier.getName().equals(SOLDIERS_NAMES.WHITE_PAWN)) {
                    int whitePawnStartSquareRow = currentSoldier.getSquare().getRow();
                    int whitePawnStartSquareColumn = currentSoldier.getSquare().getColumn();
                    int blackPawnStartSquareBeforeOneTurnRow = lastTurnInformation.getSquareTheSoldierStartFrom().getRow();
                    int blackPawnStartSquareBeforeOneTurnColumn = lastTurnInformation.getSquareTheSoldierStartFrom().getColumn();
                    int blackPawnFinishSquareRow = lastTurnInformation.getSquareTheSoldierMovesTo().getRow();
                    if (blackPawnStartSquareBeforeOneTurnRow == 1) {
                        if (blackPawnFinishSquareRow == 3 && whitePawnStartSquareRow == 3) {
                            if (whitePawnStartSquareColumn == blackPawnStartSquareBeforeOneTurnColumn + 1 || whitePawnStartSquareColumn == blackPawnStartSquareBeforeOneTurnColumn - 1) {
                                result = true;

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
        if (!this.isTurnOfWhite && lastTurnInformation.getMovingSoldier().getName().equals(SOLDIERS_NAMES.WHITE_PAWN)) {
            if (currentBeatingSquare.getName().equals(SOLDIERS_NAMES.BLACK_PAWN)) {
                int blackPawnStartSquareRow = currentBeatingSquare.getSquare().getRow();
                int blackPawnStartSquareColumn = currentBeatingSquare.getSquare().getColumn();
                int whitePawnStartSquareBeforeOneTurnRow = lastTurnInformation.getSquareTheSoldierStartFrom().getRow();
                int whitePawnStartSquareBeforeOneTurnColumn = lastTurnInformation.getSquareTheSoldierStartFrom().getColumn();
                int whitePawnFinishSquareRow = lastTurnInformation.getSquareTheSoldierMovesTo().getRow();
                if (whitePawnStartSquareBeforeOneTurnRow == 6) {
                    if (whitePawnFinishSquareRow == 4 && blackPawnStartSquareRow == 4) {
                        if (blackPawnStartSquareColumn == whitePawnStartSquareBeforeOneTurnColumn + 1 || blackPawnStartSquareColumn == whitePawnStartSquareBeforeOneTurnColumn - 1) {
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    public boolean canDoSmallCastling (Soldier king) {
        boolean result = false;
        COLOR_BLACK_OR_WHITE colorBlackOrWhite = COLOR_BLACK_OR_WHITE.BLACK;
        if (king.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
            colorBlackOrWhite = COLOR_BLACK_OR_WHITE.WHITE;
        }

        if (king.getName().equals(SOLDIERS_NAMES.BLACK_KING) || king.getName().equals(SOLDIERS_NAMES.WHITE_KING)) {
            if ((!isWhiteKingAlreadyMoved && colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.WHITE)) ||
                    (!isBlackKingAlreadyMoved && colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.BLACK))) {
                int row = king.getSquare().getRow();
                int column = king.getSquare().getColumn();
                if (FollowBoard.getSoldiers()[row][column + 1] == null) {
                    if (FollowBoard.getSoldiers()[row][column + 2] == null) {
                        if (FollowBoard.getSoldiers()[row][column + 3] != null) {
                            if (colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.WHITE)) {
                                if (FollowBoard.getSoldiers()[row][column + 3].getName().equals(SOLDIERS_NAMES.WHITE_ROOK)) {
                                    result = true;
                                }
                            } else {
                                if (FollowBoard.getSoldiers()[row][column + 3].getName().equals(SOLDIERS_NAMES.BLACK_ROOK)) {
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
        COLOR_BLACK_OR_WHITE colorBlackOrWhite = COLOR_BLACK_OR_WHITE.WHITE;
        if (lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.BLACK)) {
            colorBlackOrWhite = COLOR_BLACK_OR_WHITE.BLACK;
        }
        int row = lastSoldier.getSquare().getRow();
        int column = lastSoldier.getSquare().getColumn();
        Soldier rook = FollowBoard.getSoldiers()[row][column + 3];
        this.lastSoldier.getSquare().setRow(row);
        this.lastSoldier.getSquare().setColumn(column + 2);
        rook.getSquare().setRow(row);
        rook.getSquare().setColumn(column + 1);
        if (colorBlackOrWhite.equals(COLOR_BLACK_OR_WHITE.BLACK)) {
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
        if (lastSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
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
                    if (this.isFromTheSameColor(COLOR_BLACK_OR_WHITE.WHITE, soldier) && soldier.getName().equals(SOLDIERS_NAMES.WHITE_KING)) {
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
                    if (this.isFromTheSameColor(COLOR_BLACK_OR_WHITE.BLACK, soldier) && soldier.getName().equals(SOLDIERS_NAMES.BLACK_KING)) {
                        blackKingSquare = new Square(row, column);
                        i = 8;
                        break;
                    }
                }
            }
        }
        return blackKingSquare;
    }


    private boolean isFromTheSameColor (COLOR_BLACK_OR_WHITE colorBlackOrWhite, Soldier soldier) {
        if (soldier.getSoldierColor().equals(colorBlackOrWhite)) {
            return true;
        } else {
            return false;
        }
    }


    public List<Square> getThreateningSquaresOnCurrentKing(Square square) {
        List<Square> squares = new ArrayList<>();
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,1,0));
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,-1,0));
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,1,1));
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,-1,1));
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,0,1));
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,0,-1));
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,-1,-1));
        squares.addAll(this.getCurrentThreateningSquaresOnCurrentKingByNavigation(square,1,-1));
        squares.removeAll(Collections.singleton(null));
        return squares;
    }

    public List<Square> getCurrentThreateningSquaresOnCurrentKingByNavigation(Square square, int vertical, int horizontal) {
        List<Square> result = new ArrayList<>();
        List<Square> temp = new ArrayList<>();
        Soldier king = FollowBoard.getSoldiers()[square.getRow()][square.getColumn()];
        COLOR_BLACK_OR_WHITE color_black_or_white = king.getSoldierColor();
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
                if (color_black_or_white.equals(COLOR_BLACK_OR_WHITE.WHITE)) {
                    if (FollowBoard.getSoldiers()[row][column].getName().equals(SOLDIERS_NAMES.BLACK_BISHOP)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SOLDIERS_NAMES.BLACK_QUEEN)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SOLDIERS_NAMES.BLACK_ROOK)) {
                        temp.add(new Square(row,column));
                        result.addAll(temp);

                    }
                } else {
                    if (FollowBoard.getSoldiers()[row][column].getName().equals(SOLDIERS_NAMES.WHITE_BISHOP)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SOLDIERS_NAMES.WHITE_QUEEN)
                            || FollowBoard.getSoldiers()[row][column].getName().equals(SOLDIERS_NAMES.WHITE_ROOK)) {
                        temp.add(new Square(row,column));
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
        if (row - 1 >= 0) {
            if (FollowBoard.getSoldiers()[row - 1][column + horizontal] != null) {
                if (FollowBoard.getSoldiers()[row - 1][column + horizontal].getName().equals(SOLDIERS_NAMES.BLACK_PAWN)) {
                    soldier = FollowBoard.getSoldiers()[row - 1][column + horizontal];
                }
            }
        }

        return soldier;
    }

    public Soldier getOpponentThreateningBlackPawn (Square square, int horizontal) {
        Soldier soldier = null;
        int row = square.getRow();
        int column = square.getColumn();


        if (row + 1 <= 7) {
            if (FollowBoard.getSoldiers()[row + 1][column + horizontal] != null) {
                if (FollowBoard.getSoldiers()[row + 1][column + horizontal].getName().equals(SOLDIERS_NAMES.WHITE_PAWN)) {
                    soldier = FollowBoard.getSoldiers()[row + 1][column + horizontal];
                }
            }
        }
        return soldier;
    }


    public boolean isCurrentKingInCheckBy_Bishop_Rook_Queen (COLOR_BLACK_OR_WHITE kingColor) {
        boolean result = false;
        List<Square> threateningSquares = getThreatenedSquaresBy_Bishop_Rook_QueenIncludingTheAttackingSoldiers(kingColor);
        if (threateningSquares.size() >= 1) {
            result = true;
        }
        return result;
    }

    private List<Square> getDefendingSquaresTheCurrent_SoliderCanOffer (Soldier currentSoldier) {
        List<Square> result;
        List<Square> availableSquaresBeforeFiltering = currentSoldier.getAvailableSquaresToGoTo();
        Square currentKingSquare;
        if (currentSoldier.getSoldierColor().equals(COLOR_BLACK_OR_WHITE.WHITE)) {
            currentKingSquare = searchForWhiteKingSquare();
        } else {
            currentKingSquare = searchForBlackKingSquare();
        }
        List<Square> threateningSquaresOnCurrentKingIncludingAttackingAndOpponentSoldier = getThreateningSquaresOnCurrentKing(currentKingSquare);
        List<Square> temp = new ArrayList<>();
        for (Square square1 : threateningSquaresOnCurrentKingIncludingAttackingAndOpponentSoldier) {
            for (Square square2 : availableSquaresBeforeFiltering) {
                if (square1.getRow() == square2.getRow() &&
                square1.getColumn() == square2.getColumn()) {
                    temp.add(square1);
                } else {
                    System.out.println(square1 + " does not belong to the threatened track of the opponent attacking soldier");
                }
            }
        }
        result = temp;
        return result;
    }

    private List<Square> getThreatenedSquaresBy_Bishop_Rook_QueenIncludingTheAttackingSoldiers (COLOR_BLACK_OR_WHITE kingColor) {
        List<Square> squares = new ArrayList<>();
        Square kingSquare;
        if (kingColor.equals(COLOR_BLACK_OR_WHITE.WHITE)) {
            kingSquare = searchForWhiteKingSquare();
        } else {
            kingSquare = searchForBlackKingSquare();
        }
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,1,0));
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,1,1));
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,1,-1));
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,0,1));
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,0,-1));
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,-1,1));
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,-1,-1));
        squares.addAll(getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen(kingSquare,-1,0));
        squares.removeAll(Collections.singleton(null));
        return squares;
    }

    private List<Square> getThreateningSquaresOfAttackSoldiers_Bishop_Rook_Queen (Square kingSquare, int vertical, int horizontal) {
        List<Square> result = new ArrayList<>();
        int row = kingSquare.getRow() + vertical;
        int column = kingSquare.getColumn() + horizontal;
        boolean isThreatenedSquaresCanBeAttackedByAnyOpnnentSoldier = true;
        if (row <= 7 && row >= 0 && column >= 0 && column <= 7) {
            while (FollowBoard.getSoldiers()[row][column] == null) {
                result.add(new Square(row, column));
                row += vertical;
                column += horizontal;
                if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
                    System.out.println("checked Square for " + FollowBoard.getSoldiers()[kingSquare.getRow()][kingSquare.getColumn()].getSoldierColor() + " king " + new Square(row, column));
                } else {
                    isThreatenedSquaresCanBeAttackedByAnyOpnnentSoldier = false;
                    break;
                }
            }
            if (isThreatenedSquaresCanBeAttackedByAnyOpnnentSoldier) {
                if (FollowBoard.getSoldiers()[row][column] != null) {
                    Soldier kingSoldier = FollowBoard.getSoldiers()[kingSquare.getRow()][kingSquare.getColumn()];
                    Soldier checkedSoldier = FollowBoard.getSoldiers()[row][column];
                    if (!checkedSoldier.getSoldierColor().equals(kingSoldier.getSoldierColor())
                            && (checkedSoldier.getName().toString().contains("BISHOP")
                            || checkedSoldier.getName().toString().contains("ROOK")
                            || checkedSoldier.getName().toString().contains("QUEEN"))) {
                        result.add(new Square(row, column));
                    } else {
                        result = new ArrayList<>();
                    }
                } else {
                    result = new ArrayList<>();
                }
            } else {
//                System.out.println();
                result = new ArrayList<>();
            }
        }
        return result;
    }

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

}
