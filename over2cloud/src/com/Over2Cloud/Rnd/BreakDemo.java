package com.Over2Cloud.Rnd;

public class BreakDemo
{
  public static void main(String args[]) {
    double data[][] = { {4.1, 3.2, 1.1},
                        {-1.3, 2.4, 6.7},
                        {7.7, 0.3, 9.8} };

    outer: for(int i=0; i<3; ++i) {
      for(int j=0; j<3; ++j) {
        System.out.println("i= " + i + " j= " + j);
        if (data[i][j] < 0.0) {
           System.out.println("negative value at" +
                 " [" + i + "][" + j + "]");
           break outer;
        }
      }
    }
  }
}


