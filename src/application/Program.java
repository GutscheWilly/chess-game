package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import application.console.UI;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

public class Program {
    public static void main(String[] args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch);
                System.out.print("\nSource: ");
                ChessPosition sourcePosition = UI.readChessPosition(scanner);
                boolean[][] possibleMoves = chessMatch.possibleMoves(sourcePosition);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.print("\nTarget: ");
                ChessPosition targetPosition = UI.readChessPosition(scanner);
                ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, targetPosition);
            } 
            catch (InputMismatchException erro) {
                UI.printErro(erro, scanner);
            } 
            catch (ChessException erro) {
                UI.printErro(erro, scanner);
            }
        }
    }
}
