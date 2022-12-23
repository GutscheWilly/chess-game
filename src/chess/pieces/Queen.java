package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Queen extends ChessPiece {

    public Queen(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString() {
        return "Q";
    }

    private boolean validateMoveForQueen(Position position) {
        return getBoard().positionExists(position) && (!getBoard().thereIsAPiece(position) || isThereOpponentPiece(position));
    }

    private void applyDiagonalMoves(int gapRow, int gapColumn, boolean[][] possibleMoves) {
        Position testMovePosition = new Position(position.getRow() + gapRow, position.getColumn() + gapColumn);

        while (validateMoveForQueen(testMovePosition)) {
            possibleMoves[testMovePosition.getRow()][testMovePosition.getColumn()] = true;

            if (getBoard().thereIsAPiece(testMovePosition)) {
                break;
            }
            
            testMovePosition.setValues(testMovePosition.getRow() + gapRow, testMovePosition.getColumn() + gapColumn);
        } 
    }

    private void applyHorizontalMoves(int gapColumn, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow(), position.getColumn() + gapColumn);

        while (validateMoveForQueen(testPosition)) {
            possibleMoves[testPosition.getRow()][testPosition.getColumn()] = true;

            if (getBoard().thereIsAPiece(testPosition)) {
                break;
            }

            testPosition.setValues(testPosition.getRow(), testPosition.getColumn() + gapColumn);
        }
    }

    private void applyVerticalMoves(int gapRow, boolean[][] possibleMoves) {
        Position testPosition = new Position(position.getRow() + gapRow, position.getColumn());

        while (validateMoveForQueen(testPosition)) {
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

        applyDiagonalMoves(-1, -1, possibleMoves);
        applyDiagonalMoves(-1, 1, possibleMoves);
        applyDiagonalMoves(1, -1, possibleMoves);
        applyDiagonalMoves(1, 1, possibleMoves);

        applyHorizontalMoves(1, possibleMoves);
        applyHorizontalMoves(-1, possibleMoves);
        applyVerticalMoves(1, possibleMoves);
        applyVerticalMoves(-1, possibleMoves);

        return possibleMoves;
    }
}
