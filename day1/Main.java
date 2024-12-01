package day1;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.Math;
import java.lang.Integer;

public class Main {
  static Scanner input = new Scanner(System.in);

  public static void main(String[] args) { 
    List<Integer> leftList = new ArrayList<>();
    
    Map<Integer, Integer> similarityScores = new HashMap();   
  
    while(input.hasNextLine()) {
      String[] listElements = input.nextLine().split("   ");
      int list1Element = Integer.parseInt(listElements[0]);
      int list2Element = Integer.parseInt(listElements[1]);
      
      leftList.add(list1Element); 
      
      if(similarityScores.get(list2Element) == null) {
        similarityScores.put(list2Element, list2Element);
      }
      else {
        similarityScores.put(list2Element, 
                              similarityScores.get(list2Element) + list2Element);
      }
    }
    int result = leftList.stream()
      .mapToInt(e -> similarityScores.getOrDefault(e, 0))
      .sum();
    System.out.println("result = " + result);
  }

  public static void part1(String[] args) {
    List<String> leftList = new ArrayList<>();
    List<String> rightList = new ArrayList<>();

    while(input.hasNextLine()){
      String[] listElements = input.nextLine().split("   ");
      leftList.add(listElements[0]); 
      rightList.add(listElements[1]); 

    }
    System.out.println("L1=");
    leftList = leftList.stream().sorted().toList();
    System.out.println("L2=");
    rightList = rightList.stream().sorted().toList();
    
    int sum = 0;
    for(int i = 0; i<leftList.size(); i++){
      sum += Math.abs(new Integer(leftList.get(i)) - 
                     new Integer(rightList.get(i))); 
    }

    System.out.println("Total = " + sum);

  }
}
