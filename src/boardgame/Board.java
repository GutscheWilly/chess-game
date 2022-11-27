package boardgame;

import boardgame.exceptions.BoardException;

public class Board {
    
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Invalid size of board! It must have at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Position doesn't exist!");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position doesn't exist!");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on this position -> " + position);
        }
        piece.position = position;
        pieces[position.getRow()][position.getColumn()] = piece;
    }

    public Piece removePiece(Position position) {
        if (!thereIsAPiece(position)) {
            return null;
        }
        Piece pieceRemoved = piece(position);
        pieceRemoved.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return pieceRemoved;
    }

    public boolean thereIsAPiece(Position position) {
        return piece(position) != null;
    }
}
