package org.spengergasse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class GraphTool extends JFrame {
    private int radius = 20;
    private int buttonPressed;
   private  int knotenCounter, kantenCounter;
    private LinkedList<Integer> x1, x2;
    private LinkedList<Integer> y1, y2;
    private ArrayList<Point> knoten;
    private ArrayList<ArrayList<Point>> kanten;
    private ArrayList<ArrayList<Integer>> dataGraph;
   private boolean isFile, isdrawn;
    private JButton reset,calc,loadFileButton;
    private ArrayList<Integer> index1, index2;

    public ArrayList<ArrayList<Integer>> getGraph() {
        return dataGraph;
    }

    public boolean isFile() {
        return isFile;
    }

    public int getKnotenCounter() {
        return knotenCounter;
    }

    public int getKantenCounter() {
        return kantenCounter;
    }

    public ArrayList<Integer> getIndex1() {
        return index1;
    }

    public ArrayList<Integer> getIndex2() {
        return index2;
    }

    public ArrayList<Point> getKnoten() {
        return knoten;
    }

    public ArrayList<ArrayList<Point>> getKanten() {
        return kanten;
    }

    /** For Mouse input **/
    public GraphTool() {
        knoten = new ArrayList<>();
        kanten = new ArrayList<>();
        x1 = new LinkedList<>();
        y1 = new LinkedList<>();
        x2 = new LinkedList<>();
        y2 = new LinkedList<>();

        index1 = new ArrayList<>();
        index2 = new ArrayList<>();

        frameSettings();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buttonPressed = e.getButton();
                if (buttonPressed == MouseEvent.BUTTON1) {
                    knoten.add(e.getPoint());
                    System.out.println(knoten.get(knotenCounter));
                    knotenCounter++;
                    check();
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                buttonPressed = e.getButton();
                if (buttonPressed == MouseEvent.BUTTON3) {

                    if(index1.size() > index2.size()){
                        index1.remove(index1.size()-1);
                    }

                    for (Point i:knoten) {
                        if ((e.getX() >= i.x - radius) && (e.getX() <= i.x +radius)){
                            if ((e.getY() >= i.y - radius) && (e.getY() <= i.y +radius)){
                                x1.add(e.getX());
                                y1.add(e.getY());

                                 index1.add(knoten.indexOf(i));


                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (buttonPressed == MouseEvent.BUTTON3) {
                    for (int i = 0; i < knoten.size(); i++) {
                        Point point = knoten.get(i);
                        if ((e.getX() >= point.x - radius) && (e.getX() <= point.x + radius) &&
                                (e.getY() >= point.y - radius) && (e.getY() <= point.y + radius)) {
                            x2.add(e.getX());
                            y2.add(e.getY());
                            index2.add(knoten.indexOf(knoten.get(i)));
                            System.out.println(index1 + " : " + index2);
                            check();


                            ArrayList<Point> edge = new ArrayList<>();
                            edge.add(knoten.get(knotenCounter - 1));
                            edge.add(knoten.get(i));
                            kanten.add(edge);
                            kantenCounter++;

                            System.out.println(kanten);


                            repaint();
                            break;
                        }
                    }
                }
            }

        });

    }


    public void check(){
        if (x1.size() != x2.size()) {
            x1.removeLast();
            y1.removeLast();
            check();
        }
    }
    private void frameSettings() {
        reset = new JButton("Clear");
        calc = new JButton("Calculate");
        loadFileButton = new JButton("Load Graph from CSV");

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFile)dataGraph.clear();
                isFile = false;
                isdrawn = false;
                clear();
            }
        });
        GraphTool g = this;
        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Calc(g);
            }
        });

        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                File selectedFile = fileChooser.getSelectedFile();
                dataGraph = loadGraphFromCSV(selectedFile.getAbsolutePath());
                repaint();

            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loadFileButton);
        buttonPanel.add(reset);
        buttonPanel.add(calc);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);


        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(820, 640);
        setResizable(false);
        setTitle("Vewy Cool");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/JFrameImage/TestImg.jpg")));
        setCursor(CROSSHAIR_CURSOR);

    }

    public void clear() {
        kanten.clear();
        knoten.clear();
        x1.clear();
        x2.clear();
        y1.clear();
        y2.clear();
        kantenCounter = 0;
        knotenCounter = 0;
        index1.clear();
        index2.clear();
        repaint();


    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(Color.BLACK);
        kantenZeichnen(graphics);
        knotenZeichnen(graphics);
        if(isdrawn) {
            clear();
            documentZeichnen(graphics);
            isdrawn = false;
        }

    }

    private void knotenZeichnen(Graphics graphics) {

        for (int i = 0; i < knoten.size(); i++) {
            Point point = knoten.get(i);
            int x = point.x - radius;
            int y = point.y - radius;

            graphics.drawOval(x, y, radius * 2, radius * 2);

            String naming = Character.toString((char) ('A' + i));
            FontMetrics fm = graphics.getFontMetrics();
            int stringWidth = fm.stringWidth(naming);
            int stringHeight = fm.getHeight();
            graphics.drawString(naming, x + radius - stringWidth / 2, y + radius + stringHeight / 4);

        }
    }

    public void kantenZeichnen(Graphics graphics) {
        for (int i = 0; i < x1.size(); i++) {
            graphics.drawLine(x1.get(i), y1.get(i), x2.get(i), y2.get(i));
        }
    }

    public void documentZeichnen(Graphics graphics) {
        int size = dataGraph.size();
        Random rnd = new Random();
        Point rndPoint;

        for (int i = 1; i < size + 1; i++) {

            do {
                rndPoint = new Point(rnd.nextInt(40, 780), rnd.nextInt(40, 550));
            } while (isTooCloseToOtherKnoten(rndPoint, knoten));

            knoten.add(rndPoint);
            knotenCounter++;
        }

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (dataGraph.get(i).get(j) != 0) {
                    Point knoten1 = knoten.get(i);
                    Point knoten2 = knoten.get(j);
                        x1.add(knoten1.x);
                        y1.add(knoten1.y);
                        x2.add(knoten2.x);
                        y2.add(knoten2.y);
                        kantenZeichnen(graphics);
                        kantenCounter++;

                }
            }
        }

        System.out.println(knotenCounter);
    }


    private boolean isTooCloseToOtherKnoten(Point point, ArrayList<Point> knoten) {
        for (Point i : knoten) {
            if ((Math.sqrt(Math.pow(point.x - i.x, 2) + Math.pow(point.y - i.y, 2)) < 50)) {
                return true;
            }
        }
        return false;
    }

    /** From Document **/
    public ArrayList<ArrayList<Integer>> loadGraphFromCSV(String filePath) {
        dataGraph = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");

                ArrayList<Integer> row = new ArrayList<>();
                for (String i : values) {
                    row.add(Integer.parseInt(i));
                }
                dataGraph.add(row);
            }
            System.out.println(dataGraph);
            isFile = true;
            isdrawn = true;
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            isFile = false;
        }

        System.out.println(dataGraph);
        return dataGraph;
    }


    public static void main(String[] args) {
        new GraphTool();
    }
}
