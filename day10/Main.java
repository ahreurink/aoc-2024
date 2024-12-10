package day10;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class Main {
  static Scanner input = new Scanner(System.in);

  static class Coordinate {
    int x;
    int y;
    int height;

    Coordinate(int x, int y, int height) {
      this.x = x;
      this.y = y;
      this.height = height;
    }
    public String toString() {
      return "[" + x + "," + y + "," + height + "]";
    }
    boolean withinBounds() {
      return (x >= 0 && x < map.get(0).size() 
           && y >= 0 && y < map.size());
    }
  
    public boolean equals(Object c) {
      return this.x == ((Coordinate) c).x && this.y == ((Coordinate) c).y;
    }
    public int hashCode() {
      return this.x + this.y;
    }
  }
    
  static List<List<Integer>> map = new ArrayList<>();
  static Set<Coordinate> peaks = new HashSet<>();
  static Set<Coordinate> valleys = new HashSet<>();
  static Set<Coordinate> visitedCoordinates = new HashSet<>();
  static Set<Coordinate> reachablePeaks = new HashSet<>();
  static int reachedPeaks = 0; 

  static void move(Coordinate point, char direction, 
      Set<Coordinate> visitedCoordinatesForRun) {
    if(reachablePeaks.size() == peaks.size()) {
      return;
    }
    int newX = direction == '<' ? point.x - 1 : 
               direction == '>' ? point.x + 1 : point.x;
    int newY = direction == '^' ? point.y - 1 : 
               direction == 'v' ? point.y + 1 : point.y;

    Coordinate newCoordinate = new Coordinate(newX, newY, 0);
    if(!newCoordinate.withinBounds() || visitedCoordinatesForRun.contains(newCoordinate)) 
      return;

    newCoordinate.height = map.get(newY).get(newX);
    if(newCoordinate.height != point.height + 1)
      return;
    visitedCoordinatesForRun.add(newCoordinate);
    if(newCoordinate.height == 9) {
      if(part.equals("1"))
        reachablePeaks.add(newCoordinate);
      else
        reachedPeaks += 1;
      return;
    }

   tryAllDirections(newCoordinate, direction, visitedCoordinatesForRun);
  }

  static void tryAllDirections(Coordinate point, char previousDirection,
      Set<Coordinate> visitedCoordinatesForRun) {
    move(point, '^', new HashSet<Coordinate>(visitedCoordinatesForRun));
    move(point, 'v', new HashSet<Coordinate>(visitedCoordinatesForRun));
    move(point, '<', new HashSet<Coordinate>(visitedCoordinatesForRun));
    move(point, '>', new HashSet<Coordinate>(visitedCoordinatesForRun));
  }

  static int countPeaks(Coordinate startPoint) {
    reachablePeaks = new HashSet<>();
    reachedPeaks = 0;
    visitedCoordinates = new HashSet<>();
    tryAllDirections(startPoint, 'x', visitedCoordinates);
    if(part.equals("1"))
      return reachablePeaks.size();
    else
      return reachedPeaks;
  }

  static String part = "";

  public static void main(String[] args) { 
    while(input.hasNextLine()) {
      map.add(new ArrayList<Integer>());
      for(char c : input.nextLine().toCharArray()) {
          map.get(map.size()-1).add((int) c - '0');    
      }
    }

    for(int i = 0; i < map.size(); i++) {
      for(int j = 0; j < map.get(i).size(); j++) {
        if(map.get(i).get(j) == 0)
          valleys.add(new Coordinate(j, i, 0));
        if(map.get(i).get(j) == 9)
          peaks.add(new Coordinate(j, i, 9));
      }
    }
    part = args[0];
    int sum = valleys.stream()
                .mapToInt(Main::countPeaks)
                .sum();    
    System.out.println(sum);
  }
}
