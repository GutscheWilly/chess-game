package application.console;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.enums.Color;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static ChessPosition readChessPosition(Scanner scanner) {
        try {
            String stringPosition = scanner.nextLine();
            char column = stringPosition.charAt(0);
            int row = Integer.parseInt(stringPosition.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException erro) {
            throw new InputMismatchException("Erro reading ChessPosition! Valid values are from a1 to h8!");
        }
    }

    public static void printPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print(ANSI_GREEN + "-" + ANSI_RESET);
        } else if (piece.getColor() == Color.WHITE) {
            System.out.print(ANSI_WHITE + piece + ANSI_RESET);
        } else {
            System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
        }
        System.out.print(" ");
    }
    
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0 ; i < pieces.length ; i++) {
            System.out.print(ANSI_PURPLE + "\t" + (pieces.length - i) + " " + ANSI_RESET);
            for (int j = 0 ; j < pieces.length ; j++) {
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println(ANSI_PURPLE + "\t  a b c d e f g h\n" + ANSI_RESET); 
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0 ; i < pieces.length ; i++) {
            System.out.print(ANSI_PURPLE + "\t" + (pieces.length - i) + " " + ANSI_RESET);
            for (int j = 0 ; j < pieces.length ; j++) {
                if (possibleMoves[i][j] == true) {
                    System.out.print(ANSI_BLUE_BACKGROUND);
                }
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println(ANSI_PURPLE + "\t  a b c d e f g h\n" + ANSI_RESET);
    }

    public static void printCapturedPieces(List<ChessPiece> capturedPieces) {
        List<ChessPiece> whitePieces = capturedPieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> blackPieces = capturedPieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

        System.out.println("Captured pieces:\n");
        System.out.print("# WHITE -> [ ");
        for (ChessPiece pieceCaptured : whitePieces) {
            printPiece(pieceCaptured);
        }
        System.out.println("]");
        System.out.print("# BLACK -> [ ");
        for (ChessPiece pieceCaptured : blackPieces) {
            printPiece(pieceCaptured);
        }
        System.out.println("]");
    }

    public static void printMatch(ChessMatch chessMatch) {
        printBoard(chessMatch.getPieces());
        printCapturedPieces(chessMatch.getCapturedPieces());
        System.out.printf("\nTurn: %d\nCurrent player: %s\n\n", chessMatch.getTurn(), chessMatch.getCurrentPlayer());
        if (chessMatch.getCheck()) {
            System.out.println("<<< CHECK! >>>\n");
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printErro(Exception erro, Scanner scanner) {
        System.out.println("\n" + erro.getMessage());
        System.out.print("Press enter to continue the game!");
        scanner.nextLine();
    }

    public static int printPromotionOptions(ChessPiece[][] pieces, Scanner scanner) {
        try {
            clearScreen();
            printBoard(pieces);
            System.out.println("\n<<< You must choose one of these options to promote your pawn >>>\n");
            System.out.println("1 - Rook | 2 - Horse | 3 - Bishop | 4 - Queen\n");
            System.out.print("Enter a number: ");
            int option = scanner.nextInt();
            return option;
        } catch (RuntimeException erro) {
            throw new InputMismatchException("Input erro!");
        }
    }

    public static void printEndGameMessage(ChessMatch chessMatch) {
        clearScreen();
        printBoard(chessMatch.getPieces());
        System.out.println("<<< CHECKMATE! " + chessMatch.getCurrentPlayer() + " PLAYER WON! >>>\n");
    }
}
