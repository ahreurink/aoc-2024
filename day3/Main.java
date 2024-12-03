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

  public static void part1(String[] args) {
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

  public static void main(String[] args) {
    int sum = 0;
    String validMul = "mul\\(\\d+,\\d+\\)";
    String validDo = "do\\(\\)";
    String validDont = "don't\\(\\)";
    String validInstr = validMul + "|" + validDo + "|" + validDont;
    boolean doing = true;
    while(input.hasNextLine()) {
      String instr = input.findInLine(validInstr);
      System.out.println("Found instruction = " + instr);
      while(instr != null) {
        if(instr.matches(validDo))
          doing = true;
        else if(instr.matches(validDont))
          doing = false;
        else if(doing)
          sum += execute(instr); 
        instr = input.findInLine(validInstr);
      }
      input.nextLine();
    } 
    System.out.println(sum);
  }
}
