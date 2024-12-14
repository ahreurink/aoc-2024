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
    double intermed0 = (double) B.x*prize.y / B.y;
    double intermed1 = prize.x - intermed0;
    double intermed2 = A.x - (double) A.y*B.x / B.y;
    long a = (long) Math.round((double) intermed1 / intermed2);
    long b = (long) Math.round((prize.y - A.y*a) / B.y);
    
    System.out.println(a + " " + b);
    if(a < 0 || b < 0 || (a*A.x + b*B.x) != prize.x || (a*A.y + b*B.y) != prize.y)
      return 0;
    System.out.println("Min coins = " + (long) ( 3*a + b ));
    return (long) (3 * a + b);
  }
  
  public static void main(String[] args) { 
    long coins = 0;
    while(input.hasNextLine()) {
      Coordinate buttonAConfig = parseLine(input.nextLine());
      Coordinate buttonBConfig = parseLine(input.nextLine());
      Coordinate prizeLocation = parseLine(input.nextLine());
      if(args[0].equals("2")) {
        prizeLocation.x += 10000000000000L;
        prizeLocation.y += 10000000000000L;
        if(prizeLocation.x < 0 || prizeLocation.y < 0) 
          throw new RuntimeException("Prize overlow");
      }
      if(input.hasNextLine())
        input.nextLine();
      coins += calculateMinCoins(buttonAConfig, buttonBConfig, prizeLocation);
    }
    System.out.println(coins);
  }
}
