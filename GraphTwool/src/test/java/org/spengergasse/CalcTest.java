package org.spengergasse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spengergasse.Calc;
import org.spengergasse.GraphTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;



public class CalcTest {
    
        int[][] graphConnected;
        int[][] graphNotConnected;
        GraphTool graphConnectedTool;
        GraphTool graphNotConnectedTool;


    @BeforeEach
        public void setUp() {
            // DO NOT TOUCH HARD CODED BASED ON https://graphen.theoretische-informatik.at/
            graphConnected = new int[][]{
                    {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
            };

            graphNotConnected = new int[][]{
                    {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
            };

            graphConnectedTool = new GraphTool(graphConnected);
            graphNotConnectedTool = new GraphTool(graphNotConnected);
        }

        @Test
        public void testAMatrix_ShouldReturnAMatrix_WhenAMatrixExists() {
            Calc calc = new Calc(graphConnectedTool);
            int[][] expectedAMatrix = graphConnected;
            Assertions.assertArrayEquals(expectedAMatrix, calc.getAdjacentMatrix());
        }

        @Test
        public void testPotenzMatrix_ShouldReturnPotenzMatrixUpToKnotenmengeMinus1_WhenGraphConnected() {
            Calc calc = new Calc(graphConnectedTool);
            int[][] A2 = {
                    {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 3, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 3, 2, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 2, 3, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 2, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 2, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 1, 3, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 1, 1, 4, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 1, 1, 2, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 4, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 2, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 4, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 2, 0, 1, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 4, 0, 0, 1, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 2, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 2, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 2, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 3, 0, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 3, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2},
            };
            int[][] A6 = {
                    {15, 22, 45, 45, 36, 24, 26, 28, 24, 6, 6, 4, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0},
                    {22, 105, 103, 103, 192, 62, 64, 72, 64, 34, 38, 28, 4, 6, 2, 2, 6, 0, 0, 2, 2, 0, 0, 0},
                    {45, 103, 178, 177, 216, 116, 128, 150, 120, 52, 56, 32, 13, 19, 4, 4, 21, 1, 1, 4, 4, 1, 0, 0},
                    {45, 103, 177, 178, 216, 116, 128, 150, 120, 52, 56, 32, 13, 19, 4, 4, 21, 1, 1, 4, 4, 1, 0, 0},
                    {36, 192, 216, 216, 461, 166, 172, 222, 182, 120, 148, 81, 15, 35, 19, 19, 41, 4, 4, 19, 20, 3, 1, 1},
                    {24, 62, 116, 116, 166, 93, 98, 118, 92, 45, 49, 35, 11, 17, 4, 4, 19, 1, 1, 4, 4, 1, 0, 0},
                    {26, 64, 128, 128, 172, 98, 122, 130, 102, 47, 51, 28, 20, 18, 4, 4, 20, 1, 1, 4, 4, 1, 0, 0},
                    {28, 72, 150, 150, 222, 118, 130, 223, 140, 107, 119, 32, 13, 67, 31, 29, 92, 15, 15, 30, 31, 15, 3, 3},
                    {24, 64, 120, 120, 182, 92, 102, 140, 109, 67, 87, 28, 11, 27, 15, 15, 33, 4, 4, 15, 16, 3, 1, 1},
                    {6, 34, 52, 52, 120, 45, 47, 107, 67, 72, 83, 16, 3, 45, 27, 25, 66, 13, 13, 26, 27, 13, 3, 3},
                    {6, 38, 56, 56, 148, 49, 51, 119, 87, 83, 171, 18, 3, 43, 65, 64, 69, 25, 25, 63, 74, 18, 13, 13},
                    {4, 28, 32, 32, 81, 35, 28, 32, 28, 16, 18, 26, 2, 3, 1, 1, 3, 0, 0, 1, 1, 0, 0, 0},
                    {2, 4, 13, 13, 15, 11, 20, 13, 11, 3, 3, 2, 6, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {2, 6, 19, 19, 35, 17, 18, 67, 27, 45, 43, 3, 1, 60, 35, 41, 64, 37, 37, 24, 25, 13, 3, 3},
                    {0, 2, 4, 4, 19, 4, 4, 31, 15, 27, 65, 1, 0, 35, 110, 35, 64, 47, 47, 26, 27, 13, 3, 3},
                    {0, 2, 4, 4, 19, 4, 4, 29, 15, 25, 64, 1, 0, 41, 35, 59, 39, 37, 37, 41, 50, 14, 11, 11},
                    {2, 6, 21, 21, 41, 19, 20, 92, 33, 66, 69, 3, 1, 64, 64, 39, 150, 25, 25, 63, 67, 59, 22, 22},
                    {0, 0, 1, 1, 4, 1, 1, 15, 4, 13, 25, 0, 0, 37, 47, 37, 25, 42, 41, 13, 14, 3, 1, 1},
                    {0, 0, 1, 1, 4, 1, 1, 15, 4, 13, 25, 0, 0, 37, 47, 37, 25, 41, 42, 13, 14, 3, 1, 1},
                    {0, 2, 4, 4, 19, 4, 4, 30, 15, 26, 63, 1, 0, 24, 26, 41, 63, 13, 13, 54, 59, 39, 20, 20},
                    {0, 2, 4, 4, 20, 4, 4, 31, 16, 27, 74, 1, 0, 25, 27, 50, 67, 14, 14, 59, 89, 40, 37, 37},
                    {0, 0, 1, 1, 3, 1, 1, 15, 3, 13, 18, 0, 0, 13, 13, 14, 59, 3, 3, 39, 40, 65, 35, 35},
                    {0, 0, 0, 0, 1, 0, 0, 3, 1, 3, 13, 0, 0, 3, 3, 11, 22, 1, 1, 20, 37, 35, 32, 31},
                    {0, 0, 0, 0, 1, 0, 0, 3, 1, 3, 13, 0, 0, 3, 3, 11, 22, 1, 1, 20, 37, 35, 31, 32},
            };

            Assertions.assertArrayEquals(A2,calc.potenzMatrix(graphConnected)[2]);
            Assertions.assertArrayEquals(A6,calc.potenzMatrix(graphConnected)[6]);
            boolean lengthMinus1 = calc.potenzMatrix(graphConnected).length-1 == 23;
            Assertions.assertTrue(lengthMinus1);
        }

        @Test
        public void testPotenzMatrix_ShouldReturnPotenzMatrixUpToKnotenmengeMinus1_WhenGraphNotConnected(){
            Calc calc = new Calc(graphNotConnectedTool);
            int[][] A2 ={
                    {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,3,1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,3,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,2,3,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,2,1,1,4,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,1,1,1,2,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,1,1,1,1,3,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,3,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,1,1,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,1,1,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,1,1,1,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,2,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,2,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,2,1,1,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,3,0,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,3,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,2,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2},
            };

            int[][] A6 ={
                    {15,22,41,41,28,20,22,0,0,0,0,4,2,0,0,0,0,0,0,0,0,0,0,0},
                    {22,97,91,91,124,50,52,0,0,0,0,24,4,0,0,0,0,0,0,0,0,0,0,0},
                    {41,91,136,135,128,78,88,0,0,0,0,26,11,0,0,0,0,0,0,0,0,0,0,0},
                    {41,91,135,136,128,78,88,0,0,0,0,26,11,0,0,0,0,0,0,0,0,0,0,0},
                    {28,124,128,128,185,88,92,0,0,0,0,49,11,0,0,0,0,0,0,0,0,0,0,0},
                    {20,50,78,78,88,59,62,0,0,0,0,29,9,0,0,0,0,0,0,0,0,0,0,0},
                    {22,52,88,88,92,62,84,0,0,0,0,22,18,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,45,14,31,31,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,14,11,17,17,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,31,17,30,29,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,31,17,29,30,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {4,24,26,26,49,29,22,0,0,0,0,24,2,0,0,0,0,0,0,0,0,0,0,0},
                    {2,4,11,11,11,9,18,0,0,0,0,2,6,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,18,18,18,0,25,25,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,18,86,18,0,43,43,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,18,18,18,0,25,25,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32,0,0,31,35,35,18,18},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,25,43,25,0,40,39,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,25,43,25,0,39,40,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,31,0,0,32,35,35,18,18},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,35,0,0,35,63,36,35,35},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,35,0,0,35,36,63,35,35},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,18,0,0,18,35,35,32,31},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,18,0,0,18,35,35,31,32},
            };

            Assertions.assertArrayEquals(A2,calc.potenzMatrix(graphNotConnected)[2]);
            Assertions.assertArrayEquals(A6,calc.potenzMatrix(graphNotConnected)[6]);
            boolean lengthMinus1 = calc.potenzMatrix(graphNotConnected).length-1 == 23;
            Assertions.assertTrue(lengthMinus1);
        }

        @Test
        public void testWMatrix_ShouldReturnWMatrix_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);

            int[][] expectedWMatrix = {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };

            Assertions.assertArrayEquals(expectedWMatrix, calc.WMatrix(graphConnected));
        }

        @Test
        public void testWMatrix_ShouldReturnWMatrix_WhenGraphIsNotFullyConnected() {
            Calc calc = new Calc(graphNotConnectedTool);

            int[][] expectedWMatrix = {
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1},
            };
            Assertions.assertArrayEquals(expectedWMatrix, calc.WMatrix(graphNotConnected));
        }

        @Test
        public void testDMatrix_ShouldReturnDMatrix_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);

            int[][] expectedDMatrix = {
                    {0, 1, 2, 2, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 7, 7, 6, 8, 8, 7, 7, 8, 9, 9},
                    {1, 0, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 5, 7, 7, 6, 6, 7, 8, 8},
                    {2, 1, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                    {2, 1, 1, 0, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                    {3, 2, 1, 1, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 3, 5, 5, 4, 4, 5, 6, 6},
                    {4, 3, 2, 2, 1, 0, 1, 2, 2, 3, 3, 2, 3, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                    {4, 3, 2, 2, 1, 1, 0, 2, 2, 3, 3, 1, 2, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                    {4, 3, 2, 2, 1, 2, 2, 0, 1, 1, 1, 3, 4, 2, 3, 3, 2, 4, 4, 3, 3, 4, 5, 5},
                    {4, 3, 2, 2, 1, 2, 2, 1, 0, 2, 2, 3, 4, 3, 4, 4, 3, 5, 5, 4, 4, 5, 6, 6},
                    {5, 4, 3, 3, 2, 3, 3, 1, 2, 0, 1, 4, 5, 2, 3, 3, 2, 4, 4, 3, 3, 4, 5, 5},
                    {5, 4, 3, 3, 2, 3, 3, 1, 2, 1, 0, 4, 5, 1, 2, 2, 1, 3, 3, 2, 2, 3, 4, 4},
                    {5, 4, 3, 3, 2, 2, 1, 3, 3, 4, 4, 0, 1, 5, 6, 6, 5, 7, 7, 6, 6, 7, 8, 8},
                    {6, 5, 4, 4, 3, 3, 2, 4, 4, 5, 5, 1, 0, 6, 7, 7, 6, 8, 8, 7, 7, 8, 9, 9},
                    {6, 5, 4, 4, 3, 4, 4, 2, 3, 2, 1, 5, 6, 0, 1, 2, 2, 2, 2, 3, 3, 4, 5, 5},
                    {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 1, 0, 1, 2, 1, 1, 3, 3, 4, 5, 5},
                    {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 2, 1, 0, 1, 2, 2, 2, 2, 3, 4, 4},
                    {6, 5, 4, 4, 3, 4, 4, 2, 3, 2, 1, 5, 6, 2, 2, 1, 0, 3, 3, 1, 1, 2, 3, 3},
                    {8, 7, 6, 6, 5, 6, 6, 4, 5, 4, 3, 7, 8, 2, 1, 2, 3, 0, 1, 4, 4, 5, 6, 6},
                    {8, 7, 6, 6, 5, 6, 6, 4, 5, 4, 3, 7, 8, 2, 1, 2, 3, 1, 0, 4, 4, 5, 6, 6},
                    {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 3, 3, 2, 1, 4, 4, 0, 1, 2, 3, 3},
                    {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 3, 3, 2, 1, 4, 4, 1, 0, 1, 2, 2},
                    {8, 7, 6, 6, 5, 6, 6, 4, 5, 4, 3, 7, 8, 4, 4, 3, 2, 5, 5, 2, 1, 0, 1, 1},
                    {9, 8, 7, 7, 6, 7, 7, 5, 6, 5, 4, 8, 9, 5, 5, 4, 3, 6, 6, 3, 2, 1, 0, 1},
                    {9, 8, 7, 7, 6, 7, 7, 5, 6, 5, 4, 8, 9, 5, 5, 4, 3, 6, 6, 3, 2, 1, 1, 0}
            };

            Assertions.assertArrayEquals(expectedDMatrix,calc.DMatrix(graphConnected));
        }
        @Test
        public void testDMatrix_ShouldReturnDMatrix_WhenGraphIsNotFullyConnected() {
        Calc calc = new Calc(graphNotConnectedTool);

        int[][] expectedDMatrix = {
                {0,1,2,2,3,4,4,-9,-9,-9,-9,5,6,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {1,0,1,1,2,3,3,-9,-9,-9,-9,4,5,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {2,1,0,1,1,2,2,-9,-9,-9,-9,3,4,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {2,1,1,0,1,2,2,-9,-9,-9,-9,3,4,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {3,2,1,1,0,1,1,-9,-9,-9,-9,2,3,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {4,3,2,2,1,0,1,-9,-9,-9,-9,2,3,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {4,3,2,2,1,1,0,-9,-9,-9,-9,1,2,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,0,1,1,1,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,1,0,2,2,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,1,2,0,1,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,1,2,1,0,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {5,4,3,3,2,2,1,-9,-9,-9,-9,0,1,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {6,5,4,4,3,3,2,-9,-9,-9,-9,1,0,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,0,1,2,-9,2,2,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,1,0,1,-9,1,1,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,2,1,0,-9,2,2,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,0,-9,-9,1,1,2,3,3},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,2,1,2,-9,0,1,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,2,1,2,-9,1,0,-9,-9,-9,-9,-9},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,1,-9,-9,0,1,2,3,3},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,1,-9,-9,1,0,1,2,2},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,2,-9,-9,2,1,0,1,1},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,3,-9,-9,3,2,1,0,1},
                {-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,3,-9,-9,3,2,1,1,0},
        };

        Assertions.assertArrayEquals(expectedDMatrix,calc.DMatrix(graphNotConnected));
    }

        @Test
        public void testDurchmesser_ShouldReturnDurchmesser_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);

            assertEquals(9, calc.durchmesser());
        }

        @Test
        public void testDurchmesser_ShouldReturnDurchmesser_WhenGraphIsNotFullyConnected() {
            Calc calc = new Calc(graphNotConnectedTool);

            Assertions.assertNull(calc.durchmesser());
        }

        @Test
        public void testRadius_ShouldReturnRadius_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);
            assertEquals(5, calc.radius());
        }

        @Test
        public void testRadius_ShouldReturnRadius_WhenGraphIsNotFullyConnected() {
            Calc calc = new Calc(graphNotConnectedTool);
            Assertions.assertNull(calc.radius());
        }

        @Test
        public void testZentrum_ShouldReturnZentrum_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);
            ArrayList<Integer> expectedZentrum = new ArrayList<>();
            expectedZentrum.add(7);
            expectedZentrum.add(9);
            expectedZentrum.add(10);
            Assertions.assertEquals(expectedZentrum, calc.zentrum());
        }

        @Test
        public void testZentrum_ShouldReturnZentrum_WhenGraphIsNotFullyConnected() {
            Calc calc = new Calc(graphNotConnectedTool);

            Assertions.assertNull(calc.zentrum());
        }

        @Test
        public void testKomponentenSuche_ShouldReturnKomponenten_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);
            List<ArrayList<Integer>> expectedKomponenten = new ArrayList<>();
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                list.add(i);
            }
            expectedKomponenten.add(list);
            Assertions.assertEquals(expectedKomponenten, calc.komponentenSuche(calc.WMatrix(graphConnected)));
        }
        @Test
        public void testKomponentenSuche_ShouldReturnNothing_WhenGraphIsNotFullyConnected() {

            Calc calc = new Calc(graphNotConnectedTool);

            List<List<Integer>> expectedKomponenten = List.of(
                    Arrays.asList(0, 1, 2, 3, 4, 5, 6, 11, 12),
                    Arrays.asList(7, 8, 9, 10),
                    Arrays.asList(13, 14, 15, 17, 18),
                    Arrays.asList(16, 19, 20, 21, 22, 23)
            );

            Assertions.assertEquals(expectedKomponenten, calc.komponentenSuche(calc.WMatrix(graphNotConnected)));
        }



        @Test
        public void testArtikulationen_ShouldReturnArtikulationen_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);
            List<Integer> expectedArtikulationen = new ArrayList<>();
            expectedArtikulationen.add(1);
            expectedArtikulationen.add(4);
            expectedArtikulationen.add(6);
            expectedArtikulationen.add(7);
            expectedArtikulationen.add(10);
            expectedArtikulationen.add(11);
            expectedArtikulationen.add(14);
            expectedArtikulationen.add(16);
            expectedArtikulationen.add(20);
            expectedArtikulationen.add(21);

            Assertions.assertEquals(expectedArtikulationen, calc.artikulationen(graphConnected));
        }

        @Test
        public void testArtikulationen_ShouldReturnArtikulationen_WhenGraphIsNotFullyConnected() {
            Calc calc = new Calc(graphNotConnectedTool);
            List<Integer> expectedArtikulationen = new ArrayList<>();
            expectedArtikulationen.add(1);
            expectedArtikulationen.add(4);
            expectedArtikulationen.add(6);
            expectedArtikulationen.add(7);
            expectedArtikulationen.add(10);
            expectedArtikulationen.add(11);
            expectedArtikulationen.add(14);
            expectedArtikulationen.add(16);
            expectedArtikulationen.add(20);
            expectedArtikulationen.add(21);

            Assertions.assertEquals(expectedArtikulationen, calc.artikulationen(graphConnected));
        }

        @Test
        public void testBruecken_ShouldReturnArtikulationen_WhenGraphIsFullyConnected() {
            Calc calc = new Calc(graphConnectedTool);
            List<ArrayList<Integer>> expectedBruecken = new ArrayList<>();

            ArrayList<Integer> pair1 = new ArrayList<>();
            pair1.add(0);
            pair1.add(1);
            expectedBruecken.add(pair1);

            ArrayList<Integer> pair3 = new ArrayList<>();
            pair3.add(6);
            pair3.add(11);
            expectedBruecken.add(pair3);

            ArrayList<Integer> pair4 = new ArrayList<>();
            pair4.add(11);
            pair4.add(12);
            expectedBruecken.add(pair4);

            ArrayList<Integer> pair2 = new ArrayList<>();
            pair2.add(20);
            pair2.add(21);
            expectedBruecken.add(pair2);



            Assertions.assertEquals(expectedBruecken, calc.bruecken(graphConnected));
        }

        @Test
        public void testBruecken_ShouldReturnArtikulationen_WhenGraphIsNotFullyConnected() {
            Calc calc = new Calc(graphNotConnectedTool);
            List<ArrayList<Integer>> expectedBruecken = new ArrayList<>();

            ArrayList<Integer> pair1 = new ArrayList<>();
            pair1.add(0);
            pair1.add(1);
            expectedBruecken.add(pair1);

            ArrayList<Integer> pair3 = new ArrayList<>();
            pair3.add(6);
            pair3.add(11);
            expectedBruecken.add(pair3);

            ArrayList<Integer> pair4 = new ArrayList<>();
            pair4.add(7);
            pair4.add(8);
            expectedBruecken.add(pair4);

            ArrayList<Integer> pair5 = new ArrayList<>();
            pair5.add(11);
            pair5.add(12);
            expectedBruecken.add(pair5);

            ArrayList<Integer> pair6 = new ArrayList<>();
            pair6.add(13);
            pair6.add(14);
            expectedBruecken.add(pair6);

            ArrayList<Integer> pair7 = new ArrayList<>();
            pair7.add(14);
            pair7.add(15);
            expectedBruecken.add(pair7);

            ArrayList<Integer> pair2 = new ArrayList<>();
            pair2.add(20);
            pair2.add(21);
            expectedBruecken.add(pair2);



            Assertions.assertEquals(expectedBruecken, calc.bruecken(graphNotConnected));
        }

        //Edgecases

    @Test
    public void testNoEdgesGraph() {
        int[][] graph = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        GraphTool graphTool = new GraphTool(graph);
        assertEquals(3, graphTool.getKnotenCounter(), "Number of nodes should be 3");
        assertArrayEquals(graph, graphTool.getAMatrix(), "Adjacency matrix should match the input matrix");
    }

    @Test
    public void testSingleNodeGraph() {
        int[][] graph = {
                {0}
        };
        GraphTool graphTool = new GraphTool(graph);
        assertEquals(1, graphTool.getKnotenCounter(), "Number of nodes should be 1");
        assertArrayEquals(graph, graphTool.getAMatrix(), "Adjacency matrix should match the input matrix");
    }

    @Test
    public void testLinearGraph() {
        int[][] graph = {
                {0, 1, 0, 0},
                {1, 0, 1, 0},
                {0, 1, 0, 1},
                {0, 0, 1, 0}
        };
        GraphTool graphTool = new GraphTool(graph);
        assertEquals(4, graphTool.getKnotenCounter(), "Number of nodes should be 4");
        assertArrayEquals(graph, graphTool.getAMatrix(), "Adjacency matrix should match the input matrix");
    }

    @Test
    public void testCompleteGraph() {
        int[][] graph = {
                {0, 1, 1, 1},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 0}
        };
        GraphTool graphTool = new GraphTool(graph);
        assertEquals(4, graphTool.getKnotenCounter(), "Number of nodes should be 4");
        assertArrayEquals(graph, graphTool.getAMatrix(), "Adjacency matrix should match the input matrix");
    }

    @Test
    public void testTreeGraph() {
        int[][] graph = {
                {0, 1, 1, 0, 0},
                {1, 0, 0, 1, 0},
                {1, 0, 0, 0, 1},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0}
        };
        GraphTool graphTool = new GraphTool(graph);
        assertEquals(5, graphTool.getKnotenCounter(), "Number of nodes should be 5");
        assertArrayEquals(graph, graphTool.getAMatrix(), "Adjacency matrix should match the input matrix");
    }

    @Test
    public void testEmptyGraph() {
        GraphTool graphTool = new GraphTool();
        assertEquals(0, graphTool.getKnotenCounter(), "Number of nodes should be 0");
        assertNull(graphTool.getAMatrix(), "Adjacency matrix should be null");
    }

    @Test
    public void testGraphRedraw() {
        int[][] graph = {
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        };
        GraphTool graphTool = new GraphTool(graph);
        assertDoesNotThrow(graphTool::redrawGraph, "Redrawing graph should not throw any exceptions");
    }

    @Test
    public void testExportGraphToCSV() {
        int[][] graph = {
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };
        GraphTool graphTool = new GraphTool(graph);
        graphTool.exportGraphToCSV();
        graphTool.loadGraphFromCSV("E:\\onedrive\\Spengergasse\\3 Semester\\PosTheorie\\GraphTool\\graphFile.csv");
        assertArrayEquals(graph, graphTool.getAMatrix(), "Adjacency matrix should match the input matrix after export and import");
    }

    @Test
    public void testLoadGraphFromCSV() {
        GraphTool graphTool = new GraphTool();
        graphTool.loadGraphFromCSV("src/main/resources/testGraph.csv");
        int[][] expectedGraph = {
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        };
        assertArrayEquals(expectedGraph, graphTool.getAMatrix(), "Loaded adjacency matrix should match the expected matrix from CSV file");
    }

    }
