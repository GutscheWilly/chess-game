package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.*;

public class ChessMatch {
    
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private List<ChessPiece> piecesOnTheBoard;
    private List<ChessPiece> capturedPieces;
    private Board board;

    public ChessMatch() {
        turn = 1;
        currentPlayer = Color.WHITE;
        check = false;
        checkMate = false;
        piecesOnTheBoard = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        board = new Board(8, 8);
        initialBoardSetup();
    }

    public int getTurn() {
        return turn;
    }

    private void increaseTurn() {
        turn++;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private void changeCurrentPlayer() {
        currentPlayer = opponentColor(currentPlayer);
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public List<ChessPiece> getPiecesOnTheBoard() {
        return piecesOnTheBoard;
    }

    public List<ChessPiece> getCapturedPieces() {
        return capturedPieces;
    }

    private Color opponentColor(Color color) {
        if (color == Color.WHITE) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    private ChessPiece getKing(Color color) {
        List<ChessPiece> pieces = piecesOnTheBoard.stream().filter(piece -> piece.getColor() == color).collect(Collectors.toList());
        for (ChessPiece piece : pieces) {
            if (piece instanceof King) {
                return piece;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board!");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = getKing(color).getChessPosition().toPosition();
        List<ChessPiece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> piece.getColor() == opponentColor(color)).collect(Collectors.toList());
        for (ChessPiece opponentPiece : opponentPieces) {
            if (opponentPiece.possibleMove(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (testCheck(color)) {
            List<ChessPiece> allyPieces = piecesOnTheBoard.stream().filter(piece -> piece.getColor() == color).collect(Collectors.toList());

            for (ChessPiece allyPiece : allyPieces) {
                boolean[][] possibleMoves = allyPiece.possibleMoves();
                for (int i = 0 ; i < board.getRows() ; i++) {
                    for (int j = 0 ; j < board.getColumns() ; j++) {
                        if (possibleMoves[i][j]) {
                            Position source = allyPiece.getChessPosition().toPosition();
                            Position target = new Position(i, j);
                            ChessPiece capturedPiece = makeMove(source, target);
                            boolean testCheck = testCheck(color);
                            undoMove(source, target, capturedPiece);
                            if (!testCheck) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        if (piece != null) {
            board.placePiece(piece, new ChessPosition(column, row).toPosition());
            piecesOnTheBoard.add(piece);
        }
    }

    public void initialBoardSetup() {
        //Black pieces:
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Horse(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Horse(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        for (char i = 'a' ; i <= 'h' ; i++) {
            placeNewPiece(i, 7, new Pawn(board, Color.BLACK));
        }
        //White pieces:
        for (char i = 'a' ; i <= 'h' ; i++) {
            placeNewPiece(i, 2, new Pawn(board, Color.WHITE));
        }
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Horse(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Horse(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0 ; i < board.getRows() ; i++) {
            for (int j = 0 ; j < board.getColumns() ; j++) {
                matrix[i][j] = (ChessPiece)board.piece(i, j);
            }
        }
        return matrix;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position source = sourcePosition.toPosition();
        validateSourcePosition(source);
        return board.piece(source).possibleMoves();
    }

    private boolean validateCurrentPlayer(ChessPiece piece) {
        return piece.getColor() == currentPlayer;
    }

    private void validateSourcePosition(Position sourcePosition) {
        if (!board.thereIsAPiece(sourcePosition)) {
            throw new ChessException("There isn't a piece on source position!");
        }
        if (!validateCurrentPlayer((ChessPiece)board.piece(sourcePosition))) {
            throw new ChessException("The chosen piece is not yours!");
        }
        if (!board.piece(sourcePosition).isThereAnyPossibleMove()) {
            throw new ChessException("There isn't any possible move for this piece!");
        }
    }

    private void validateTargetPosition(Position targetPosition, ChessPiece sourcePiece) {
        if (!sourcePiece.possibleMove(targetPosition)) {
            throw new ChessException("Target position is not valid for the piece chosen!");
        }
    }

    private ChessPiece makeMove(Position source, Position target) {
        ChessPiece sourcePiece = (ChessPiece)board.removePiece(source);
        ChessPiece pieceOnTarget = (ChessPiece)board.removePiece(target);
        board.placePiece(sourcePiece, target);
        sourcePiece.increaseMoveCount();
        capturePiece(pieceOnTarget);
        return pieceOnTarget;
    }

    private void makeMoveForRookInCastlingCase(Position source, Position target) {
        int gapColumn = target.getColumn() - source.getColumn();

        if (gapColumn == 2) {
            makeMove(new Position(source.getRow(), source.getColumn() + 3), new Position(source.getRow(), source.getColumn() + 1));
        }

        if (gapColumn == -2) {
            makeMove(new Position(source.getRow(), source.getColumn() - 4), new Position(source.getRow(), source.getColumn() - 1));
        }
    }

    public void performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(target, (ChessPiece)board.piece(source));

        if (board.piece(source) instanceof King) {
            makeMoveForRookInCastlingCase(source, target);
        }

        ChessPiece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);

            if (board.piece(source) instanceof King) {
                undoMoveForRookInCastlingCase(source, target);
            }

            throw new ChessException(("You can't put yourself in check!"));
        }

        check = testCheck(opponentColor(currentPlayer));
        checkMate = testCheckMate(opponentColor(currentPlayer));

        if (!checkMate) {
            increaseTurn();
            changeCurrentPlayer();
        }
    }

    private void capturePiece(ChessPiece piece) {
        if (piece != null) {
            piecesOnTheBoard.remove(piece);
            capturedPieces.add(piece);
        }
    }

    private void replacePieceOnTheBoard(ChessPiece piece) {
        if (piece != null) {
            capturedPieces.remove(piece);
            piecesOnTheBoard.add(piece);
        }
    }

    private void undoMove(Position source, Position target, ChessPiece capturedPiece) {
        ChessPiece targetPiece = (ChessPiece)board.removePiece(target);
        board.placePiece(targetPiece, source);
        targetPiece.decreaseMoveCount();
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
        }
        replacePieceOnTheBoard(capturedPiece);
    }

    private void undoMoveForRookInCastlingCase(Position source, Position target) {
        int gapColumn = target.getColumn() - source.getColumn();

        if (gapColumn == 2) {
            undoMove(new Position(source.getRow(), source.getColumn() + 3), new Position(source.getRow(), source.getColumn() + 1), null);
        }

        if (gapColumn == -2) {
            undoMove(new Position(source.getRow(), source.getColumn() - 4), new Position(source.getRow(), source.getColumn() - 1), null);
        }
    }
}
