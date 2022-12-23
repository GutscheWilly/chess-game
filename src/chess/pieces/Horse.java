package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Horse extends ChessPiece {

    public Horse(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString() {
        return "H";
    }

    private boolean validateMoveForHorse(Position position) {
        return getBoard().positionExists(position) && (!getBoard().thereIsAPiece(position) || isThereOpponentPiece(position));
    }

    private void applyMovesInL(int gapRow, int gapColumn, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow() + gapRow, position.getColumn() + 2 * gapColumn);
        
        if (validateMoveForHorse(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;
        }

        testPosition.setValues(testPosition.getRow() + gapRow, testPosition.getColumn() - gapColumn);

        if (validateMoveForHorse(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        applyMovesInL(1, 1, possibleMoves);
        applyMovesInL(1, -1, possibleMoves);
        applyMovesInL(-1, 1, possibleMoves);
        applyMovesInL(-1, -1, possibleMoves);

        return possibleMoves;
    }
}
