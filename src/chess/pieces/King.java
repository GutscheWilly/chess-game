package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

    ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean validateMoveForKing(Position position) {
        return getBoard().positionExists(position) && (!getBoard().thereIsAPiece(position) || isThereOpponentPiece(position));
    }

    private void applyDiagonalMoves(int gapRow, int gapColumn, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow() + gapRow, position.getColumn() + gapColumn);
        
        if (validateMoveForKing(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;
        }
    }

    private void applyHorizontalMoves(int gapColumn, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow(), position.getColumn() + gapColumn);

        if (validateMoveForKing(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;
        }
    }

    private void applyVerticalMoves(int gapRow, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow() + gapRow, position.getColumn());

        if (validateMoveForKing(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        applyDiagonalMoves(1, 1, possibleMoves);
        applyDiagonalMoves(1, -1, possibleMoves);
        applyDiagonalMoves(-1, 1, possibleMoves);
        applyDiagonalMoves(-1, -1, possibleMoves);

        applyHorizontalMoves(1, possibleMoves);
        applyHorizontalMoves(-1, possibleMoves);

        applyVerticalMoves(1, possibleMoves);
        applyVerticalMoves(-1, possibleMoves);

        if (validateMoveCountsForCastling(this) && !chessMatch.getCheck()) {
            if (testKingSideCastling()) {
                applyHorizontalMoves(2, possibleMoves);
            }
            if (testQueenSideCastling()) {
                applyHorizontalMoves(-2, possibleMoves);
            }
        }

        return possibleMoves;
    }

    private boolean validateMoveCountsForCastling(ChessPiece chessPiece) {
        return chessPiece != null && chessPiece.getMoveCount() == 0;
    }

    private boolean testKingSideCastling() {
        Position testPosition = new Position(this.position.getRow(), this.position.getColumn() + 1);
        
        for (int i = 0 ; i < 2 ; i++) {
            if (getBoard().thereIsAPiece(testPosition)) {
                return false;
            }
            testPosition.setValues(testPosition.getRow(), testPosition.getColumn() + 1);
        }

        ChessPiece pieceAtSourcePositionOfRook = (ChessPiece)getBoard().piece(testPosition);

        return validateMoveCountsForCastling(pieceAtSourcePositionOfRook) && pieceAtSourcePositionOfRook instanceof Rook;
    }

    private boolean testQueenSideCastling() {
        Position testPosition = new Position(this.position.getRow(), this.position.getColumn() - 1);

        for (int i = 0 ; i < 3 ; i++) {
            if (getBoard().thereIsAPiece(testPosition)) {
                return false;
            }
            testPosition.setValues(testPosition.getRow(), testPosition.getColumn() - 1);
        }

        ChessPiece pieceAtSourcePositionOfRook = (ChessPiece)getBoard().piece(testPosition);

        return validateMoveCountsForCastling(pieceAtSourcePositionOfRook) && pieceAtSourcePositionOfRook instanceof Rook;
    }
}
