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
    for(int i = 0; i < fileIds.size(); i++) {
      fileId = fileIds.get(index);
      if(fileIds.get(index) != -1) {
        sum += index * fileId;
      }
      index++;
    }
    return sum;
  }

  static int moveBlock(int position, List<Integer> fileIds, int startAt) {
    int id = fileIds.get(position);
    if(id != -1) {
      startAt = addToFront(id, fileIds, startAt, position);
    }
    return startAt;
  }

  static int findFreeSpot(int fromIndex, List<Integer> abstractDisk, int size) {
    for(int i = 1; i < fromIndex; i+=2) {
      int spaceToCheck = abstractDisk.get(i);
      if(spaceToCheck - size >= 0) {
        abstractDisk.set(fromIndex, 0);
        abstractDisk.set(i, spaceToCheck - size);
        abstractDisk.set(i-1, abstractDisk.get(i-1) + size);
        return i; 
      }
    }
    return -1;
  }

  static List<Integer> moveOnConcreteDisk(List<Integer> fileIds, List<Integer> abstractDisk, 
                                int freeIndex, int removedIndex, int size) {
    int freeIndexCalculated = -size; 
    int removedIndexCalculated = 0; 
    for(int i = 0; i < removedIndex; i++) {
      if(i < freeIndex)
        freeIndexCalculated += abstractDisk.get(i);
      removedIndexCalculated += abstractDisk.get(i);
    }
    for(int i = 0; i < size; i++) {
      fileIds.set(freeIndexCalculated + i, fileIds.get(removedIndexCalculated + i));
      fileIds.set(removedIndexCalculated + i, -1);
    }
    return fileIds;
  }

  public static void main(String[] args) {
    String disk = input.nextLine();
    List<Integer> abstractDisk = new ArrayList<>();
    for(char c : disk.toCharArray()) {
      abstractDisk.add((int) c - '0');
    }
    List<Integer> originalDisk = new ArrayList(abstractDisk);

    List<Integer> fileIds = loadDisk(disk, new ArrayList<>());
    int startAt = 0;
    if(args[0].equals("1")) {
      for(int i = fileIds.size() - 1; i >= 0; i--) {
        startAt = moveBlock(i, fileIds, startAt);
      }
    }
    else {
      for(int checkIndex = abstractDisk.size() - 1; checkIndex >= 0; checkIndex-=2) {
        int size = originalDisk.get(checkIndex);
        int freeIndex = findFreeSpot(checkIndex, abstractDisk, size);
        if(freeIndex != -1) {
          fileIds = moveOnConcreteDisk(fileIds, abstractDisk, freeIndex, checkIndex, size);
        }
      }
    }

    long checkSum = calculateCheckSum(fileIds);
    System.out.println(checkSum);
  }
}
