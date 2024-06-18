package org.spengergasse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class GraphTool extends JFrame {
    private static final int RADIUS = 20;
    private final List<Point> knoten = new ArrayList<>();
    private final List<Kante> kanten = new ArrayList<>();
    private int[][] exportDataGraph,AMatrix;

    private static class Kante {
        protected Point start;
        protected Point end;

        Kante(Point start, Point end) {
            this.start = start;
            this.end = end;
        }
    }

    private JButton resetButton, calcButton, loadFileButton, exportButton, redrawButton;

    public GraphTool() {
        frameSettings();
        initializeMouseListener();
    }

    public GraphTool(int[][] manualGraph) {
        this();
        initializeGraphFromMatrix(manualGraph);
        try {

            redrawGraph();
        }catch (ArrayIndexOutOfBoundsException e) {
            clear();
            throw new GraphException(e.getMessage());
        }
    }

    private void initializeMouseListener() {
        addMouseListener(new MouseAdapter() {
            private Point startKnoten;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    knoten.add(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    startKnoten = getKnotenAt(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && startKnoten != null) {
                    Point endKnoten = getKnotenAt(e.getPoint());
                    if (endKnoten != null && !startKnoten.equals(endKnoten)) {
                        kanten.add(new Kante(startKnoten, endKnoten));
                        repaint();
                    }
                    startKnoten = null;
                }
            }
        });
    }

    private Point getKnotenAt(Point point) {
        return knoten.stream().filter(knoten -> point.distance(knoten) <= RADIUS).findFirst().orElse(null);
    }

    public int getKnotenCounter() {
        return knoten.size();
    }

    private void frameSettings() {
        resetButton = new JButton("Clear");
        calcButton = new JButton("Calculate");
        loadFileButton = new JButton("Load Graph from CSV");
        exportButton = new JButton("Export Graph");
        redrawButton = new JButton("Reload");

        resetButton.addActionListener(e -> {
            AMatrix = null;
            clear();
        });
        calcButton.addActionListener(e -> {
            calculateAdjacencyMatrix();
            new Calc(this);
        }
        );
        loadFileButton.addActionListener(e -> loadGraphFromFile());
        redrawButton.addActionListener(e -> redrawGraph());
        exportButton.addActionListener(e -> exportGraphToCSV());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loadFileButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(calcButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(redrawButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(820, 640);
        setResizable(false);
        setTitle("Graph Tool");
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("TestImg.jpg")));

        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setVisible(true);
    }

    private void initializeGraphFromMatrix(int[][] manualGraph) {
        int knotenCount = manualGraph.length;
        this.AMatrix = manualGraph;
        Random rnd = new Random();
        for (int i = 0; i < knotenCount; i++) {
            Point rndPoint;
            do {
                rndPoint = new Point(rnd.nextInt(780) + 20, rnd.nextInt(550) + 20);
            } while (isTooCloseToOtherKnotens(rndPoint));
            knoten.add(rndPoint);
        }
        for (int i = 0; i < knotenCount; i++) {
            for (int j = i + 1; j < knotenCount; j++) {
                if (manualGraph[i][j] != 0) {
                    kanten.add(new Kante(knoten.get(i), knoten.get(j)));
                }else{
//                    JOptionPane.showMessageDialog(this, "ManuelGraph is Empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        repaint();
    }

    private boolean isTooCloseToOtherKnotens(Point point) {
        return knoten.stream().anyMatch(other -> point.distance(other) < 2 * RADIUS);
    }

    private void calculateAdjacencyMatrix() {
        int size = knoten.size();
        AMatrix = new int[size][size];

        for (Kante kante : kanten) {
            int idx1 = knoten.indexOf(kante.start);
            int idx2 = knoten.indexOf(kante.end);
            AMatrix[idx1][idx2] = 1;
            AMatrix[idx2][idx1] = 1;

        }
        exportDataGraph = AMatrix;

    }

    public int[][] getAMatrix() {
        return AMatrix;
    }

    private void clear() {
        knoten.clear();
        kanten.clear();
//        dataGraph.clear();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawKanten(g);
        drawKnotens(g);
    }

    private void drawKnotens(Graphics g) {
        for (int i = 0; i < knoten.size(); i++) {
            Point point = knoten.get(i);
            int x = point.x - RADIUS;
            int y = point.y - RADIUS;
            g.drawOval(x, y, RADIUS * 2, RADIUS * 2);
            String label = Character.toString((char) ('A' + i));
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(label);
            int stringHeight = fm.getHeight();
            g.drawString(label, x + RADIUS - stringWidth / 2, y + RADIUS + stringHeight / 4);
        }
    }

    private void drawKanten(Graphics g) {
        for (Kante kante : kanten) {
            g.drawLine(kante.start.x, kante.start.y, kante.end.x, kante.end.y);
        }
    }

    public void redrawGraph() {
        clear();
        Random rnd = new Random();
        if (AMatrix == null){
            JOptionPane.showMessageDialog(this, "No Node found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < AMatrix.length; i++) {
            Point rndPoint;
            do {
                rndPoint = new Point(rnd.nextInt(780) + 20, rnd.nextInt(550) + 20);
            } while (isTooCloseToOtherKnotens(rndPoint));
            knoten.add(rndPoint);
        }
        for (int i = 0; i < AMatrix.length; i++) {
            for (int j = i + 1; j < AMatrix.length; j++) {
                if (AMatrix[i][j] != 0) {
                    kanten.add(new Kante(knoten.get(i), knoten.get(j)));
                }
            }
        }
        repaint();
    }

    public void loadGraphFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadGraphFromCSV(selectedFile.getAbsolutePath());
            redrawGraph();
        }
    }

    public void loadGraphFromCSV(String filePath) {
        List<int[]> rows = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split("[;,]");
                int[] row = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Integer.parseInt(values[i]);
                }
                rows.add(row);
            }
            calculateAdjacencyMatrix();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        AMatrix = new int[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            AMatrix[i] = rows.get(i);
            System.out.println(Arrays.toString(AMatrix[i]));
        }

//        redrawGraph();
    }

    public void exportGraphToCSV() {
        calculateAdjacencyMatrix();
        if (exportDataGraph == null) {
            JOptionPane.showMessageDialog(this, "Please draw or import a graph first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\onedrive\\Spengergasse\\3 Semester\\PosTheorie\\GraphTool\\graphFile.csv"))) {
            for (int[] row : exportDataGraph) {
                for (int j = 0; j < row.length; j++) {
                    writer.write(row[j] + (j < row.length - 1 ? ";" : ""));
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

//        new GraphTool();

        int[][] graphConnected = new int[][]{
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

        int[][] graphNotConnected = new int[][]{
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
        new GraphTool(graphConnected);
    }


}
