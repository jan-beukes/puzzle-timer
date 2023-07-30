
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import MyComponents.InfoFrame;
import MyComponents.MyButton;

public class StatsScreen extends JPanel implements ActionListener { // The Stats screen

    private MyButton backButton, helpButton, clearButton, graphButton;
    private JPanel topPanel, midPanel, rightPanel, topRightPanel;
    private JLabel titleLabel, solvesLabel, avgLabel, ao5Label, pbLabel;

    private ImageIcon helpIcon, pressedHelpIcon, clearIcon, pressedClearIcon, graphIcon, pressedGraphIcon;

    StatsScreen() { // Constructs the StatsScreen
        this.setLayout(new BorderLayout());

        // sets all the icons to their image contained in in the resources
        try {
            helpIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/HelpIcon.png")));
            pressedHelpIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PressedHelpIcon.png")));
            clearIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/ClearIcon.png")));
            pressedClearIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PressedClearIcon.png")));
            graphIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/GraphIcon.png")));
            pressedGraphIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PressedGraphIcon.png")));

        } catch (IOException e) {
           
        }

        // sets all the panels
        topPanel = new JPanel(new BorderLayout());
        midPanel = new JPanel(new GridLayout(4, 1));
        rightPanel = new JPanel(null);
        topRightPanel = new JPanel();

        topPanel.setPreferredSize(new Dimension(0, 100));
        rightPanel.setPreferredSize(new Dimension(110, 0));
        topRightPanel.setPreferredSize(new Dimension(110, 100));

        rightPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBackground(Color.lightGray);
        topRightPanel.setBackground(Color.DARK_GRAY);
        midPanel.setBackground(Color.LIGHT_GRAY);

        initButtonsAndLabels();

        midPanel.add(solvesLabel);
        midPanel.add(avgLabel);
        midPanel.add(ao5Label);
        midPanel.add(pbLabel);

        topRightPanel.add(backButton);
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.add(helpButton);

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(p, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        rightPanel.add(clearButton);
        rightPanel.add(graphButton);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(midPanel, BorderLayout.CENTER);

    }

    private void initButtonsAndLabels() { // initializes and sets all the values for the buttons and labels

        titleLabel = new JLabel("STATS ");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 80));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        solvesLabel = new JLabel(
        String.format("%-17s", "TOTAL SOLVES") + String.format("%-8s", Stats.getTotalSolves()));
        avgLabel = new JLabel(String.format("%-17s", "AVERAGE") + Stats.getAvgStr());
        ao5Label = new JLabel(String.format("%-16s", "AVERAGE OF 5") + Stats.getAO5Str());
        pbLabel = new JLabel(String.format("%-15s", "PERSONAL BEST") + Stats.getPbStr());

        solvesLabel.setHorizontalAlignment(JLabel.CENTER);
        avgLabel.setHorizontalAlignment(JLabel.CENTER);
        ao5Label.setHorizontalAlignment(JLabel.CENTER);
        pbLabel.setHorizontalAlignment(JLabel.CENTER);

        solvesLabel.setFont(new Font("MV Boli", Font.PLAIN, 45));
        avgLabel.setFont(new Font("MV Boli", Font.PLAIN, 45));
        ao5Label.setFont(new Font("MV Boli", Font.PLAIN, 45));
        pbLabel.setFont(new Font("MV Boli", Font.PLAIN, 45));

        helpButton = new MyButton();
        helpButton.setPreferredSize(new Dimension(60, 60));
        helpButton.setIcon(helpIcon);
        helpButton.setBackground(topPanel.getBackground());
        helpButton.setVerticalAlignment(JLabel.TOP);
        helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpButton.setPressedColor(topPanel.getBackground());
        helpButton.setPressedIcon(pressedHelpIcon);
        helpButton.addActionListener(this);

        backButton = new MyButton("BACK");
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBackground(Color.white);
        backButton.setFont(new Font("MV Boli", Font.BOLD, 32));
        backButton.setHorizontalAlignment(JButton.CENTER);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
        backButton.setPressedColor(Color.lightGray);
        backButton.addActionListener(this);

        clearButton = new MyButton();
        clearButton.setBounds(0, 439, 110, 131);
        clearButton.setBackground(rightPanel.getBackground());
        clearButton.setIcon(clearIcon);
        clearButton.setPressedIcon(pressedClearIcon);
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.setPressedColor(rightPanel.getBackground());
        clearButton.addActionListener(this);

        graphButton = new MyButton();
        graphButton.setBounds(0, 175, 110, 110);
        graphButton.setBackground(rightPanel.getBackground());
        graphButton.setIcon(graphIcon);
        graphButton.setPressedIcon(pressedGraphIcon);
        graphButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        graphButton.setPressedColor(rightPanel.getBackground());
        graphButton.addActionListener(this);
    }

    @Override
    public void paint(Graphics g) { // custom painting to paint a straight line
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g.create();

        g2D.setPaint(Color.black);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(500, 150, 500, 630);

        g2D.dispose();
    }

    public void update() { // updates the values of the labels that display the stats
        solvesLabel.setText(String.format("%-17s", "TOTAL SOLVES") + String.format("%-8s", Stats.getTotalSolves()));
        avgLabel.setText(String.format("%-17s", "AVERAGE") + Stats.getAvgStr());
        ao5Label.setText(String.format("%-16s", "AVERAGE OF 5") + Stats.getAO5Str());
        pbLabel.setText(String.format("%-15s", "PERSONAL BEST") + Stats.getPbStr());
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {// called when an action is performed
                                                // and takes an ActionEvent as a Parameter
        if (e.getSource() == backButton) { // when backbutton is pressed
            MyFrame.setScreen("1");

        }
        if (e.getSource() == helpButton) { // when help button is pressed

            String help = "<html>The Stats Page gives a more detailed look at your statistics<br>"
                    + "Here you can see your total amount of solves, your <br>average solve time,"
                    + "your average time of the last 5 solves<br>and your personal best. "
                    + "You can click on the graph button to<br>open a graph that displays your times and personal best solves<br>"
                    + "against your total solves.The bin button can be pressed to<br>clear all your stats<html>";

            InfoFrame f = new InfoFrame(help, true);
            f.setTitle("Info");

        }

        if (e.getSource() == clearButton) { //when the clear button is pressed
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all your data", null,
                    JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                Stats.delete();
                update();
            }
        }

        if (e.getSource() == graphButton) { //when the graph button is pressed
            GraphPage graphPage = new GraphPage();
        }
    }

}
