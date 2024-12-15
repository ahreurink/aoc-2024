package day14;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
  static Scanner input = new Scanner(System.in);

  static class Robot {
    long px;
    long py;

    long vx;
    long vy;

    Robot(long px, long py, long vx, long vy) {
      this.px = px;
      this.py = py;
      this.vx = vx;
      this.vy = vy;
    }

    int quadrant() {
      int midY = maxY / 2; 
      int midX = maxX / 2;
      if(py < midY && px < midX)
        return 1;
      if(py < midY && px > midX)
        return 2;
      if(py > midY && px < midX)
        return 3;
      if(py > midY && px > midX)
        return 4;
      return -1;
    }
    
    public boolean equals(Object other) {
      Robot otherBot = (Robot) other;
      return (otherBot.px == px && otherBot.py == py);
    }

    public String toString() {
      return "[" + this.px + "," + this.py + "|" + this.vx + "," + this.vy + "]";
    }
  }

  static void simulate(Robot robot, int time) {
    robot.px = (robot.px + robot.vx * time) % maxX;
    robot.px = robot.px < 0 ? maxX + robot.px : robot.px;

    robot.py = (robot.py + robot.vy * time) % maxY;
    robot.py = robot.py < 0 ? maxY + robot.py : robot.py;
  }

  static Robot parseLine(String inputLine) {
    String[] pv = inputLine.split(" ");
    String position = pv[0];
    String positions[] = position.substring(2).split(",");
    long px = Long.parseLong(positions[0]);
    long py = Long.parseLong(positions[1]);
    String velocity = pv[1];
    String velocities[] = velocity.substring(2).split(",");
    long vx = Long.parseLong(velocities[0]);
    long vy = Long.parseLong(velocities[1]);
    return new Robot(px, py, vx, vy);
  }

  static final int maxX = 101;
  static final int maxY = 103;

  public static void main(String[] args) throws InterruptedException { 
    List<Robot> robots = new ArrayList<>();
    while(input.hasNextLine())
      robots.add(parseLine((input.nextLine())));

    System.out.println(robots);    


    if(args[0].equals("1")) {
      robots.forEach(r -> simulate(r, 100));
      long i = robots.stream()
        .collect(Collectors.groupingBy(Robot::quadrant))
        .entrySet().stream()
        .filter(entry -> entry.getKey() > 0)
        .mapToInt(entry -> entry.getValue().size())
        .reduce(1, (a, b) -> a*b);
    }
    else {
      for(int i = 0; i < 20000; i++) {
         System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Time elapsed = " + i);
        for(long x = 0; x < 101; x++) {
          for(long y = 0; y < 101; y++) {
            if(robots.contains(new Robot(x, y, 0, 0)))
              System.out.print("\u2588");
            else
              System.out.print(" ");
          }
          System.out.println();
        }

        robots.forEach(r -> simulate(r, 1));
        
        Thread.sleep(200);
      }
    }
  }
}
