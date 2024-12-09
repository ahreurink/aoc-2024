package day9;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
  static Scanner input = new Scanner(System.in);

  public static List<Integer> loadDisk(String disk, List<Integer> fileIds) {
    boolean empty = false;
    int id = 0;
    for(char c : disk.toCharArray()) {
      for(int i = 0; i < ((int) c - '0'); i++) {
        fileIds.add(empty ? -1 : id);
      }
      if(!empty)
        id++;
      empty = !empty;
    }
    return fileIds;
  }

  static int addToFront(int id, List<Integer> fileIds, 
                        int startAt, int idSource) {
    for(int i = startAt; i < idSource; i++) {
      if(fileIds.get(i) == -1) {
        fileIds.set(i, id);
        fileIds.set(idSource, -1);
        return i;
      }
    }
    return idSource;
  }

  static long calculateCheckSum(List<Integer> fileIds) {
    long sum = 0;
    int fileId = 0;
    int index = 0;
    fileId = fileIds.get(index);
    while(fileId != -1) {
      sum += index * fileId;
      index++;
      fileId = fileIds.get(index);
    }
    return sum;
  }

  public static void main(String[] args) { 
    String disk = input.nextLine();
    List<Integer> fileIds = loadDisk(disk, new ArrayList<>());
    int startAt = 0;
    for(int i = fileIds.size() - 1; i >= 0; i--) {
      int id = fileIds.get(i);
      if(id != -1) {
        startAt = addToFront(id, fileIds, startAt, i);
      }
    }
    
    if(args[0].equals("1")) {
      long checkSum = calculateCheckSum(fileIds);
      System.out.println(fileIds);
      System.out.println(checkSum);
    }
    else {
      System.out.println("Not ready for part 2");
    }
  }
}
