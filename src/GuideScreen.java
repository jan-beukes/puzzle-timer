
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import MyComponents.InfoFrame;
import MyComponents.MyButton;

public class GuideScreen extends JPanel implements ActionListener, MouseListener { // The Guide screen

    private MyButton helpButton, backButton;
    private JPanel middlePanel, topPanel, topRightPanel, rightPanel;
    private JLabel titleLabel, jPermLabel;
    private ImageIcon helpIcon, pressedHelpIcon, cubeIcon;
    private JLabel[] linkLabels;

    GuideScreen() { // constructs the guide screen
        this.setBackground(Color.lightGray);
        this.setLayout(new BorderLayout());

        // sets all the icons to their image contained in in the resources
        try {
            helpIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/HelpIcon.png")));
            pressedHelpIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PressedHelpIcon.png")));
            cubeIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/JPermIcon.png")));
        } catch (IOException e) {

        }

        initComponents();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);

    }

    private void initComponents() { // Initializes and sets the values of all the components used on this screen
        middlePanel = new JPanel();
        topPanel = new JPanel();
        rightPanel = new JPanel();
        topRightPanel = new JPanel();

        middlePanel.setLayout(new GridLayout(5, 1));
        topPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
        topRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        topPanel.setPreferredSize(new Dimension(0, 100));
        rightPanel.setPreferredSize(new Dimension(170, 0));
        topRightPanel.setPreferredSize(new Dimension(170, 100));

        middlePanel.setOpaque(false);
        topPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        topRightPanel.setOpaque(false);

        helpButton = new MyButton();
        helpButton.setPreferredSize(new Dimension(60, 60));
        helpButton.setIcon(helpIcon);
        helpButton.setBackground(this.getBackground());
        helpButton.setVerticalAlignment(JLabel.TOP);
        helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpButton.setPressedColor(this.getBackground());
        helpButton.setPressedIcon(pressedHelpIcon);
        helpButton.addActionListener(this);

        backButton = new MyButton("BACK");
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBackground(Color.white);
        backButton.setFont(new Font("MV Boli", Font.BOLD, 32));
        backButton.setHorizontalTextPosition(JButton.LEFT);
        backButton.setVerticalTextPosition(JButton.TOP);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
        backButton.setPressedColor(Color.lightGray);
        backButton.addActionListener(this);

        jPermLabel = new JLabel();
        jPermLabel.setIcon(cubeIcon);
        jPermLabel.setPreferredSize(new Dimension(170, 520));
        jPermLabel.setVerticalAlignment(JLabel.BOTTOM);

        titleLabel = new JLabel("GUIDES ");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 80));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // sets the values for the array of link labels
        linkLabels = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            linkLabels[i] = new JLabel();
        }

        linkLabels[0].setText("Beginner Method");
        linkLabels[1].setText("Advanced Method");
        linkLabels[2].setText("J Perm Channel");
        linkLabels[3].setText("Cube Notations");
        linkLabels[4].setText("JPerm Website");

        for (JLabel i : linkLabels) {
            i.setFont(new Font("MV Boli", Font.BOLD, 50));
            i.setHorizontalAlignment(JLabel.CENTER);
            i.setForeground(Color.blue);
            i.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            i.addMouseListener(this);
        }
        linkLabels[4].setPreferredSize(new Dimension(400, 200));
        linkLabels[4].setVerticalTextPosition(JLabel.BOTTOM);

        for (JLabel i : linkLabels) {
            middlePanel.add(i);
        }

        topRightPanel.add(backButton);

        JPanel p = new JPanel();
        p.setOpaque(false);
        p.add(helpButton);

        topPanel.add(p, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        rightPanel.add(jPermLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {// called when an action is performed
                                                // and takes an ActionEvent as a Parameter
        if (e.getSource() == backButton) // when back button is pressed
        {
            MyFrame.setScreen("1");
        }
        if (e.getSource() == helpButton) { // when help button is pressed

            String help = "<html>The Guides Page has links to useful resources<br>that can help you if you"
                    + " are unfamiliar with speed cubing<br>or do not know how to solve a Rubik’s cube<br>"
                    + "I recommend watching the beginner method video if you<br>do not know how to solve a Rubik’s cube.<br>"
                    + "If you do know how to solve a Rubik’s cube, I recommend<br>checking out the advanced method and J Perm’s channel<br>"
                    + "To improve your skills, I recommend checking out the video on<br>Cube notations and "
                    + "using the J Perm website to practice<html>";

            InfoFrame f = new InfoFrame(help, true);
            f.setTitle("Info");

        }
    }

    private void openLink(String link) { // takes in a weblink contained in a string and opens it in the users browser
        try {

            Desktop.getDesktop().browse(new URI(link));

        } catch (IOException | URISyntaxException e) {

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) { // called when a Mouse event is performed
                                             // and takes the MouseEvent as a Parameter

        if (e.getSource() == linkLabels[0]) {// if the 1st label is clicked
            String l = "https://youtu.be/7Ron6MN45LY";
            openLink(l);
        } else if (e.getSource() == linkLabels[1]) {// if the 2nd label is clicked
            String l = "https://youtu.be/MS5jByTX_pk";
            openLink(l);
        } else if (e.getSource() == linkLabels[2]) {// if the 3rd label is clicked
            String l = "https://www.youtube.com/c/JPerm/featured";
            openLink(l);
        } else if (e.getSource() == linkLabels[3]) {// if the 4th label is clicked
            String l = "https://youtu.be/24eHm4ri8WM";
            openLink(l);
        } else if (e.getSource() == linkLabels[4]) {// if the 5th label is clicked
            String l = "https://jperm.net/";
            openLink(l);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {//required to be overridden when working with mouselistener

    }

    @Override
    public void mouseReleased(MouseEvent e) {//required to be overridden when working with mouselistener

    }

    @Override
    public void mouseEntered(MouseEvent e) {//when a label is hovered over it add an underline to the text
        for (JLabel i : linkLabels) {
            if (e.getSource() == i) {
                i.setText("<html><a href=''>" + i.getText() + "</a></html>");

            }

        }
    }

    @Override
    public void mouseExited(MouseEvent e) { //when a label is no longer hovered over it sets the text back to normal
        if (e.getSource() == linkLabels[0]) {
            linkLabels[0].setText("Beginner Method");
        } else if (e.getSource() == linkLabels[1]) {
            linkLabels[1].setText("Advanced Method");
        } else if (e.getSource() == linkLabels[2]) {
            linkLabels[2].setText("J Perm Channel");
        } else if (e.getSource() == linkLabels[3]) {
            linkLabels[3].setText("Cube Notations");
        } else if (e.getSource() == linkLabels[4]) {
            linkLabels[4].setText("JPerm Website");
        }

    }
}
