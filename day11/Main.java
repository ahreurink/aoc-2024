package day11;

import java.util.Scanner;

public class Main {
  static Scanner input = new Scanner(System.in);

  static class Stone {
    long number;
    Stone next;

    Stone(int n) {
      this.number = n;
    }

    public String toString() {
      return "" + this.number;
    }
    public boolean equals(Object o) {
      return this.number == ((Stone) o).number;
    }
  }

  static Stone transform(Stone stone) {
    if(stone.number == 0) {
      stone.number += 1;
      return stone.next;
    }
    String digits = "" + stone.number;
    if(digits.length() % 2 == 0) {
      String leftHalfDigits = digits.substring(0, digits.length() / 2).toString();
      stone.number = Integer.parseInt(leftHalfDigits);
      Stone temp = stone.next;
      String rightHalfDigits = digits.substring(digits.length() / 2, digits.length()).toString();
      stone.next = new Stone(Integer.parseInt(rightHalfDigits));
      stone.next.next = temp;
      return stone.next.next;
    }
    stone.number = stone.number * 2024;
    return stone.next;
  }

  public static void main(String[] args) {
    Stone firstStone = null;
    Stone stone = null;
    while(input.hasNextLine()) {
      for(String number : input.nextLine().split(" ")) {
        if(firstStone == null) {
          firstStone = new Stone(Integer.parseInt(number)); 
          stone = firstStone; 
        }
        else {
          stone.next = new Stone(Integer.parseInt(number));
          stone = stone.next;
        }
      }
    }
    int limit = args[0].equals("1") ? 25 : 15;
    for(int i = 0; i < limit; i++) {
      stone = firstStone; 
      while(stone != null) {
        System.out.print(stone + ",");
        stone = transform(stone);
      }
      System.out.println();
    }
    int count = 0;
    stone = firstStone; 
    while(stone != null) {
      count++;
      stone = stone.next;
    }
    System.out.println(count);
  }
}
