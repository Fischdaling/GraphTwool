package org.spengergasse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Calc {
    private final GraphTool graphTool;
    ArrayList<ArrayList<Point>> kanten;
    ArrayList<Point> knoten;
    private ArrayList<Integer> index1;
    private ArrayList<Integer> index2;
    private int[][][] potenzMatrix;

    public Calc(GraphTool graphTool) {
        this.graphTool = graphTool;

        index1 = graphTool.getIndex1();
        index2 = graphTool.getIndex2();

        this.knoten = graphTool.getKnoten();
        this.kanten = graphTool.getKanten();
        potenzMatrix = new int[getKnotenCounter()][getKnotenCounter()][getKnotenCounter()];
        printMatrix(AMatrix());
        AXMatrix();
        System.out.println();
        printMatrix(DMatrix(AMatrix(),potenzMatrix));
    }

    public int getKnotenCounter() {
        return graphTool.getKnotenCounter();
    }

    public int getKantenCounter() {
        return graphTool.getKantenCounter();
    }

    public int[][] AMatrix() {

            int[][] AMatrix = new int[getKnotenCounter()][getKnotenCounter()];

            for (int i = 0; i < getKnotenCounter(); i++) {
                for (int j = 0; j < getKnotenCounter(); j++) {
                    AMatrix[i][j] = 0;
                }
            }
        if (!graphTool.isFile()){
            for (int i = 0; i < getKantenCounter(); i++) {

                AMatrix[index1.get(i)][index2.get(i)] = 1;
                AMatrix[index2.get(i)][index1.get(i)] = 1;
            }
        }else {
            for (int i = 0; i < getKnotenCounter(); i++) {
                for (int j = 0; j < getKnotenCounter(); j++) {
                    AMatrix[i][j] = graphTool.getGraph().get(i).get(j);
                }
            }
        }
        return AMatrix;
    }

    public int[][] AXMatrix() {
        int[][] AXMatrix = AMatrix();
        int[][] OldAXMatrix = AMatrix();
//        int[][] DMatrix = AMatrix();
//        int[][] OldDMatrix = AMatrix();
//        System.out.println("DMatrix");
//        printMatrix(DMatrix);
        for (int k = 0; k < getKnotenCounter(); k++) {
            System.out.println("Potenzmatrix " + k + ":");

            int[][] tempMatrix = new int[getKnotenCounter()][getKnotenCounter()];

            /** PotenzMatrix **/
            for (int i = 0; i < getKnotenCounter(); i++) {
                for (int j = 0; j < getKnotenCounter(); j++) {
                    for (int x = 0; x < getKnotenCounter(); x++) {
                        tempMatrix[i][j] += OldAXMatrix[i][x] * AXMatrix[x][j];

                    }
                }
            }
//            /** Distanz Matrix **/
//            for (int i = 0; i < getKnotenCounter(); i++) {
//                for (int j = 0; j < getKnotenCounter(); j++) {
////                    if(AXMatrix[i][j] == 0) DMatrix[i][j] = -9;
//                    if(j==i)DMatrix[i][j] = 0;
//                        if (OldAXMatrix[i][j] != tempMatrix[i][j]) {
//                            if (OldDMatrix[i][j] > k) {
//                                DMatrix[i][j] = k+1; //TODO NONOWORKING -9 stays Rest IST OK
//
//                            }
//                        }
//                }
//            }

            OldAXMatrix = tempMatrix;
//            OldDMatrix = DMatrix;
            printMatrix(OldAXMatrix);
//            System.out.println("DMatrix k="+k);
//            printMatrix(DMatrix);
        potenzMatrix[k] = OldAXMatrix;

        }

        return OldAXMatrix;
    }
//TODO Not Working yet fix I want to calculate the Distance Matrix
    public static int[][] DMatrix(int[][] matrix, int[][][] potenzMatrix) {
        /** INIT **/
        int n = matrix.length;
        int[][] distanceMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(distanceMatrix[i], -9);
            distanceMatrix[i][i] = 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && matrix[i][j] != 0) {
                    distanceMatrix[i][j] = 1;
                }
            }
        }

        int k = 2;
        /** Change Loop **/
        while (true) {
            boolean changed = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] != 0 && distanceMatrix[i][j] == -9) {
                        boolean found = false;
                        for (int p = 2; p <= k; p++) {
                            if (potenzMatrix[k][i][j] != 0) {
                                distanceMatrix[i][j] = p;
                                changed = true;
                                found = true;
                                break;
                            }
                        }
                        if (!found) distanceMatrix[i][j] = -9;
                    }
                }
            }
            if (k == n || !changed) {
                break;
            }
            k++;
        }

        return distanceMatrix;
    }

    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < getKnotenCounter(); i++) {
            for (int j = 0; j < getKnotenCounter(); j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}