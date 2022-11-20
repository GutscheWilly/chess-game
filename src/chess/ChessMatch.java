package chess;

import boardgame.Board;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.*;

public class ChessMatch {
    
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    private void initialSetup() {
        board.placePiece(new Rook(board, Color.WHITE), new Position(7, 0));
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0 ; i < board.getRows() ; i++) {
            for (int j = 0 ; j < board.getColumns() ; j++) {
                matrix[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return matrix;
    }
}
