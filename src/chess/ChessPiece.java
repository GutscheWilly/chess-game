package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;

public abstract class ChessPiece extends Piece {
    
    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
        moveCount = 0;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece opponentPiece = (ChessPiece)getBoard().piece(position);
        return opponentPiece != null && opponentPiece.getColor() != color;
    }
}
