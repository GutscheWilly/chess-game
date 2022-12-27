package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class Pawn extends ChessPiece {

    ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }
    
    @Override
    public String toString() {
        return "P";
    }

    public int gapValueFromColor() {
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

        applyMovesEnPassant(gap, possibleMoves);

        return possibleMoves;
    }

    private int getRowOfEnPassant() {
        if (this.getColor() == Color.WHITE) {
            return 3;
        }
        return 4;
    }

    private boolean validateSidePosition(Position position) {
        if (getBoard().positionExists(position)) {
            ChessPiece piece = (ChessPiece)getBoard().piece(position);
            return piece != null && piece instanceof Pawn && piece.getMoveCount() == 1 && piece.getTurnOfFirstMoviment() == chessMatch.getTurn() - 1;
        }
        return false;
    }

    private void applyMovesEnPassant(int gapRow, boolean[][] possibleMoves) {
        if (this.position.getRow() == getRowOfEnPassant()) {

            if (validateSidePosition(new Position(this.position.getRow(), this.position.getColumn() + 1))) {
                possibleMoves[this.position.getRow() + gapRow][this.position.getColumn() + 1] = true;
            }

            if (validateSidePosition(new Position(this.position.getRow(), this.position.getColumn() - 1))) {
                possibleMoves[this.position.getRow() + gapRow][this.position.getColumn() - 1] = true;
            }
        }
    }
}
