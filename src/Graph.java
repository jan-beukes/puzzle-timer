
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Graph extends JPanel { // The actual graph that is drawn in the graphPanel

    private final int WIDTH = 500, HEIGHT = 400;
    private double xUnit, yUnit; // xUnit is pixels per solve, yUnit is pixels per second
    private int solvesIncrement = 1, secondsIncrement = 1;
    private Point[] point;

    public Graph(int maxTime, int maxSolves) { // Constructs a Graph taking the maxTime and maxSolves as parameters
        maxTime = maxTime / 1000;

        // makes sure that the xUnit and YUnit are never less than 1 pixel
        if (WIDTH / maxSolves < 1) {
            solvesIncrement *= 2;
        }
        xUnit = WIDTH / (double) (maxSolves / solvesIncrement);

        if (HEIGHT / maxTime < 1) {
            secondsIncrement *= 2;
        }
        yUnit = HEIGHT / (double) (maxTime / secondsIncrement);

        point = new Point[Stats.getTotalSolves()];
        for (int i = 0; i < Stats.getTotalSolves(); i++) {
            point[i] = new Point(i + 1, Stats.getTime(i));
        }

    }

    @Override
    public void paintComponent(Graphics g) { // Custom painting to draw the graph
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        drawAllSolvesGraph(g2);
        drawPbPoints(g2);

        g2.dispose();
    }

    private void drawAllSolvesGraph(Graphics2D g2) { // draws the line graph of time against solves
                                                    // taking in the components graphics as a parameter

        for (int i = 0; i < Stats.getTotalSolves(); i++) {

            int x = (int) (point[i].getX() / solvesIncrement * xUnit);
            int y = 400 - (int) ((double) (point[i].getY() / 1000) / secondsIncrement * yUnit);
            if (y == 400) {
                y = 398;
            }

            g2.setPaint(Color.blue);
            g2.setStroke(new BasicStroke(3));
            if (i == 0) {
                g2.drawLine(0, y, x, y);
            } else {
                g2.drawLine((int) (point[i - 1].getX() / solvesIncrement * xUnit),
                        400 - (int) ((double) (point[i - 1].getY() / 1000) / secondsIncrement * yUnit), x, y);
            }

            g2.fillOval(x - 3, y - 3, 5, 5);

        }
    }

    private void drawPbPoints(Graphics2D g2) { // Draws Orange dots for solves that were a new PB
                                               // taking in the components graphics as a parameter
        ArrayList<Integer> pbSolves = Stats.getPbPositions();

        for (int i = 0; i < pbSolves.size(); i++) {
            int y = 400 - (int) ((Stats.getTime(pbSolves.get(i) - 1) / 1000.0) / secondsIncrement * yUnit) - 4;
            g2.setPaint(Color.orange);
            g2.fillOval((int) (pbSolves.get(i) / solvesIncrement * xUnit) - 4, y, 7, 7);
        }
    }

}
