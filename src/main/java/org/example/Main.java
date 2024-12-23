package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    Solution.readInputAndSaveData("src/main/resources/input.txt");
    Solution.setTilesCoveredByGuard();
    System.out.println(Solution.countTilesCoveredByGuard());
  }
}

class Solution {
  static List<List<Character>> data = new ArrayList<>();
  static char currentGuardFigure;
  static int[] startIndices = new int[2];
  static int width;
  static int height;
  public static void readInputAndSaveData(String path) {
    try(BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line;
      while((line = br.readLine()) != null) {

        List<Character> list = new ArrayList<>();
        for(Character c : line.toCharArray()) list.add(c);
        data.add(list);

        if(line.contains("^")) {
          startIndices[0] = data.size()-1;
          startIndices[1] = line.indexOf("^");
        }
      }
      currentGuardFigure = '^';
      width = data.get(0).size();
      height = data.size();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void setTilesCoveredByGuard() {
    int numTiles = 0;
    int i = startIndices[0];
    int j = startIndices[1];
    while (i >= 0 && j >= 0 && i <= height-1 && j <= width-1) {
      System.out.println(data.get(i).get(j));
      if (currentGuardFigure == '^') {
        // move upp, i--
        if(isValidPosition(i-1, j)) {
          char nextPlace = data.get(i-1).get(j);
          if(nextPlace == '#') {
            currentGuardFigure ='>';
            data.get(i).set(j, 'X');
          } else {
            data.get(i).set(j, 'X');
            i--;
          }
        } else break;//Next position ain't valid and mister guard is outside field
      } else if(currentGuardFigure == '>') {
        //move right, j++
        if(isValidPosition(i, j+1)) {
          char nextPlace = data.get(i).get(j+1);
          if(nextPlace == '#') {
            currentGuardFigure ='v';
            data.get(i).set(j, 'X');
          } else {
            data.get(i).set(j, 'X');
            j++;
          }
        } else break;//Next position aint valid and mister guard is outside field
      } else if(currentGuardFigure == 'v') {
        //Move down, i++
        if(isValidPosition(i+1, j)) {
          char nextPlace = data.get(i+1).get(j);
          if(nextPlace == '#') {
            currentGuardFigure ='<';
            data.get(i).set(j, 'X');
          } else {
            data.get(i).set(j, 'X');
            i++;
          }
        } else break;//Next position isnt valid and mister guard is outside field
      } else if(currentGuardFigure == '<') {
        //Move left, j--;
        if(isValidPosition(i, j-1)) {
          char nextPlace = data.get(i).get(j-1);
          if(nextPlace == '#') {
            currentGuardFigure ='^';
            data.get(i).set(j, 'X');
          } else {
            data.get(i).set(j, 'X');
            j--;
          }
        } else break;//Next position isnt valid and mister guard is outside field
      }
    }
  }

  private static boolean isValidPosition(int i, int j) {
    if (i < 0 || j < 0 || i > height-1 || j > width-1){
      return false;
    }
    return true;
  }

  public static int countTilesCoveredByGuard() {
    int numTiles = 0;
    for (List<Character> list:data) {
      for(Character character:list) {
        if (character == 'X') numTiles++;
      }
    }
    return numTiles;
  }

}