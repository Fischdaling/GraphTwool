package org.spengergasse;

import java.util.*;

public class Calc {
    private  GraphTool graphTool;
    private  int[][][] potenzMatrix;
    private  int[][] adjacentMatrix;
    private  int[] exzentrizitaeten;
    private  int knotenCounter;
    private  ArrayList<Integer> artikulationen;
    private int[] exzentritaeten;
    List<Integer> eulischerCycle = new ArrayList<>();

    public Calc(GraphTool graphTool) {
        this.graphTool = graphTool;
        knotenCounter = graphTool.getKnotenCounter();
        if (knotenCounter == 0) {
            throw new GraphException("Keine Knoten gefunden");
        }

        adjacentMatrix = graphTool.getAMatrix();

        artikulationen = new ArrayList<>();
        potenzMatrix = new int[knotenCounter][knotenCounter][knotenCounter];

        exzentrizitaeten = distanz();
        if (hasEulerianPathOrCycle(adjacentMatrix)) {
            initEulerianCycle(adjacentMatrix, 0);
        }
        System.out.println(this);
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("A:").append("\n").append(matrixToString(adjacentMatrix)).append("\n");
        str.append(potenzMatrixToString());
        str.append("Exzentizitaeten").append("\n").append(arrayToString(exzentrizitaeten)).append("\n");
        str.append("Durchmesser ").append(durchmesser()).append("\n");
        str.append("Radius ").append(radius()).append("\n");
        str.append("Zentrum ").append(convertNumbersToLetters(zentrum())).append("\n");
        str.append("Weg Matrix").append("\n").append(matrixToString(WMatrix(adjacentMatrix))).append("\n");
        str.append("Distance Matrix").append("\n").append(matrixToString(DMatrix(adjacentMatrix))).append("\n");
        str.append("Komponenten").append("\n").append(convertNumbersToLetters2D(komponentenSuche(WMatrix(adjacentMatrix)))).append("\n");
        str.append("Artikulationen").append("\n").append(convertNumbersToLetters(artikulationen(adjacentMatrix))).append("\n");
        str.append("Bruecken").append("\n").append(convertNumbersToLetters2D(bruecken(adjacentMatrix))).append("\n");
        str.append("Blöcke").append("\n").append(convertNumbersToLetters2D(bloecke())).append("\n");
        str.append("Euler Zyklus: ").append(eulischerCycle).append("\n");
        str.append("Is connected: ").append(isZusammenHaengend()).append("\n");
//        str.append("Spanning Tree: ").append(findSpanningTree());
        return str.toString();
    }

    public int[][] getAdjacentMatrix() {
        return adjacentMatrix;
    }

    private String matrixToString(int[][] matrix) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < knotenCounter; i++) {
            for (int j = 0; j < knotenCounter; j++) {
                str.append(matrix[i][j] == -9 ? "∞" : matrix[i][j]);
                if (j != knotenCounter - 1) str.append(",");
            }
            str.append("\n");
        }
        return str.toString();
    }

    private String potenzMatrixToString() {
        StringBuilder str = new StringBuilder();
        for (int i = 2; i < knotenCounter; i++) {
            str.append("A").append(i).append("\n");
            for (int j = 0; j < knotenCounter; j++) {
                for (int k = 0; k < knotenCounter; k++) {
                    if (potenzMatrix[i][j][k] < 10) str.append(0);
                    str.append(potenzMatrix[i][j][k]);
                    if (k != knotenCounter - 1) str.append(",");
                }
                str.append("\n");
            }
            str.append("\n");
        }
        return str.toString();
    }

    private String arrayToString(int[] array) {
        if (array == null) return null;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < knotenCounter; i++) {
            char letter = (char) (i + 'A');
            str.append(letter).append(":").append(array[i]);
            if (i != knotenCounter - 1) str.append(",");
        }
        return str.toString();
    }

    private String convertNumbersToLetters(ArrayList<Integer> list) {
        if (list == null) return null;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            int num = list.get(i);
            char letter = (char) (num + 'A');
            result.append(letter);
            if (i != list.size() - 1) result.append(", ");
        }
        return result.toString();
    }

    private String convertNumbersToLetters2D(ArrayList<ArrayList<Integer>> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Integer> innerList = list.get(i);
            result.append("[");
            for (int j = 0; j < innerList.size(); j++) {
                int num = innerList.get(j);
                char letter = (char) (num + 'A');
                result.append(letter);
                if (j != innerList.size() - 1) result.append(", ");
            }
            result.append("]");
            if (i != list.size() - 1) result.append(", ");
        }
        return result.toString();
    }


    int[][][] potenzMatrix(int[][] matrix) {
        int[][] AXMatrix = matrix;
        int[][] OldAXMatrix = matrix;
        for (int k = 2; k < matrix.length; k++) {
            int[][] tempMatrix = new int[matrix.length][matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    for (int x = 0; x < matrix.length; x++) {
                        tempMatrix[i][j] += OldAXMatrix[i][x] * AXMatrix[x][j];
                    }
                }
            }
            OldAXMatrix = tempMatrix;
            potenzMatrix[k] = OldAXMatrix;
        }
        return potenzMatrix;
    }

    public int[][] WMatrix(int[][] AMatrix) {
        int[][][] potenzMatrix = potenzMatrix(AMatrix);
        int n = AMatrix.length;
        int[][] wegMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(wegMatrix[i], 0);
            wegMatrix[i][i] = 1;
            for (int j = 0; j < n; j++) {
                if (i != j && AMatrix[i][j] != 0) {
                    wegMatrix[i][j] = 1;
                }
            }
        }

        int k = 2;
        while (true) {
            boolean changed = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (wegMatrix[i][j] == 0) {
                        boolean found = false;
                        for (int p = 2; p <= k; p++) {
                            if (potenzMatrix[p][i][j] != 0) {
                                wegMatrix[i][j] = 1;
                                changed = true;
                                found = true;
                                break;
                            }
                        }
                        if (!found) wegMatrix[i][j] = 0;
                    }
                }
            }
            if (k == n || !changed) break;
            k++;
        }

        return wegMatrix;
    }

    public int[][] DMatrix(int[][] matrix ) {
        /** INIT **/
        int[][][] potenzMatrix = potenzMatrix(matrix);
        int n = matrix.length;
        int[][] distanceMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(distanceMatrix[i], -9); // -9 Statt Unendlich
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
            while (true) {
                boolean changed = false;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (distanceMatrix[i][j] == -9) {
                            boolean found = false;
                            for (int p = 2; p <= k; p++) {
                                if (potenzMatrix[p][i][j] != 0) {
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

    public int[] distanz(){
        if (!isZusammenHaengend())
            return null;
        int[] distanz = new int[knotenCounter];
        for (int i = 0; i < knotenCounter; i++) {
            for (int j = 0; j < knotenCounter; j++) {
                if (distanz[i]< DMatrix(adjacentMatrix)[i][j]){
                    distanz[i] = DMatrix(adjacentMatrix)[i][j];
                }
            }
        }
        this.exzentritaeten = distanz;
        return distanz;
    }

    public Integer durchmesser(){
        if (!isZusammenHaengend())
            return null;
        int distanz = 0;
        for (int i = 0; i < knotenCounter; i++) {
           if (exzentritaeten[i] > distanz) distanz = exzentritaeten[i];
        }
        return distanz;
    }

    public Integer radius(){
        if (!isZusammenHaengend())
            return null;
        int radius = Integer.MAX_VALUE;
        for (int i = 0; i < knotenCounter; i++) {
            if (exzentritaeten[i] < radius) radius = exzentritaeten[i];
        }

        return radius;
    }

    public ArrayList<Integer> zentrum(){
        if (!isZusammenHaengend())
            return null;
        ArrayList<Integer> zentum = new ArrayList<>();
        for (int i = 0; i < knotenCounter; i++) {
            if (exzentritaeten[i] == radius()) zentum.add(i);
        }

        return zentum;
    }


    public ArrayList<ArrayList<Integer>> komponentenSuche(int[][] wegMatrix) {
        ArrayList<ArrayList<Integer>> komponenten = new ArrayList<>();

        for (int i = 0; i < wegMatrix.length; i++) {
                ArrayList<Integer> neuKomponent = new ArrayList<>();
            for (int j = 0; j < wegMatrix.length; j++) {
                if (wegMatrix[i][j] == 1){
                    neuKomponent.add(j);
                }
            }
            if (!neuKomponent.isEmpty()) {
                boolean visited = true;
                Iterator<ArrayList<Integer>> iteratored = komponenten.iterator();
                while (iteratored.hasNext()) {
                    ArrayList<Integer> komponente = iteratored.next();
                    if (komponente.containsAll(neuKomponent)) {
                        visited = false;
                        break;
                    }
                    if (neuKomponent.containsAll(komponente)) {
                        iteratored.remove();
                    }
                }
                if (visited) {
                    komponenten.add(neuKomponent);
                }
            }
        }

        return komponenten;
    }


    public ArrayList<Integer> artikulationen(int[][] AMatrix) {
        int nKomponenten = komponentenSuche(WMatrix(AMatrix)).size();
        ArrayList<Integer> articulationPoints = new ArrayList<>();

        for (int i = 0; i < AMatrix.length; i++) {

            int[][] tmpMatrix = new int[AMatrix.length][AMatrix.length];

            for (int j = 0; j < AMatrix.length; j++) {
                for (int k = 0; k < AMatrix.length; k++) {
                    tmpMatrix[j][k] = AMatrix[j][k];
                }
            }

            for (int j = 0; j < AMatrix.length; j++) {
                tmpMatrix[i][j] = 0;
                tmpMatrix[j][i] = 0;
            }

            int modifiedComponents = komponentenSuche(WMatrix(tmpMatrix)).size();

            if (modifiedComponents-1 > nKomponenten) {
                articulationPoints.add(i);
            }

        }

        return articulationPoints;
    }

    public ArrayList<ArrayList<Integer>> bruecken(int[][] AMatrix){
        ArrayList<ArrayList<Integer>> bruecken = new ArrayList<>();
        int nKomponenten = komponentenSuche(WMatrix(AMatrix)).size();

        int[][] tmpMatrix = new int[AMatrix.length][AMatrix.length];

        for (int j = 0; j < AMatrix.length; j++) {
            for (int k = 0; k < AMatrix.length; k++) {
                tmpMatrix[j][k] = AMatrix[j][k];
            }
        }
        for (int i = 0; i < AMatrix.length; i++) {
            for (int j = 0; j < AMatrix.length; j++) {
                if (tmpMatrix[i][j] == 1){
                    tmpMatrix[i][j] = 0;
                    tmpMatrix[j][i] = 0;
                    if (komponentenSuche(WMatrix(tmpMatrix)).size() > nKomponenten){
                        ArrayList<Integer> bruecke = new ArrayList<>();
                        bruecke.add(Math.min(i,j));
                        bruecke.add(Math.max(i,j));
                        if (!bruecken.contains(bruecke)){
                            bruecken.add(bruecke);
                        }
                    }
                    tmpMatrix[i][j] = 1;
                    tmpMatrix[j][i] = 1;
                }
            }
        }

        return bruecken;
    }


    public boolean hasEulerianPathOrCycle(int[][] adjacentMatrix) {
    int oddDegreeCount = 0;
    for (int i = 0; i < knotenCounter; i++) {
        int degree = 0;
        for (int j = 0; j < knotenCounter; j++) {
            degree += adjacentMatrix[i][j];
        }
        if (degree % 2 != 0) {
            oddDegreeCount++;
        }
    }

    if (oddDegreeCount > 2) {
        return false;
    }

    return oddDegreeCount == 0 || oddDegreeCount == 2;
}

    public void initEulerianCycle(int[][] adjacentMatrix, int startKnoten) {
        int[][] matrix = new int[adjacentMatrix.length][];
        for (int i = 0; i < adjacentMatrix.length; i++) {
            matrix[i] = adjacentMatrix[i].clone();
        }

        eulischerCycle.clear();
        findEulerianCycleHelper(matrix, startKnoten);
    }

    private void findEulerianCycleHelper(int[][] matrix, int currentKnoten) {
        for (int i = 0; i < knotenCounter; i++) {
            while (matrix[currentKnoten][i] > 0) {
                matrix[currentKnoten][i]--;
                matrix[i][currentKnoten]--;
                findEulerianCycleHelper(matrix, i);
            }
        }
        eulischerCycle.add(currentKnoten);
    }

    //TODO Spannbäume/Gerüste
    public List<int[]> findSpanningTree() {
        boolean[] visited = new boolean[knotenCounter];
        Arrays.fill(visited, false);

        List<int[]> spanningTree = new ArrayList<>();

        DFSForSpanningTree(0, visited, spanningTree);

        return spanningTree;
    }

    private void DFSForSpanningTree(int vertex, boolean[] visited, List<int[]> spanningTree) {
        visited[vertex] = true;
        for (int i = 0; i < knotenCounter; i++) {
            if (adjacentMatrix[vertex][i] != 0 && !visited[i]) {
                spanningTree.add(new int[]{vertex, i});
                DFSForSpanningTree(i, visited, spanningTree);
            }
        }
    }
    //TODO Starke Zusammenhangskomponente
    public boolean isZusammenHaengend() {
        return komponentenSuche(WMatrix(adjacentMatrix)).size() == 1;
    }
    //TODO Blöcke (schwierig!)
    public ArrayList<ArrayList<Integer>> bloecke() {
    ArrayList<ArrayList<Integer>> blocks = new ArrayList<>();
    ArrayList<Integer> articulationPoints = artikulationen(adjacentMatrix);

    ArrayList<ArrayList<Integer>> bridges = bruecken(adjacentMatrix);

    for (int i = 0; i < knotenCounter; i++) {
        if (!articulationPoints.contains(i)) {
            ArrayList<Integer> block = new ArrayList<>();
            block.add(i);

            for (ArrayList<Integer> bridge : bridges) {
                if (bridge.contains(i)) {
                    int otherVertex = (bridge.get(0) == i) ? bridge.get(1) : bridge.get(0);
                    block.add(otherVertex);
                }
            }

            blocks.add(block);
        }
    }

    return blocks;
}


}