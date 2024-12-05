package day5;

import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Integer;
import java.util.Arrays;

public class Main {
  static Scanner input = new Scanner(System.in);

  static Map<Integer, List<Integer>> rules = new HashMap<>();

  static boolean correctOrder(List<Integer> pages) {
    for(int i = pages.size() - 1; i >= 0; i--) { // reverse loop through pages
      Integer lastPage = pages.get(i);
      List<Integer> pagesNotBeforeLastPage = rules.get(lastPage);
      for(int j = 0; j < i; j++) { // loop through all pages before last page
        Integer pageBeforeLast = pages.get(j);
        if(pagesNotBeforeLastPage != null && pagesNotBeforeLastPage.contains(pageBeforeLast))
          return false;
      }
    } 
    return true;
  }

  static int ruleBasedCompare(int x, int y) {
    if(rules.get(x) != null && rules.get(x).contains(y))
      return -1;
    return 0;
  }

  public static void main(String[] args) { 
    int sum = 0;
    int incorrectSum = 0;
    while(input.hasNextLine()) {
      String inputLine = input.nextLine();
      if(inputLine.matches("\\d\\d\\|\\d\\d\\s*")) {
        String[] numbers = inputLine.split("\\|");
        Integer number1 = Integer.parseInt(numbers[0]);
        Integer number2 = Integer.parseInt(numbers[1]);
        if(rules.get(number1) == null) 
          rules.put(number1, new ArrayList<Integer>());
        rules.get(number1).add(number2);
      }
       
      else if(!inputLine.matches("\\s*")) {
        List<Integer> pages = Arrays.stream(inputLine.split(","))
                      .map(Integer::parseInt)
                      .toList();
        if(correctOrder(pages)) {
         sum += pages.get(pages.size() / 2); 
        }
        else {
          List<Integer> correctedPages = new ArrayList<Integer>(pages);
          correctedPages.sort(Main::ruleBasedCompare);
          incorrectSum += correctedPages.get(correctedPages.size() / 2); 
        }
      }
    }
    System.out.println("Correct pages = " + sum);
    System.out.println("Corrected pages = " + incorrectSum);
  }
}
