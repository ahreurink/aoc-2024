package day3;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.lang.Integer;


public class Main {
  static Scanner input = new Scanner(System.in);

  static int execute(String mul) {
    String commaSeparatedNumbers = mul.substring(4, mul.length()-1);
    String numbers[] = commaSeparatedNumbers.split(","); 
    return Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
  }

  public static void main(String[] args) {
    int sum = 0;
    String validMul = "mul\\(\\d+,\\d+\\)";
    while(input.hasNextLine()) {
      String mul = input.findInLine(validMul);
      while(mul != null) {
        sum += execute(mul); 
        mul = input.findInLine(validMul);
      }
      input.nextLine();
    } 
    System.out.println(sum);
  }
}
