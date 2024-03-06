package org.spengergasse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcTest {
    GraphTool graphTool;
    Calc calc;

    @BeforeEach
    public void setUp(){
        graphTool = new GraphTool();
        calc = new Calc(graphTool);
    }
    @Test
    public void AMatrix_ShouldReturnEmtyMatrixWithEdgeTimesEdge_WhenNoKanten(){
        assertArrayEquals(new int[0][0], calc.AMatrix());
    }

    @Test
    public void AMatrix_ShouldReturnCorrectMatrixWithNodeTimesNode_WhenNoKanten(){
        graphTool.loadGraphFromCSV("C:\\Users\\gabri\\Desktop\\smallgraph.csv");
        assertArrayEquals(new int[0][0], calc.AMatrix());
        assertEquals(graphTool.getKnotenCounter(), calc.AMatrix().length);
    }





}
