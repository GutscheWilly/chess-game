package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString() {
        return "B";
    }

    private boolean validateMoveForBishop(Position position) {
        return getBoard().positionExists(position) && (!getBoard().thereIsAPiece(position) || isThereOpponentPiece(position));
    }

    private void applyDiagonalMoves(int gapRow, int gapColumn, boolean[][] possibleMoves) {
        Position testMovePosition = new Position(position.getRow() + gapRow, position.getColumn() + gapColumn);

        while (validateMoveForBishop(testMovePosition)) {
            possibleMoves[testMovePosition.getRow()][testMovePosition.getColumn()] = true;

            if (getBoard().thereIsAPiece(testMovePosition)) {
                break;
            }
            
            testMovePosition.setValues(testMovePosition.getRow() + gapRow, testMovePosition.getColumn() + gapColumn);
        } 
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        applyDiagonalMoves(-1, -1, possibleMoves);
        applyDiagonalMoves(-1, 1, possibleMoves);
        applyDiagonalMoves(1, -1, possibleMoves);
        applyDiagonalMoves(1, 1, possibleMoves);

        return possibleMoves;
    }
}
