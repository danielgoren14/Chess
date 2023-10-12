import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
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
    private final ImageIcon BLACK_PAWN_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackPawn.png", 30, 30);
    private final ImageIcon WHITE_PAWN_ICON = Utils.upscaleImage("src/ObjectPhotos/WhitePawn.png", 30, 30);
    private final ImageIcon BLACK_ROOK_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackRook.png", 30, 30);
    private final ImageIcon WHITE_ROOK_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteRook.png", 30, 30);
    private final ImageIcon BLACK_KNIGHT_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackKnight.png", 30, 30);
    private final ImageIcon BLACK_BISHOP_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackBishop.png", 30, 30);
    private final ImageIcon BLACK_QUEEN_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackQueen.png", 30, 30);
    private final ImageIcon BLACK_KING_ICON = Utils.upscaleImage("src/ObjectPhotos/BlackKing.png", 30, 30);
    private final ImageIcon WHITE_KNIGHT_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteKnight.png", 30, 30);
    private final ImageIcon WHITE_BISHOP_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteBishop.png", 30, 30);
    private final ImageIcon WHITE_QUEEN_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteQueen.png", 30, 30);
    private final ImageIcon WHITE_KING_ICON = Utils.upscaleImage("src/ObjectPhotos/WhiteKing.png", 30, 30);
    private FollowBoard followBoard;
    private JButton[][] chessBoard = new JButton[8][8];

    public ChessBoard() {
        this.currentBeatingSquare = null;
        this.rightRookMoved = false;
        this.leftRookMoved = false;
        this.isWhiteKingAlreadyMoved = false;
        this.isBlackKingAlreadyMoved = false;
        this.boardHistory = new BoardHistory();
        isTurnOfWhite = true;
        this.followBoard = new FollowBoard();
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


    public Square isTrackLeftUp(King king) {
        Square result = null;
        int row = king.getSquare().getRow() - 1;
        int column = king.getSquare().getColumn() - 1;
        Soldier currentSoldier = null;
        while (row >= 0 && column >= 0) {
            if (followBoard.getSoldiers()[row][column] != null) {
                currentSoldier = followBoard.getSoldiers()[row][column];
                break;
            }
            if (row > 0 && column > 0) {
                row--;
                column--;
            } else {
                break;
            }
        }
        if (currentSoldier != null) {
            SoldiersNames soldiersNames = followBoard.getSoldiers()[row][column].getName();
            if (soldiersNames.equals(SoldiersNames.BLACK_KING) || soldiersNames.equals(SoldiersNames.WHITE_KING)) {
                result = new Square(row, column);
            }
        }
        return result;
    }


//    public List<Square> getALlPossibleThreateningSoldiers () {
////        this.getSoldierThreateningOnKingFromLeftUp();
//
//
//    }


    public Square getBlackKingSquareInFollowBoard () {
        Square result = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (followBoard.getSoldiers()[i][j] != null) {
                    if (followBoard.getSoldiers()[i][j].getName().equals(SoldiersNames.BLACK_KING)) {
                        result = new Square(followBoard.getSoldiers()[i][j].getSquare().getRow(), followBoard.getSoldiers()[i][j].getSquare().getRow());
                    }
                }
            }
        }
        return result;
    }
    public Square getWhiteKingInFollowBoard () {
        Square result = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (followBoard.getSoldiers()[i][j] != null) {
                    if (followBoard.getSoldiers()[i][j].getName().equals(SoldiersNames.WHITE_KING)) {
                        result = new Square(followBoard.getSoldiers()[i][j].getSquare().getRow(), followBoard.getSoldiers()[i][j].getSquare().getRow());
                    }
                }
            }
        }
        return result;
    }

    private Soldier getSoldierThreateningOnKingFromLeftUp (Soldier soldier) {
        Soldier result = null;
        int row = soldier.getSquare().getRow() - 1;
        int column = soldier.getSquare().getColumn() - 1;
        Square square = new Square(row, column);
        while (followBoard.getSoldiers()[row][column] != null) {
            if (row > 0 && column > 0) {
                row--;
                column--;
            } else {
                break;
            }
        }
        if (followBoard.getSoldiers()[row][column] != null) {
            result = followBoard.getSoldiers()[row][column];
        }
        return result;
    }

    public List<Square> getTrackOfSSquaresOfThreateningOpponentSoldierOnKing(Soldier soldier) {
        List<Square> result = new ArrayList<>();
        Square square = new Square(soldier.getSquare().getRow(), soldier.getSquare().getColumn());
        SoldiersNames soldierName = soldier.getName();
        List<Square> availableSquares = soldier.getAvailableSquaresToGoTo();
        if (soldier.getName() != SoldiersNames.BLACK_KNIGHT && soldier.getName() != SoldiersNames.WHITE_KNIGHT
                && soldier.getName() != SoldiersNames.BLACK_PAWN && soldier.getName() != SoldiersNames.WHITE_PAWN
                && soldier.getName() != SoldiersNames.BLACK_PAWN && soldier.getName() != SoldiersNames.WHITE_PAWN
                && soldier.getName() != SoldiersNames.BLACK_KING && soldier.getName() != SoldiersNames.WHITE_KING) {
//            availableSquares = this.getTrackOfLeftUp(soldier);
            if (availableSquares.size() > 0) {
                return availableSquares;
            }
        }
        return result;
    }


    private void playTurn (Square currentSquare) {
        Soldier currentClickedSoldier_IfSoldierExists = null;
        if (followBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()] != null) {
            currentClickedSoldier_IfSoldierExists = followBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()];
        }
        if (this.soldierClickedOnce) {
            Square castlingSquare = null;
            boolean toContinue = true;
            List<Square> availableSquares = Soldier.filterNullSquares(lastSoldier.getAvailableSquaresToGoTo());
            Square squareOfEatingKing_ToRemoveIt = null;
            for (Square square : availableSquares) {
                if (followBoard.getSoldiers()[square.getRow()][square.getColumn()] != null) {
                    if (followBoard.getSoldiers()[square.getRow()][square.getColumn()].getName().equals(SoldiersNames.WHITE_KING)
                            || followBoard.getSoldiers()[square.getRow()][square.getColumn()].getName().equals(SoldiersNames.BLACK_KING)) {
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
                    if (currentSquare.getRow() == this.currentBeatingSquare.getRow()
                            && currentSquare.getColumn() == this.currentBeatingSquare.getColumn()) {
//                        TurnInformation currentTurnInformation = new TurnInformation(this.lastSoldier,currentSquare);
//                        this.boardHistory.addTurnInformationToHistory(currentTurnInformation);
//                        Soldier pawn;
                        if (this.lastSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
                            this.chessBoard[this.currentBeatingSquare.getRow()][this.currentBeatingSquare.getColumn()].setIcon(WHITE_PAWN_ICON);
//                            pawn = new WhitePawn(new Square(currentBeatingSquare.getRow(),currentSquare.getColumn()),SoldiersNames.WHITE_PAWN);
                        } else {
                            this.chessBoard[this.currentBeatingSquare.getRow()][this.currentBeatingSquare.getColumn()].setIcon(BLACK_PAWN_ICON);
//                            pawn = new BlackPawn(new Square(currentBeatingSquare.getRow(),currentSquare.getColumn()),SoldiersNames.BLACK_PAWN);
                        }

                        this.returnTheColorBack(this.chessBoard[currentSquare.getRow()][currentSquare.getColumn()]);
                        this.returnTheColorBack(this.chessBoard[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getColumn()]);
                        this.chessBoard[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getColumn()].setIcon(null);

                        if (currentSquare.getColumn() == this.lastSoldier.getSquare().getColumn() - 1) {

                            this.chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn() - 1].setIcon(null);
                            followBoard.setSoldiers(null,lastSoldier.getSquare().getRow(),lastSoldier.getSquare().getColumn() - 1);
                        } else {
                            this.chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn() + 1].setIcon(null);
                            followBoard.setSoldiers(null,lastSoldier.getSquare().getRow(),lastSoldier.getSquare().getColumn() + 1);
                        }
                        Soldier soldier = followBoard.getSoldiers()[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getColumn()];
                        this.lastSoldier.getSquare().setColumn(currentSquare.getColumn());
                        followBoard.setSoldiers(lastSoldier,currentSquare.getRow(),currentSquare.getColumn());
                        followBoard.setSoldiers(null,soldier.getSquare().getRow(),soldier.getSquare().getColumn());
                        toContinue = false;
                        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares,false);
                        this.soldierClickedOnce = false;
                        this.lastSoldier = null;
                    }
                }
            }
            if (availableSquares.size() > 0) {
                Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.WHITE;
                if (currentClickedSoldier_IfSoldierExists != null) {
//                    if (lastSoldier.getName().equals(SoldiersNames.BLACK_ROOK) || lastSoldier.getName().equals(SoldiersNames.WHITE_ROOK)
//                            || lastSoldier.getName().equals(SoldiersNames.BLACK_BISHOP) || lastSoldier.getName().equals(SoldiersNames.WHITE_BISHOP) ||
//                            lastSoldier.getName().equals(SoldiersNames.BLACK_QUEEN) || lastSoldier.getName().equals(SoldiersNames.WHITE_QUEEN)) {
//                        if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)) {
//                            colorBlackOrWhite = Color_Black_Or_White.BLACK;
//                        }
//                        List<Square> temp = new ArrayList<>();
//                        for (Square square : availableSquares) {
////                            if (!this.isFromTheSameColor(colorBlackOrWhite, followBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()])) {
//                            if (!this.isFromTheSameColor(colorBlackOrWhite,this.lastSoldier)) {
//                                temp.add(square);
//                            }
//                        }
//                        if (temp.size() > 0) {
//                            availableSquares.removeAll(temp);
//                        }
//                    }
                }

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
                        followBoard.setSoldiers(null, lastSoldier.getSquare().getRow(), lastSoldier.getSquare().getColumn());
                        returnTheColorBack(chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
                        lastSoldier.getSquare().setRow(currentSquare.getRow());
                        lastSoldier.getSquare().setColumn(currentSquare.getColumn());
                        followBoard.setSoldiers(lastSoldier, currentSquare.getRow(), currentSquare.getColumn());
                        System.out.println(followBoard.getSoldiers()[currentSquare.getRow()][currentSquare.getColumn()]);
                        System.out.println(followBoard.getSoldiers()[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
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
        } else {
            if (currentClickedSoldier_IfSoldierExists != null) {
                if ((isTurnOfWhite && currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(Color_Black_Or_White.WHITE) ||
                        (!this.isTurnOfWhite && currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(Color_Black_Or_White.BLACK)))) {
                    List<Square> availableSquares = Soldier.filterNullSquares(currentClickedSoldier_IfSoldierExists.getAvailableSquaresToGoTo());
                    if (this.canDoSmallCastling(currentClickedSoldier_IfSoldierExists)) {
                        Square smallCastlingSquare = new Square(currentSquare.getRow(),currentSquare.getColumn() + 2);
                        availableSquares.add(smallCastlingSquare);
                    }
                    if (this.canDoBigCastling(currentClickedSoldier_IfSoldierExists)) {
                        Square bigCastlingSquare = new Square(currentSquare.getRow(),currentSquare.getColumn() - 2);
                        availableSquares.add(bigCastlingSquare);
                    }
                    this.soldierClickedOnce = true;

                    if (currentClickedSoldier_IfSoldierExists.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
                        isTurnOfWhite = false;
                    } else {
                        isTurnOfWhite = true;
                    }
                    System.out.println(currentClickedSoldier_IfSoldierExists);
                    this.lastSoldier = currentClickedSoldier_IfSoldierExists;
                    this.currentBeatingSquare = this.getBeatingSquare(currentClickedSoldier_IfSoldierExists);
                    if (this.currentBeatingSquare != null) {
                        availableSquares.add(this.currentBeatingSquare);
                    }
                    if (availableSquares != null) {
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
        Soldier rook = followBoard.getSoldiers()[row][column - 4];
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
        followBoard.setSoldiers(rook, row, column - 1);
        followBoard.setSoldiers(null, row, column - 4);
        this.returnTheColorBack(this.chessBoard[row][column]);
        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(squaresToReturnTheirColorBack, false);
        followBoard.setSoldiers(lastSoldier, row, column - 2);
        followBoard.setSoldiers(null, row, column);

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
                if (followBoard.getSoldiers()[row][column - 1] == null) {
                    if (followBoard.getSoldiers()[row][column - 2] == null) {
                        if (followBoard.getSoldiers()[row][column - 3] == null) {
                            if (followBoard.getSoldiers()[row][column - 4] != null) {
                                if (colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) {
                                    if (followBoard.getSoldiers()[row][column - 4].getName().equals(SoldiersNames.WHITE_ROOK)) {
                                        result = true;
                                    }
                                } else {
                                    if (followBoard.getSoldiers()[row][column - 4].getName().equals(SoldiersNames.BLACK_ROOK)) {
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
            if (!this.isTurnOfWhite && lastTurnInformation.getMovingSoldier().getName().equals(SoldiersNames.BLACK_PAWN)) {
                if (currentSoldier.getName().equals(SoldiersNames.WHITE_PAWN)) {
                    int whitePawnStartSquareRow = currentSoldier.getSquare().getRow();
                    int whitePawnStartSquareColumn = currentSoldier.getSquare().getColumn();
                    int blackPawnStartSquareBeforeOneTurnRow = lastTurnInformation.getSquareTheSoldierStartFrom().getRow();
                    int blackPawnStartSquareBeforeOneTurnColumn = lastTurnInformation.getSquareTheSoldierStartFrom().getColumn();
                    int blackPawnFinishSquareRow = lastTurnInformation.getSquareTheSoldierMovesTo().getRow();
                    int blackPawnFinishSquareColumn = lastTurnInformation.getSquareTheSoldierMovesTo().getColumn();
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
        if (this.isTurnOfWhite && lastTurnInformation.getMovingSoldier().getName().equals(SoldiersNames.WHITE_PAWN)) {
            if (currentBeatingSquare.getName().equals(SoldiersNames.BLACK_PAWN)) {
                int blackPawnStartSquareRow = currentBeatingSquare.getSquare().getRow();
                int blackPawnStartSquareColumn = currentBeatingSquare.getSquare().getColumn();
                int whitePawnStartSquareBeforeOneTurnRow = lastTurnInformation.getSquareTheSoldierStartFrom().getRow();
                int whitePawnStartSquareBeforeOneTurnColumn = lastTurnInformation.getSquareTheSoldierStartFrom().getColumn();
                int whitePawnFinishSquareRow = lastTurnInformation.getSquareTheSoldierMovesTo().getRow();
                int whitePawnFinishSquareColumn = lastTurnInformation.getSquareTheSoldierMovesTo().getColumn();
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
                if (followBoard.getSoldiers()[row][column + 1] == null) {
                    if (followBoard.getSoldiers()[row][column + 2] == null) {
                        if (followBoard.getSoldiers()[row][column + 3] != null) {
                            if (colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) {
                                if (followBoard.getSoldiers()[row][column + 3].getName().equals(SoldiersNames.WHITE_ROOK)) {
                                    result = true;
                                }
                            } else {
                                if (followBoard.getSoldiers()[row][column + 3].getName().equals(SoldiersNames.BLACK_ROOK)) {
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
        Soldier rook = followBoard.getSoldiers()[row][column + 3];
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
        followBoard.setSoldiers(rook,row,column + 1);
        followBoard.setSoldiers(null,row,column + 3);
        this.returnTheColorBack(this.chessBoard[row][column]);
        this.passOnAllTheSquaresAndLighteningOrReturnThemBack(squaresToReturnTheirColorBack, false);
        followBoard.setSoldiers(lastSoldier,row,column + 2);
        followBoard.setSoldiers(null,row,column);


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

    private Square searchForKingSquare (Color_Black_Or_White colorBlackOrWhite) {
        Square square = null;
        if (colorBlackOrWhite.equals(Color_Black_Or_White.WHITE)) {
            for (int i = 0; i < 8; i++) {
                int row = i;
                for (int j = 0; j < 8; j++) {
                    int column = j;
                    Soldier soldier = followBoard.getSoldiers()[row][column];
                    if (soldier != null) {
                        if (this.isFromTheSameColor(colorBlackOrWhite, soldier) && soldier.getName().equals(SoldiersNames.WHITE_KING)) {
                            square = new Square(row, column);
                            i = 8;
                            break;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int row = i;
                for (int j = 0; j < 8; j++) {
                    int column = j;
                    Soldier soldier = followBoard.getSoldiers()[row][column];
                    if (soldier != null) {
                        if (this.isFromTheSameColor(colorBlackOrWhite, soldier) && soldier.getName().equals(SoldiersNames.BLACK_KING)) {
                            square = new Square(row, column);
                            i = 8;
                            break;
                        }
                    }
                }
            }
        }
        return square;
    }

    private boolean isFromTheSameColor (Color_Black_Or_White colorBlackOrWhite, Soldier soldier) {
        if (soldier.getSoldierColor().equals(colorBlackOrWhite)) {
            return true;
        } else {
            return false;
        }
    }

    public Soldier getRightUpThreateningOpponentSoldier (Soldier king) {
        Soldier soldier = null;
        int row = king.getSquare().getRow() - 1;
        int column = king.getSquare().getColumn() + 1;
        while (row >= 0 && column <= 7) {
            if (FollowBoard.getSoldiers()[row][column] != null) {
                if (followBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_BISHOP)
                        || followBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_BISHOP)
                        || followBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.WHITE_QUEEN)
                        || followBoard.getSoldiers()[row][column].getName().equals(SoldiersNames.BLACK_QUEEN)) {
                    soldier = followBoard.getSoldiers()[row][column];
                }
            } else {
                if (row > 0 && column < 7) {
                    row --;
                    column ++;
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

    public List<Soldier> getAllTheOpponentSoldiers(Color_Black_Or_White colorBlackOrWhite) {
        List<Soldier> currentPossibleThreateningSoldiers = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int row = i;
            for (int j = 0; j < 8; j++) {
                int column = j;
                if (followBoard.getSoldiers()[row][column] != null) {
                    Soldier soldier = new Soldier(new Square(row,column),followBoard.getSoldiers()[row][column].getName());
                    if (!soldier.getSoldierColor().equals(colorBlackOrWhite)) {
                        currentPossibleThreateningSoldiers.add(soldier);
                    }
                }
            }
        }
        return currentPossibleThreateningSoldiers;
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
// if a soldier was already clicked (check if he can go to that square)
//                        passOnAllTheSquaresAndLighteningOrReturnThemBack(this.currentSoldierSquare);
//                        if (currentSoldier != null) {
//                    Square squareToMoveTheSoldierTo = null;
//                    List<Square> availableSquares = Soldier.filterNullSquares(lastSoldier.getAvailableSquaresToGoTo());
//                    Color_Black_Or_White colorBlackOrWhite = Color_Black_Or_White.WHITE;
//                    if (availableSquares != null) {
//                        for (Square square : availableSquares) {
//                            if (currentSoldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)) {
//                                colorBlackOrWhite = Color_Black_Or_White.BLACK;
//                            }
//                            if (!this.isFromTheSameColor(colorBlackOrWhite, currentSoldier)) {
//                                availableSquares.remove(square);
//                            }
//                        }
//                        for (Square square : availableSquares) {
//                            if (square.getRow() == currentSquare.getRow() &&
//                                    square.getColumn() == currentSquare.getColumn()) {
//                                squareToMoveTheSoldierTo = new Square(row, column);
//                                break;
//                            }
//                        }
//                        if (squareToMoveTheSoldierTo != null) {
//                            String imageURL = lastSoldier.getAddressName();
//                            System.out.println(imageURL);
//                            ImageIcon currentImageIcon = Utils.upscaleImage("src/ObjectPhotos/" + imageURL + ".png", 30, 30);
//                            chessBoard[currentSquare.getRow()][currentSquare.getColumn()].setIcon(currentImageIcon);
//                            chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()].setIcon(null);
//                            followBoard.setSoldiers(null, lastSoldier.getSquare().getRow(), lastSoldier.getSquare().getColumn());
//                            returnTheColorBack(chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
//                            lastSoldier.getSquare().setRow(row);
//                            lastSoldier.getSquare().setColumn(column);
//                            followBoard.setSoldiers(lastSoldier, row, column);
//                            this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
//                            lastSoldier = null;
//                            this.soldierClickedOnce = false;
//                        } else {
//                    if (followBoard.getSoldiers()[row][column] != null) {
//                        if (currentSoldier != null) {
//                            if (this.isFromTheSameColor(followBoard.getSoldiers()[row][column].getSoldierColor(), followBoard.getSoldiers()[row][column])) {
//                                returnTheColorBack(this.chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
//                                this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
//                                if (followBoard.getSoldiers()[row][column].getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
//                                    this.isTurnOfWhite = true;
//                                } else {
//                                    this.isTurnOfWhite = false;
//                                }
//                                this.soldierClickedOnce = false;
//                            } else {
//                                System.out.println("null");
//                                this.returnTheColorBack(this.chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
//                                this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
//
//                                this.soldierClickedOnce = true;
//                            }
//                        } else {
//                            returnTheColorBack(this.chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()]);
//                            this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
//                            if (lastSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
//                                this.isTurnOfWhite = true;
//                            } else {
//                                this.isTurnOfWhite = false;
//                            }
//                            this.soldierClickedOnce = false;
////                        }
//                            lastSoldier = null;
//                    if (this.soldierClickedOnce) {
//
////                        else {
////                            System.out.println("player in color "+ currentSoldier.getSoldierColor() + " can't play");
////                        }
//                        // if a soldier was already clicked (check if he can go to that square)
////                        passOnAllTheSquaresAndLighteningOrReturnThemBack(this.currentSoldierSquare);
////                        if (currentSoldier != null) {
//
//
//                        Square squareToMoveTheSoldierTo = null;
//                        List<Square> availableSquares = Soldier.filterNullSquares(this.lastSoldier.getAvailableSquaresToGoTo());
//                        if (availableSquares != null) {
//                            for (Square square : availableSquares) {
//                                if (square.getRow() == currentSquare.getRow() &&
//                                        square.getColumn() == currentSquare.getColumn()) {
//                                    squareToMoveTheSoldierTo = new Square(row, column);
//                                    break;
//                                }
//                            }
//                            if (squareToMoveTheSoldierTo != null) {
//                                String imageURL = lastSoldier.getAddressName();
//                                System.out.println(imageURL);
//                                ImageIcon currentImageIcon = Utils.upscaleImage("src/ObjectPhotos/" + imageURL + ".png", 30, 30);
//                                this.chessBoard[currentSquare.getRow()][currentSquare.getColumn()].setIcon(currentImageIcon);
//                                this.chessBoard[lastSoldier.getSquare().getRow()][lastSoldier.getSquare().getColumn()].setIcon(null);
//                                followBoard.setSoldiers(null, this.lastSoldier.getSquare().getRow(), this.lastSoldier.getSquare().getColumn());
//                                this.returnTheColorBack(this.chessBoard[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getColumn()]);
//                                lastSoldier.getSquare().setRow(row);
//                                lastSoldier.getSquare().setColumn(column);
//                                followBoard.setSoldiers(this.lastSoldier, row, column);
//                                this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
//
//
//                            } else {
//                                this.returnTheColorBack(this.chessBoard[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getColumn()]);
//                                lastSoldier = null;
//                                this.soldierClickedOnce = false;
//                                this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, false);
//                            }
//
//                        }
////                            }
////                        }
////                        if (squareToMoveTheSoldierTo != null) {
////
////                        }
//
////                        this.returnTheColorBack(this.chessBoard[this.lastSoldier.getSquare().getRow()][this.lastSoldier.getSquare().getRow()]);
//                        lastSoldier = null;
//                        this.soldierClickedOnce = false;
//                    } else {
//                        if (currentSoldier != null) {
//                            if ((this.isTurnOfWhite && currentSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE) ||
//                                    (!this.isTurnOfWhite && currentSoldier.getSoldierColor().equals(Color_Black_Or_White.BLACK)))) {
//
//                                List<Square> availableSquares = Soldier.filterNullSquares(currentSoldier.getAvailableSquaresToGoTo());
//
//                                this.soldierClickedOnce = true;
////                            this.currentSoldierSquare = new Square(row,column);
//                                lastSoldier = currentSoldier;
//                                if (currentSoldier.getSoldierColor().equals(Color_Black_Or_White.WHITE)) {
//                                    this.isTurnOfWhite = false;
//                                } else {
//                                    this.isTurnOfWhite = true;
//                                }
//                                System.out.println(currentSoldier);
//
//
//                                if (availableSquares != null) {
//                                    System.out.println(availableSquares.toString());
//                                    this.passOnAllTheSquaresAndLighteningOrReturnThemBack(availableSquares, true);
//                                    this.intensifyColorOfButton(this.chessBoard[row][column]);
//                                } else {
//                                    this.soldierClickedOnce = false;
//                                    lastSoldier = null;
//                                }
//
//
////                            System.out.println(currentSoldier.getAvailableSquaresToGoTo().toString());
//                            } else {
//                                System.out.println(currentSoldier.getSoldierColor().toString());
//                            }
//                        } else {
//                            System.out.println("null");
//                        }
//                        // click on soldier to attack with it
//                    }