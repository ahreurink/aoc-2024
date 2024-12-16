package day15;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Optional; 

public class Main {
  static Scanner input = new Scanner(System.in);
  static List<List<Character>> map = new ArrayList<>();
  static List<Character> instructions = new ArrayList();

  static record Coordinate(int x, int y){ };

  static int startX = 0;
  static int startY = 0;

  static int x = 0;
  static int y = 0;

  static void parseInput() {
    while(input.hasNextLine()){
      List<Character> lineList = new ArrayList();
      for(char c : input.nextLine().toCharArray()) {
        lineList.add(c);
      }
      if(lineList.size() == 0)
        continue;
      if(lineList.get(0) == '#') {
        map.add(lineList);
        if(lineList.contains('@')) {
          startY = map.size() - 1;
          startX = lineList.indexOf('@');
        }
      }
      else
        instructions.addAll(lineList);
    }
  }

  static Optional<Coordinate> findNextCoordinate(char instruction, Coordinate current) {
    return switch(instruction) {
      case '^' -> map.get(current.y).get(current.x - 1) != '#' ? 
        Optional.of(new Coordinate(current.x - 1, current.y)) : Optional.empty();
      case 'v' -> map.get(current.y).get(current.x + 1) != '#' ?
        Optional.of(new Coordinate(current.x + 1, current.y)) : Optional.empty();
      case '<' -> map.get(current.y - 1).get(current.x) != '#' ? 
        Optional.of(new Coordinate(current.x, current.y - 1)) : Optional.empty();
      case '>' -> map.get(current.y + 1).get(current.x) != '#' ?
        Optional.of(new Coordinate(current.x, current.y + 1)) : Optional.empty();
      default -> throw new RuntimeException("Invalid instruction");
    };
  }

  static boolean moveBoxes(char instruction) {
    Coordinate box = findNextCoordinate(instruction, new Coordinate(x, y)).get();
    while(map.get(box.y).get(box.x) == 'O') {
      Optional<Coordinate> next = findNextCoordinate(instruction, box);
      if(next.isEmpty()) {
        return false;
      }
      box = next.get();
    }
    if(map.get(box.y).get(box.x) != '.')
      throw new RuntimeException("Box line ends with something other than #, 0, . : pos = " + map.get(box.y).get(box.x));
    map.get(box.y).set(box.x, 'O');
    map.get(y).set(x, '@');
    return true;
  }

  static void moveRobot(int xx, int yy, Coordinate next) {
    map.get(next.y).set(next.x, '@');
    map.get(yy).set(xx, '.');
    x = next.x;
    y = next.y;
  }

  static void execute(char instruction) {
    Optional<Coordinate> next = findNextCoordinate(instruction, new Coordinate(x, y));
    if(next.isEmpty()) {
      return;
    }
    
    char token = map.get(next.get().y).get(next.get().x);
    if(token == '.') {
      moveRobot(x, y, next.get());
    }
    else if(token == 'O' && moveBoxes(instruction)) {
      moveRobot(x, y, next.get());
    }
  }

  static void renderMap(char x) {
    try {
      System.out.println("\033[H\033[2J"); 
    System.out.println("Instruction = " + x);
      map.forEach(System.out::println); 
      Thread.sleep(500);
    } 
    catch(InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    parseInput();
    if(args[0].equals("1")) {
      x = startX;
      y = startY;
      instructions.stream()
        .peek(Main::renderMap)
        .forEach(instruction -> execute(instruction));
      int sum = 0;
      for(int y = 0; y < map.size(); y++) {
        for(int x = 0; x < map.get(y).size(); x++) {
          if(map.get(y).get(x) == 'O') {
            sum += x;
            sum += y * 100;
          }
        }
      }

      renderMap('O');
      System.out.println(sum);
    }
    else {
      System.out.println("Not ready for part 2");
    }
  }
}
