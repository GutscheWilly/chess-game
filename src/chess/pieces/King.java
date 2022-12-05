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

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece)getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position testPosition = new Position(0, 0);

        // Above:
        for (int i = -1 ; i < 2 ; i++) {
            testPosition.setValues(position.getRow() - 1, position.getColumn() + i);
            if (getBoard().positionExists(testPosition) && canMove(testPosition)) {
                possibleMoves[position.getRow() - 1][position.getColumn() + i] = true;
            }
        }

        // Below:
        for (int i = -1 ; i < 2 ; i++) {
            testPosition.setValues(position.getRow() + 1, position.getColumn() + i);
            if (getBoard().positionExists(testPosition) && canMove(testPosition)) {
                possibleMoves[position.getRow() + 1][position.getColumn() + i] = true;
            }
        }

        // Left:
        testPosition.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(testPosition) && canMove(testPosition)) {
            possibleMoves[position.getRow()][position.getColumn() - 1] = true;
        }

        // Right:
        testPosition.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(testPosition) && canMove(testPosition)) {
            possibleMoves[position.getRow()][position.getColumn() + 1] = true;
        }

        return possibleMoves;
    }
}
