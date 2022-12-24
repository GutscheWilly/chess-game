package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString() {
        return "P";
    }

    private int gapValueFromColor() {
        if (getColor() == Color.WHITE) {
            return -1;
        }
        return 1;
    }

    private boolean canMoveForward(int gapRow, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow() + gapRow, position.getColumn());
        
        if (getBoard().positionExists(testPosition) && !getBoard().thereIsAPiece(testPosition)) {
            possibleMoves[position.getRow() + gapRow][position.getColumn()] = true;
            return true;
        }
        return false;
    }

    private boolean canCapturePiece(int gapRow, int gapColumn, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow() + gapRow, position.getColumn() + gapColumn);

        if (getBoard().positionExists(testPosition) && isThereOpponentPiece(testPosition)) {
            possibleMoves[position.getRow() + gapRow][position.getColumn() + gapColumn] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        int gap = gapValueFromColor();

        if (canMoveForward(gap, possibleMoves) && getMoveCount() == 0) {
            canMoveForward(2 * gap, possibleMoves);
        }
        
        canCapturePiece(gap, -gap, possibleMoves);
        canCapturePiece(gap, gap, possibleMoves);

        return possibleMoves;
    }
}
