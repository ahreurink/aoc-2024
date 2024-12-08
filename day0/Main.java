package day0;

import java.util.Scanner;

public class Main {
  static Scanner input = new Scanner(System.in);

  public static void main(String[] args) { 
    if(args[0].equals("1")) {
      while(input.hasNextLine())
        System.out.println(input.nextLine());
    }
    else {
      System.out.println("Not ready for part 2");
    }
  }
}
