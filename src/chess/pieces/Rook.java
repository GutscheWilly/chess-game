package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean validateMoveForRook(Position position) {
        return getBoard().positionExists(position) && (!getBoard().thereIsAPiece(position) || isThereOpponentPiece(position));
    }

    private void applyHorizontalMoves(int gapColumn, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow(), position.getColumn() + gapColumn);

        while (validateMoveForRook(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;

            if (getBoard().thereIsAPiece(testPosition)) {
                break;
            }

            testPosition.setValues(testPosition.getRow(), testPosition.getColumn() + gapColumn);
        }
    }

    private void applyVerticalMoves(int gapRow, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow() + gapRow, position.getColumn());

        while (validateMoveForRook(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;

            if (getBoard().thereIsAPiece(testPosition)) {
                break;
            }

            testPosition.setValues(testPosition.getRow() + gapRow, testPosition.getColumn());
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        applyHorizontalMoves(1, possibleMoves);
        applyHorizontalMoves(-1, possibleMoves);
        applyVerticalMoves(1, possibleMoves);
        applyVerticalMoves(-1, possibleMoves);

        return possibleMoves;
    }
}
