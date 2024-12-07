package day7;

import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.lang.Long;

public class Main {
  static Scanner input = new Scanner(System.in);

  static Set<Character> operators = Set.of('+', '*');

  static long apply(Character operator, long x, long y) {
    if(operator.equals('+')) 
      return x + y;
  
    if(operator.equals('*')) 
      return x * y;

    throw new RuntimeException("Unsupported operator : " + operator);
  }

  static boolean canBeCorrect(long[] operands, long answer) {
    if(operands.length == 1) {
      return operands[0] == answer;
    }
    for(Character operator : operators) {
      long[] newOperands = new long[operands.length - 1];
      newOperands[0] = apply(operator, operands[0], operands[1]);
      for(int i = 1; i < operands.length - 1; i++) {
        newOperands[i] = operands[i + 1];
      } 
      if(canBeCorrect(newOperands, answer)) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) { 
    long sum = 0;
    while(input.hasNextLine()) {
      String[] equation = input.nextLine().split(": ");
      long answer = Long.parseLong(equation[0]);
      String[] operandInput = equation[1].split(" ");
      long[] operands = new long[operandInput.length];
      for(int i = 0; i < operandInput.length ; i++) {
        operands[i] = Long.parseLong(operandInput[i]);
      }
      if(canBeCorrect(operands, answer)) {
        sum += answer; 
      }
    }
    System.out.println(sum);
  }
}
