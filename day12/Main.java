package day12;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.stream.Collectors;


class Coordinate {
  int x;
  int y;
  char type;

  Coordinate(int x, int y, char type) {
    this.x = x;
    this.y = y;
    this.type = type;
  }

  boolean withinBounds() {
    return (x >= 0 && x < Main.map.get(0).size() 
         && y >= 0 && y < Main.map.size());
  }

  boolean adjacent(Coordinate other) {
    boolean verticalAdjacent = (this.x == other.x) && 
        (((this.y - 1) == other.y) || ((this.y + 1) == other.y));
    boolean horizontalAdjacent = (this.y == other.y) && 
        (((this.x - 1) == other.x) || ((this.x + 1) == other.x));
    return verticalAdjacent || horizontalAdjacent;
  }

  public String toString() {
    return "[" + x + "," + y + "," + type + "]";
  }

  public boolean equals(Object c) {
    return this.x == ((Coordinate) c).x && this.y == ((Coordinate) c).y;
  }

  public int hashCode() {
    return this.x + this.y;
  }
}

public class Main {
  public static List<List<Character>> loadMap(Scanner input) {
    List<List<Character>> map = new ArrayList<>();
    while(input.hasNextLine()) {
      map.add(new ArrayList<Character>());
      for(char c : input.nextLine().toCharArray()) {
          map.get(map.size()-1).add(c);    
      }
    }
    return map;
  }

  static void mergeRegions(List<List<Coordinate>> regions) {
    for(int i = 0; i < regions.size(); i++) {
      for(int j = i + 1; j < regions.size(); j++) {
        if(regions.get(j).size() == 0 || regions.get(i).size() == 0)
          continue;
        List<Coordinate> originalRegion = regions.get(i);
        List<Coordinate> comparedRegion = regions.get(j);
        if(originalRegion.get(0).type != comparedRegion.get(0).type)
          continue;
        if(originalRegion.stream()
             .anyMatch(plant1 -> comparedRegion.stream().anyMatch(plant2 -> plant2.adjacent(plant1)))) {
          originalRegion.addAll(comparedRegion);
          regions.set(j, new ArrayList<Coordinate>());
        }
      }
    }
    for(int i = regions.size() - 1; i >= 0; i--) {
      if(regions.get(i).size() == 0) {
        regions.remove(i); 
      }
    }
  }

  static int calculateCircumference(List<Coordinate> region) {
    int circumference = 0;
    for(Coordinate plant : region) {
      circumference += region.contains(new Coordinate(plant.x-1, plant.y, 'x')) ? 0 : 1;
      circumference += region.contains(new Coordinate(plant.x+1, plant.y, 'x')) ? 0 : 1;
      circumference += region.contains(new Coordinate(plant.x, plant.y-1, 'x')) ? 0 : 1;
      circumference += region.contains(new Coordinate(plant.x, plant.y+1, 'x')) ? 0 : 1;
    }
    return circumference;
  }

  static List<List<Character>> map;

  public static void main(String[] args) { 
    map = loadMap(new Scanner(System.in));
    List<List<Coordinate>> regions = new ArrayList<>();
    if(args[0].equals("1")) {
      System.out.println(map);
      for(int y = 0; y < map.size(); y++) {
        for(int x = 0; x < map.get(y).size(); x++) {
          Coordinate newCoordinate = new Coordinate(x, y, map.get(y).get(x)); 
          boolean partOfRegion = false;
          for(List<Coordinate> region : regions) {
            if(region.get(0).type == newCoordinate.type && 
               region.stream().anyMatch(c -> c.adjacent(newCoordinate))) {
              if(!partOfRegion) {
                region.add(newCoordinate);
                partOfRegion = true;
              }
            }
          }
          if(!partOfRegion) {
            List<Coordinate> newRegion = new ArrayList<>();
            newRegion.add(newCoordinate);
            regions.add(newRegion);
          }
        }
      }
      mergeRegions(regions);
      int sum = 0;
      for(List<Coordinate> region : regions) {
        int area = region.size();
        int circumference = calculateCircumference(region);
        System.out.println("For " + region.get(0).type + " = " + area + " + " + circumference);
        sum += area * circumference; 
      }
      System.out.println(sum);
    }
    else {
      System.out.println("Not ready for part 2");
    }
  }
}
