package day2;

import java.util.Scanner;
import java.util.Arrays;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

public class Main {
  static Scanner input = new Scanner(System.in);

  static boolean isIncreasing(int prev, int current) {
    int difference = current - prev;
    boolean result = difference > 0 && difference < 4;
    return result;
  }
  
  static boolean isDecreasing(int prev, int current) {
    int difference = current - prev;
    boolean result = difference < 0 && difference > -4;
    return result;
  }

  public static boolean checkLevel(String[] level) { 
    int previousStep = Integer.MAX_VALUE; 
    boolean isIncreasing = true;
    boolean isDecreasing = true;
    for(int i = 0; i < level.length; i++) {
      int step = Integer.parseInt(level[i]);
      if(i==0) {
        previousStep = step;
        continue;
      }

      assert previousStep != Integer.MAX_VALUE;
      int difference = step - previousStep;
      if(isIncreasing && difference > 0 && difference < 4) {
        previousStep = step;
      }
      else {
        isIncreasing = false;
      }

      if(isDecreasing && difference < 0 && difference > -4) {
        previousStep = step;
      }
      else {
        isDecreasing = false;
      }
      if(!isIncreasing && !isDecreasing) {
        break;
      }
    }
    return isIncreasing || isDecreasing;  
  }

  public static void part1(String[] args) { 
    int safeLevels = 0; 
    while(input.hasNextLine()) {
      String[] level = input.nextLine().split(" ");
      safeLevels += checkLevel(level) ? 1 : 0; 
    }
    System.out.println("Safe levels = " + safeLevels); 
  }

  public static void main(String[] args) { 
    int safeLevels = 0; 
    while(input.hasNextLine()) {
      String[] level = input.nextLine().split(" ");
      if(checkLevel(level)) {
        safeLevels += 1;
        continue;
      }
      for(int i = 0; i < level.length; i++) {
        String[] croppedLevel = new String[level.length - 1];
        for(int j = 0; j < level.length - 1; j++) {
          if(j < i)
            croppedLevel[j] = level[j];
          else
            croppedLevel[j] = level[j+1];
        }
        if(checkLevel(croppedLevel)) {
          safeLevels += 1;
          System.out.println("Safe level by cropping");
          break;
        }
      }
    }
    System.out.println("Safe levels = " + safeLevels); 
  }
}
