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

    private void apllyDiagonalMoves(int gapRow, int gapColum, boolean[][] possibleMoves) {
        Position testMovePosition = new Position(position.getRow() + gapRow, position.getColumn() + gapColum);

        while (getBoard().positionExists(testMovePosition)) {
            if (!getBoard().thereIsAPiece(testMovePosition) || isThereOpponentPiece(testMovePosition)) {
                possibleMoves[testMovePosition.getRow()][testMovePosition.getColumn()] = true;
            }
            if (getBoard().thereIsAPiece(testMovePosition)) {
                break;
            }
            testMovePosition.setValues(testMovePosition.getRow() + gapRow, testMovePosition.getColumn() + gapColum);
        } 
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        apllyDiagonalMoves(-1, -1, possibleMoves);
        apllyDiagonalMoves(-1, 1, possibleMoves);
        apllyDiagonalMoves(1, -1, possibleMoves);
        apllyDiagonalMoves(1, 1, possibleMoves);

        return possibleMoves;
    }
}
