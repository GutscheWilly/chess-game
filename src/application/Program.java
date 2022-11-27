package application;

import java.util.Scanner;

import application.console.UI;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
    public static void main(String[] args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.print("\nSource: ");
            ChessPosition sourcePosition = UI.readChessPosition(scanner);
            System.out.print("\nTarget: ");
            ChessPosition targetPosition = UI.readChessPosition(scanner);
            ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, targetPosition);
        }
    }
}
