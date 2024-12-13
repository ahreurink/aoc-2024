package day13;

import java.util.Scanner;

public class Main {
  static Scanner input = new Scanner(System.in);

  static class Coordinate {
    long x;
    long y;

    Coordinate(long x, long y) {
      this.x = x;
      this.y = y;
    }

    public String toString() {
      return this.x + "," + this.y;
    }
  }

  static Coordinate parseLine(String inputLine) {
    System.out.println(inputLine);
    String[] xY = inputLine.split(", ");
    String[] xPart = xY[0].split("X");  
    long x = Long.parseLong(xPart[1].substring(1));
    long y = Long.parseLong(xY[1].substring(2));
    return new Coordinate(x, y);
  }

  static long calculateMinCoins(Coordinate A, Coordinate B, Coordinate prize) {
    long aMax = Math.min(prize.x / A.x, prize.y / A.y);
    long bMax = Math.min(prize.x / B.x, prize.y / B.y);
    long cost = Long.MAX_VALUE;
    for(int a = 0; a <= aMax; a++) {
      for(int b = 0; b<= bMax; b++) {
        if(a*A.x + b*B.x == prize.x && a*A.y + b*B.y == prize.y &&
            3*a + b < cost) {
          cost = 3*a + b; 
        }
      }
    }
    if(cost == Long.MAX_VALUE)
      return 0;
    System.out.println("Min coins = " + cost);
    return cost;
  }
  
  public static void main(String[] args) { 
    int coins = 0;
    while(input.hasNextLine()) {
      Coordinate buttonAConfig = parseLine(input.nextLine());
      Coordinate buttonBConfig = parseLine(input.nextLine());
      Coordinate prizeLocation = parseLine(input.nextLine());
      if(args[0].equals("2")) {
        prizeLocation.x += 10000000000000l;
        prizeLocation.y += 10000000000000L;
      }
      if(input.hasNextLine())
        input.nextLine();
      coins += calculateMinCoins(buttonAConfig, buttonBConfig, prizeLocation);
    }
    System.out.println(coins);
  }
}
