package day8;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main {
  static Scanner input = new Scanner(System.in);

  static int maxX = 0;
  static int maxY = 0;

  static Map<Character, List<Location>> antennae = new HashMap<>();
  static List<List<Character>> antiNodes = new ArrayList<>();

  static class Location {
    int x;
    int y;
    Location(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public String toString() {
      return "[" + x + "," + y + "]";
    }
    
    Location minus(Location other) {
      return new Location(x - other.x, y - other.y);
    }
  
    Location plus(Location other) {
      return new Location(x + other.x, y + other.y);
    }

    boolean withinBounds() {
      return (x >= 0 && x <= maxX && y >= 0 && y <= maxY);
    }
  
    List<Location> calculateAntiNodes(Location other) {
      Location difference = this.minus(other);
      List<Location> antiNodes = new ArrayList<>();
      Location firstAntiNode = this.plus(difference);
      if(firstAntiNode.withinBounds())
        antiNodes.add(firstAntiNode);
      Location secondAntiNode = other.minus(difference);
      if(secondAntiNode.withinBounds())
        antiNodes.add(secondAntiNode);
      return antiNodes;
    }

    void mark() {
      antiNodes.get(y).set(x, '#');
    }
  }


  static void processAntennae(List<Location> antennaeGroup) {
    for(int i = 0; i < antennaeGroup.size(); i++) {
      for(int j = i + 1; j < antennaeGroup.size(); j++) {
        List<Location> nodes = antennaeGroup.get(i).calculateAntiNodes(antennaeGroup.get(j));
        nodes.forEach(Location::mark);
      }
    }
  }

 static void traceAntennae(List<Location> antennaeGroup) {
    for(int i = 0; i < antennaeGroup.size(); i++) {
      for(int j = i + 1; j < antennaeGroup.size(); j++) {
        Location location = antennaeGroup.get(i);
        Location difference = location.minus(antennaeGroup.get(j));
        while(location.withinBounds()) {
          location.mark();
          location = location.minus(difference); 
        }
        location = antennaeGroup.get(i);
        while(location.withinBounds()) {
          location.mark();
          location = location.plus(difference); 
        }
      }
    }
  }

 public static void main(String[] args) { 
    int row = 0;
    while(input.hasNextLine()) {
      String rowFrequencies = input.nextLine();
      maxX = rowFrequencies.length() - 1;
      for(int col = 0; col < rowFrequencies.length(); col++) {
        char frequency = rowFrequencies.charAt(col);
        if(frequency == '.')
          continue;
        if(antennae.get(frequency) == null) {
          antennae.put(frequency, new ArrayList<Location>());
        }
        antennae.get(frequency).add(new Location(col, row));
      }
      row++;
    }
    maxY = row - 1;
    for(int i = 0; i <= maxY; i++) {
      antiNodes.add(new ArrayList<Character>());
      for(int j = 0; j <= maxX; j++) {
        antiNodes.get(i).add('.');
      }
    }
    
    if(args[0].equals("1")) {
      antennae.values().forEach(antennae -> processAntennae(antennae));
    }
    else {
      antennae.values().forEach(antennae -> traceAntennae(antennae));
    }
    long nodes = antiNodes.stream().flatMap(s -> s.stream()).filter(c -> c == '#').count();
    System.out.println(nodes);
  }
}
