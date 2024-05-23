package org.spengergasse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GraphTool extends JFrame {
    private static final int RADIUS = 20;
    private final List<Point> knoten = new ArrayList<>();
    private final List<Kante> kanten = new ArrayList<>();
    private int[][] exportDataGraph,AMatrix;

    private static class Kante {
        Point start;
        Point end;

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
    

    public void setExportDataGraph(int[][] exportDataGraph) {
        this.exportDataGraph = exportDataGraph;
    }

    private void frameSettings() {
        resetButton = new JButton("Clear");
        calcButton = new JButton("Calculate");
        loadFileButton = new JButton("Load Graph from CSV");
        exportButton = new JButton("Export Graph");
        redrawButton = new JButton("Reload");

        resetButton.addActionListener(e -> {
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

    private void redrawGraph() {
        clear();
        Random rnd = new Random();
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

    private void loadGraphFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadGraphFromCSV(selectedFile.getAbsolutePath());
            redrawGraph();
        }
    }

    private void loadGraphFromCSV(String filePath) {
        List<int[]> rows = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(";");
                int[] row = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Integer.parseInt(values[i]);
                }
                rows.add(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AMatrix = new int[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            AMatrix[i] = rows.get(i);
        }
    }

    private void exportGraphToCSV() {
        if (exportDataGraph == null) {
            JOptionPane.showMessageDialog(this, "Please draw or import a graph first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (FileWriter writer = new FileWriter("graphFile.csv")) {
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
        new GraphTool();
    }


}
