package application;

import java.util.Scanner;

import application.console.UI;
import chess.ChessMatch;
import chess.ChessPosition;

public class Program {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch);
                System.out.print("Source: ");
                ChessPosition sourcePosition = UI.readChessPosition(scanner);
                boolean[][] possibleMoves = chessMatch.possibleMoves(sourcePosition);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.print("Target: ");
                ChessPosition targetPosition = UI.readChessPosition(scanner);
                chessMatch.performChessMove(sourcePosition, targetPosition);
            } 
            catch (Exception erro) {
                UI.printErro(erro, scanner);
            } 
        }

        UI.printEndGameMessage(chessMatch);
    }
}
