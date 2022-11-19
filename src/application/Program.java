package application;

import boardgame.Position;

public class Program {
    public static void main(String[] args) throws Exception {
        
        Position positionTest = new Position(2, 5);
        System.out.println(positionTest);
        positionTest.setValues(10, 20);
        System.out.println(positionTest);
    }
}
