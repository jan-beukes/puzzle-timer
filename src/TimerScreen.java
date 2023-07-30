
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import MyComponents.InfoFrame;
import MyComponents.MyButton;

public class TimerScreen extends JPanel implements ActionListener { // The main Timer Screen

    private ImageIcon helpIcon, guideIcon, statsIcon, pressedHelpIcon, pressedGuideIcon, pressedStatsIcon;
    private MyButton helpButton, guideButton, statsButton, userButton;
    private JPanel titlePanel, statsPanel, barPanel, stopwatchPanel, innerStatsPanel1, innerTitlePanel1,
            innerStatsPanel2, innerTitlePanel2;
    private static JLabel titleLabel, solvesLabel, avgLabel, ao5Label, pbLabel, prevLabel;
    Stopwatch stopwatch;

    TimerScreen() { // Constructs the Timer Screen
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BorderLayout());

        // sets all the icons to their image contained in in the resources
        try {
            helpIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/HelpIcon.png")));
            guideIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GuideIcon.png")));
            statsIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/StatsIcon.png")));
            pressedHelpIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/PressedHelpIcon.png")));
            pressedGuideIcon = new ImageIcon(
                    ImageIO.read(this.getClass().getResourceAsStream("/PressedGuideIcon.png")));
            pressedStatsIcon = new ImageIcon(
                    ImageIO.read(this.getClass().getResourceAsStream("/PressedStatsIcon.png")));
        } catch (IOException e) {

        }

        initPanels();
        initButtonsAndLabels();

        stopwatch = new Stopwatch();

        stopwatchPanel.add(stopwatch);

        innerTitlePanel1.add(helpButton);

        innerTitlePanel2.add(titleLabel);
        innerTitlePanel2.add(solvesLabel);

        barPanel.add(guideButton);
        barPanel.add(statsButton);

        innerStatsPanel2.add(avgLabel);
        innerStatsPanel2.add(pbLabel);
        innerStatsPanel2.add(ao5Label);
        innerStatsPanel2.add(prevLabel);

        innerStatsPanel1.add(userButton);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(barPanel, BorderLayout.WEST);
        this.add(stopwatchPanel, BorderLayout.CENTER);
        this.add(statsPanel, BorderLayout.SOUTH);
    }

    private void initPanels() { // Initializes and sets vaues for Panels
        titlePanel = new JPanel();
        statsPanel = new JPanel();
        barPanel = new JPanel();
        stopwatchPanel = new JPanel();
        innerStatsPanel1 = new JPanel();
        innerTitlePanel1 = new JPanel();
        innerStatsPanel2 = new JPanel();
        innerTitlePanel2 = new JPanel();

        stopwatchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setLayout(new BorderLayout());
        statsPanel.setLayout(new BorderLayout());
        barPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 25));
        innerStatsPanel2.setLayout(new GridLayout(2, 2));
        innerTitlePanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 400, 0));

        titlePanel.setPreferredSize(new Dimension(0, 150));
        statsPanel.setPreferredSize(new Dimension(0, 250));
        barPanel.setPreferredSize(new Dimension(100, 0));
        stopwatchPanel.setPreferredSize(new Dimension(400, 200));
        innerStatsPanel1.setPreferredSize(new Dimension(100, 0));
        innerTitlePanel1.setPreferredSize(new Dimension(100, 0));

        titlePanel.setBackground(Color.lightGray);
        barPanel.setBackground(Color.darkGray);
        statsPanel.setBackground(Color.lightGray);
        stopwatchPanel.setBackground(Color.lightGray);
        innerTitlePanel1.setBackground(Color.darkGray);
        innerStatsPanel1.setBackground(Color.darkGray);
        innerTitlePanel2.setOpaque(false);
        innerStatsPanel2.setOpaque(false);

        titlePanel.add(innerTitlePanel1, BorderLayout.WEST);
        statsPanel.add(innerStatsPanel1, BorderLayout.WEST);
        titlePanel.add(innerTitlePanel2, BorderLayout.CENTER);
        statsPanel.add(innerStatsPanel2, BorderLayout.CENTER);

    }

    private void initButtonsAndLabels() { // Initializes and sets vaues for Buttons and Labels

        titleLabel = new JLabel("TIMER");
        solvesLabel = new JLabel("Total Solves: " + Stats.getTotalSolves());
        avgLabel = new JLabel("Avg: " + Stats.getAvgStr());
        ao5Label = new JLabel("AO5: " + Stats.getAO5Str());
        pbLabel = new JLabel("PB: " + Stats.getPbStr());
        prevLabel = new JLabel("Prev: " + Stats.getPrevStr());

        titleLabel.setFont(new Font("Consolas", Font.BOLD, 80));
        solvesLabel.setFont(new Font("MV Boli", Font.PLAIN, 40));
        avgLabel.setFont(new Font("MV Boli", Font.PLAIN, 40));
        ao5Label.setFont(new Font("MV Boli", Font.PLAIN, 40));
        pbLabel.setFont(new Font("MV Boli", Font.PLAIN, 40));
        prevLabel.setFont(new Font("MV Boli", Font.PLAIN, 40));

        avgLabel.setHorizontalAlignment(JLabel.CENTER);
        ao5Label.setHorizontalAlignment(JLabel.CENTER);
        pbLabel.setHorizontalAlignment(JLabel.CENTER);
        prevLabel.setHorizontalAlignment(JLabel.CENTER);

        helpButton = new MyButton();
        guideButton = new MyButton();
        statsButton = new MyButton();
        userButton = new MyButton();

        helpButton.setPreferredSize(new Dimension(50, 50));
        guideButton.setPreferredSize(new Dimension(100, 100));
        statsButton.setPreferredSize(new Dimension(100, 100));
        userButton.setPreferredSize(new Dimension(100, 100));

        helpButton.setIcon(helpIcon);
        guideButton.setIcon(guideIcon);
        statsButton.setIcon(statsIcon);
        userButton.setIcon(UserManager.currentUser.getIcon());

        helpButton.setBackground(innerTitlePanel1.getBackground());
        guideButton.setBackground(barPanel.getBackground());
        statsButton.setBackground(barPanel.getBackground());
        userButton.setBackground(barPanel.getBackground());

        helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        guideButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        statsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        helpButton.setPressedColor(innerTitlePanel1.getBackground());
        guideButton.setPressedColor(barPanel.getBackground());
        statsButton.setPressedColor(barPanel.getBackground());
        userButton.setPressedColor(barPanel.getBackground());

        helpButton.setPressedIcon(pressedHelpIcon);
        guideButton.setPressedIcon(pressedGuideIcon);
        statsButton.setPressedIcon(pressedStatsIcon);

        helpButton.addActionListener(this);
        guideButton.addActionListener(this);
        statsButton.addActionListener(this);
        userButton.addActionListener(this);
    }

    private void showHelp() { // creates an InfoFrame
        String help = "<html>The Timer page is the main page of the program and contains<br>the Stopwatch, "
                + "some statistics as well as buttons to other pages.<br>"
                + "The Stopwatch can be used by pressing the start button or<br>pressing the SPACEBAR KEY."
                + " After stopping the time you must<br>either save, add 2 seconds to the time or discard it by using<br>"
                + "the respective buttons. After the time is saved, your stats are<br>calculated and displayed in "
                + "minutes seconds and milliseconds.<br>"
                + " If you are unfamiliar with solving Rubik’s cubes click on the<br>book button to go to the "
                + "guides page that has many useful<br>resources"
                + ". To see a more in depth look at your statistics<br>click on the graph button to "
                + "go the statistics page <html>";

        InfoFrame f = new InfoFrame(help, true);
        f.setTitle("Info");

    }

    @Override
    public void actionPerformed(ActionEvent e) {// called when an action is performed
        // and takes an ActionEvent as a Parameter
        if (e.getSource() == guideButton) { // when the guide button is pressed
            MyFrame.setScreen("2");
        } else if (e.getSource() == statsButton) { // when the start button is pressed
            MyFrame.setScreen("3");
        } else if (e.getSource() == userButton) {
            MyFrame.setScreen("4");
        } else if (e.getSource() == helpButton) { // when the help button is pressed
            showHelp();
        }

    }

    public static void setSolvesLabel(String text) { //sets the Solves label taking in text as a parameter
        solvesLabel.setText("Total Solves: " + text);

    }

    public static void setAvgLabel(String text) { //sets the Average label taking in text as a parameter
        avgLabel.setText("Avg: " + text);
    }

    public static void setAo5Label(String text) { //sets the Average of 5 label taking in text as a parameter
        ao5Label.setText("AO5: " + text);
    }

    public static void setPbLabel(String text) { //sets the PB label taking in text as a parameter
        pbLabel.setText("PB: " + text);
    }

    public static void setPrevLabel(String text) { //sets the Previous label taking in text as a parameter
        prevLabel.setText("Prev: " + text);
    }
    
    public void updateUserIcon(){ // Updates displayed icon on user button
     userButton.setIcon(UserManager.currentUser.getIcon());   
    }

    public Stopwatch getStopwatch() { //returns the Stopwatch
        return stopwatch;
    }

}
