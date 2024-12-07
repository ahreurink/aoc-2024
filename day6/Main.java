package day6;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Main {
  static Scanner input = new Scanner(System.in);

  static List<List<Character>> map;
  static List<List<Set<Character>>> loopDetector;

  static class Coordinate {
    int i;
    int j;
    
    Coordinate(int x, int y) {
      i = x;
      j = y;
    }
  }

  static boolean cantGoUp(int i, int j) {
    boolean result = i - 1 < 0;
    if(result) {
      map.get(i).set(j, 'X');
    }
    return result;
  }
  
  static boolean cantGoDown(int i, int j) {
    boolean result = i + 1 > map.size() - 1;
    if(result) {
      map.get(i).set(j, 'X');
    }
    return result;
  }  

  static boolean cantGoLeft(int i, int j) {
    boolean result = j - 1 < 0;
    if(result) {
      map.get(i).set(j, 'X');
    }
    return result;
  }

  static boolean cantGoRight(int i, int j) {
    boolean result = j + 1 > map.get(i).size() - 1;
    if(result) {
      map.get(i).set(j, 'X');
    } 
    return result;
  }

  static int goUp(int i, int j) {
    if(loopDetector.get(i - 1).get(j).contains('^')) {
      loopFound = true;
    }
    if(map.get(i - 1).get(j).equals('#')) {
      map.get(i).set(j, '>');
      loopDetector.get(i).get(j).add('>');
      return i;
    }
    else {
      map.get(i).set(j, 'X');
      map.get(i - 1).set(j, '^');
      loopDetector.get(i - 1).get(j).add('^');
      return i - 1;
    } 
  }

  static int goDown(int i, int j) {
    if(loopDetector.get(i + 1).get(j).contains('v')) {
      loopFound = true;
    }
    if(map.get(i + 1).get(j).equals('#')) {
      map.get(i).set(j, '<');
      loopDetector.get(i).get(j).add('<');
      return i;
    }
    else {
      map.get(i).set(j, 'X');
      map.get(i + 1).set(j, 'v');
      loopDetector.get(i + 1).get(j).add('v');
      return i + 1;
    }
  }

  static int goLeft(Integer i, Integer j) {
    if(loopDetector.get(i).get(j - 1).contains('<')) {
      loopFound = true;
    }
    if(map.get(i).get(j - 1).equals('#')) { 
      map.get(i).set(j, '^');
      loopDetector.get(i).get(j).add('^');
      return j;
    }
    else {
      map.get(i).set(j, 'X');
      map.get(i).set(j - 1, '<');
      loopDetector.get(i).get(j - 1 ).add('<');
      return j - 1;
    }
  }

  static int goRight(Integer i, Integer j) {
    if(loopDetector.get(i).get(j + 1).contains('>')) {
      loopFound = true;
    }
    if(map.get(i).get(j + 1).equals('#')) { 
      map.get(i).set(j, 'v');
      loopDetector.get(i).get(j).add('v');
      return j;
    }
    else {
      map.get(i).set(j, 'X');
      map.get(i).set(j + 1, '>');
      loopDetector.get(i).get(j + 1 ).add('>');
      return j + 1;
    }
  }

  static boolean checkBoundary(int i, int j) {
    return switch(map.get(i).get(j)) {
      case 'v' -> !cantGoDown(i, j);
      case '^' -> !cantGoUp(i, j);
      case '<' -> !cantGoLeft(i, j);
      case '>' -> !cantGoRight(i, j);
      default -> throw new RuntimeException("invalid substition occurred: can't go futher but not reached the end");
    };
  }

  static int findNewVertical(int i, int j) {
    return switch(map.get(i).get(j)) {
        case 'v' -> goDown(i, j);
        case '^' -> goUp(i, j);
        default -> i;
    };
  }
  
  static int findNewHorizontal(int i, int j) {
    return switch(map.get(i).get(j)) {
        case '<' -> goLeft(i, j);    
        case '>' -> goRight(i, j);  
        default -> j;
    };
  }

  static boolean calculatePath(int i, int j) {
    boolean canContinue = true;
    while(canContinue) { 
      canContinue = checkBoundary(i, j);
      if(!canContinue) {
        return false;
      }

      i = findNewVertical(i,j);
      j = findNewHorizontal(i,j);
    }
    throw new RuntimeException("Haven't found exit but cannot continue");
  }

  static boolean loopFound = false;

  static boolean calculateLoop(int i, int j) {
    boolean canContinue = true;
    while(canContinue) {
      canContinue = checkBoundary(i , j);

      
      if(!canContinue) {
        return false;
      }

      i = findNewVertical(i,j);
      j = findNewHorizontal(i,j);

      if(loopFound) {
        loopFound = false;
        return true;
      }
    }
    return false;
  }

  static List<List<Character>> buildMap() {
    List<List<Character>> newMap = new ArrayList<>(); 
    while(input.hasNextLine()) {
      newMap.add(new ArrayList<Character>()); 
      input.nextLine().chars()
            .forEach(c -> newMap.get(newMap.size()-1).add((char) c));
    }
    return newMap;
  }

  static List<List<Character>> copyMap(List<List<Character>> fromMap) {
    List<List<Character>> toMap = new ArrayList<>();
    fromMap.forEach(row -> toMap.add(new ArrayList(row)));
    return toMap;
  }

  static List<List<Set<Character>>> newLoopDetector() {
    List<List<Set<Character>>> detector = new ArrayList<>();
    map.forEach(row -> detector.add(new ArrayList()));

    for(int i = 0; i < map.get(0).size(); i++) {
      detector.forEach(row -> row.add(new HashSet<Character>()));
    }

    return detector;
  }

  static Coordinate getStartPoint() {
    int cols = map.get(0).size();
    for(int i = 0; i < map.size(); i++) {
      for(int j = 0; j < cols; j++) {
        Character startChar = map.get(i).get(j);
        if(startChar.equals('^') || startChar.equals('v') || startChar.equals('<') || startChar.equals('>')) {
          return new Coordinate(i,j);
        }
      }
    }
    throw new RuntimeException("Start point not found");
  }

  public static void part1(String[] args) {
    map = buildMap();
    Coordinate startPoint = getStartPoint();

    calculatePath(startPoint.i, startPoint.j);
    long num = map.stream().flatMap(List::stream).filter(c -> c.equals('X')).count();
    System.out.println(num);
  }

  public static void main(String[] args) { 
    map = buildMap();
    Coordinate startPoint = getStartPoint();
    
    List<List<Character>> originalMap = copyMap(map);

    int loops = 0;
    for(int i = 0; i < map.size(); i++) {
      for(int j = 0; j < map.get(0).size(); j++) {
        if(i == startPoint.i && j == startPoint.j) {
          continue; // skip start
        }
        map = copyMap(originalMap); 

        if(map.get(i).get(j).equals('#')) {
          continue; //Obstacle already exists
        }

        map.get(i).set(j, '#'); //Place obstacle

        loopDetector = newLoopDetector();
        
        if(calculateLoop(startPoint.i, startPoint.j)) {
          loops++;
        }
      } 
    }
    System.out.println(loops);
  }
}
