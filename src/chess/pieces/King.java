package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
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

        return possibleMoves;
    }
}
