
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GraphPanel extends JPanel { // The Panel containing everything on the GraphPage frame

    private Graph graph;

    public GraphPanel() { // Constructs the panel

        this.setLayout(null);

        graph = new Graph(getMaxTime(), getMaxSolves());
        graph.setBackground(Color.DARK_GRAY);
        graph.setBounds(100, 0, 510, 400);

        this.add(graph);
    }

    @Override
    public void paintComponent(Graphics g) { // custom painting to paint the text, graph increments and graph axes
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // draws the Axes
        g2.setPaint(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(98, 0, 98, 400);
        g2.drawLine(98, 400, 600, 400);

        // draws y axis increments
        g2.setFont(new Font("", Font.PLAIN, 12));
        for (int i = 0; i < 8; i++) {
            int y = 50;
            g2.drawLine(99, i * y, 90, i * y);
            if (i == 0) {
                g2.drawString(timeAtIndex(i), 60, i * y + 10);
            } else {
                g2.drawString(timeAtIndex(i), 60, i * y + 5);
            }
        }
        // draws x axis increments
        for (int i = 10; i > 0; i--) {
            int x = 50;
            g2.drawLine(100 + (i * x), 400, 100 + (i * x), 410);
            g2.drawString(solvesAtIndex(i), 94 + (i * x), 430);
        }
        // draws The text
        g2.setPaint(Color.orange);
        g2.setFont(new Font("Consolas", Font.PLAIN, 25));
        g2.fillOval(520, 463, 10, 10);
        g2.drawString("PBs", 540, 475);

        g2.setPaint(Color.white);
        g2.setFont(new Font("Consolas", Font.BOLD, 35));
        g2.drawString("SOLVES", 280, 480);

        FontMetrics fm = g2.getFontMetrics();
        int x = 50 + (600 - fm.stringWidth("Time(s)")) / 2;
        int y = -20;
        g2.rotate(Math.toRadians(-90), 610 / 2, 500 / 2);
        g2.drawString("Time(s)", x, y);
        g2.dispose();
    }

    private String solvesAtIndex(int index) { // takes in an increment of the x axis and returns
                                              // the solve number that should be displayed on that increment
        if (Stats.getTotalSolves() < 10) {
            return Integer.toString(index);
        }

        int maxSolves = getMaxSolves();

        return Integer.toString(index * (maxSolves / 10));
    }

    private String timeAtIndex(int index) { // takes in an increment of the y axis and returns
                                            // the time that should be displayed on that increment

        int maxTime = getMaxTime();

        return Integer.toString(((8 - index) * (maxTime / 8)) / 1000);
    }

    private int getMaxSolves() { //returns the max solves that the x axis should go upto
        int add = 10 - Stats.getTotalSolves() % 10;
        int maxSolves = Stats.getTotalSolves() + add;
        return maxSolves;
    }

    private int getMaxTime() { //returns the max time that the y axis should go upto
        int highestTime = 0;
        for (int i = 0; i < Stats.getTotalSolves(); i++) {
            if (Stats.getTime(i) > highestTime) {
                highestTime = Stats.getTime(i);
            }
        }
        int addInc;

        if (highestTime <= 60000) {
            addInc = 20000;
        } else {
            addInc = 60000;
        }
        int add = addInc - (highestTime % addInc);
        int maxTime = highestTime + add;
        return maxTime;
    }
}
