package day4;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
  static Scanner input = new Scanner(System.in);
  static List<List<Character>> puzzle = new ArrayList<>();
  static String target = "XMAS";
  static int targetSize = target.length();

  static boolean cantGoUp(int i) {
    return i - targetSize + 1 < 0;
  }
  
  static boolean cantGoDown(int i) {
    return i + targetSize > puzzle.size();
  }  

  static boolean cantGoLeft(int i, int j) {
    if(i == 9 && j == 3)
      System.out.println("j (" + j +  ") - targetSize (" + targetSize + ") = " + (j - targetSize));
    return j - targetSize + 1 < 0;
  }

  static boolean cantGoRight(int i, int j) {
    return j + targetSize > puzzle.get(i).size();
  }

  static boolean is(int i, int j, int k) {
    return puzzle.get(i).get(j).equals(target.charAt(k));
  }

  static boolean checkNorth(int i, int j) {
      if(cantGoUp(i)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i - k, j, k)){ 
          return false; 
        }
      }
      System.out.println("Found North at " + i + "," + j);
      return true;
  }
  
  static boolean checkNorthWest(int i, int j) {
      if(cantGoUp(i) || cantGoLeft(i, j)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i - k,j - k, k)){ 
          return false; 
        }
      }

      System.out.println("Found NorthWest at " + i + "," + j);
      return true;
  }
  
  static boolean checkNorthEast(int i, int j) {
      if(cantGoUp(i) || cantGoRight(i, j)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i - k, j + k, k)){ 
          return false; 
        }
      }

      System.out.println("Found NorthEast at " + i + "," + j);

      return true;
  }

  static boolean checkSouth(int i, int j) {
      if(cantGoDown(i)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i + k, j, k)){ 
          return false; 
        }
      }

      System.out.println("Found South at " + i + "," + j);

      return true;
  }

  static boolean checkSouthWest(int i, int j) {
      if(cantGoDown(i) || cantGoLeft(i, j)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i + k, j - k, k)){ 
          return false; 
        }
      }

      System.out.println("Found SouthWest at " + i + "," + j);
      return true;
  }

  static boolean checkSouthEast(int i, int j) {
      if(cantGoDown(i) || cantGoRight(i, j)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i + k, j + k, k)){ 
          return false; 
        }
      }

      System.out.println("Found SouthEast at " + i + "," + j);
      return true;
  }

  static boolean checkWest(int i, int j) {
      if(cantGoLeft(i, j)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i, j - k, k)){ 
          return false; 
        }
      }

      System.out.println("Found West at " + i + "," + j);
      return true;
  }

  static boolean checkEast(int i, int j) {
      if(cantGoRight(i, j)) {
        return false;
      }
      for(int k = 1; k < targetSize; k++) {
        if(!is(i, j + k, k)){ 
          return false; 
        }
      }
      System.out.println("Found East at " + i + "," + j);
      return true;
  }

  static int countXmas(int i, int j) {
    int count = 0;
    count += checkEast(i, j) ? 1 : 0;  
    count += checkWest(i, j) ? 1 : 0;  
    count += checkSouth(i, j) ? 1 : 0;  
    count += checkNorth(i, j) ? 1 : 0;  
    count += checkNorthEast(i, j) ? 1 : 0;  
    count += checkNorthWest(i, j) ? 1 : 0;  
    count += checkSouthEast(i, j) ? 1 : 0;  
    count += checkSouthWest(i, j) ? 1 : 0;  
    return count; 
  };
  
  public static void main(String[] args) { 
    while(input.hasNextLine()){
      puzzle.add(new ArrayList<Character>());
      input.nextLine().chars().forEach(i -> puzzle.getLast().add((char) i)); 
    }

    int count = 0;
    for(int i = 0; i < puzzle.size(); i++) {
      for(int j = 0; j < puzzle.get(i).size(); j++) {
        if(puzzle.get(i).get(j).equals('X')) {
          count += countXmas(i, j);  
        }
      }
    }
    System.out.println("XMAX count = " + count);
  }
}
