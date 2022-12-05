package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.*;

public class ChessMatch {
    
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    public void initialSetup() {
        // Black pieces:
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Horse(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 8, new Queen(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Horse(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        for (char i = 'a' ; i <= 'h' ; i++) {
            placeNewPiece(i, 7, new Pawn(board, Color.BLACK));
        }

        // White pieces:
        for (char i = 'a' ; i <= 'h' ; i++) {
            placeNewPiece(i, 2, new Pawn(board, Color.WHITE));
        }
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Horse(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
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

    private void validateSourcePosition(Position sourcePosition) {
        if (!board.thereIsAPiece(sourcePosition)) {
            throw new ChessException("There isn't a piece on source position!");
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

    private Piece makeMove(Position source, Position target) {
        Piece pieceOnTarget = board.removePiece(target);
        board.placePiece(board.removePiece(source), target);
        return pieceOnTarget;
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        validateSourcePosition(source);
        Position target = targetPosition.toPosition();
        validateTargetPosition(target, (ChessPiece)board.piece(source));
        Piece pieceCaptured = makeMove(source, target);
        return (ChessPiece)pieceCaptured;
    }
}
