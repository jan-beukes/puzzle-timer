
import MyComponents.InfoFrame;
import MyComponents.MyButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UserScreen extends JPanel implements ActionListener, MouseListener {

    private MyButton backButton, helpButton, createButton, deleteButton;
    private JPanel topPanel, midPanel, topRightPanel;
    private JLabel titleLabel, iconLabel;
    private JComboBox userBox;
    private ImageIcon helpIcon, pressedHelpIcon;

    public UserScreen() { //Constructs the User Screen

        try {
            helpIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/HelpIcon.png")));
            pressedHelpIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PressedHelpIcon.png")));
        } catch (IOException ex) {
            Logger.getLogger(UserScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setLayout(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());
        midPanel = new JPanel(new FlowLayout());
        topRightPanel = new JPanel();

        topPanel.setBackground(Color.LIGHT_GRAY);
        midPanel.setBackground(Color.LIGHT_GRAY);
        topRightPanel.setBackground(Color.LIGHT_GRAY);

        topPanel.setPreferredSize(new Dimension(0, 100));

        initComponents();

        topRightPanel.add(backButton);

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(helpButton, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        midPanel.add(iconLabel);
        midPanel.add(userBox);
        midPanel.add(createButton);
        midPanel.add(deleteButton);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);

    }

    private void initComponents() { //Initialize UI Components
        titleLabel = new JLabel("USERS ");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 80));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

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
        backButton.setHorizontalTextPosition(JButton.LEFT);
        backButton.setVerticalTextPosition(JButton.TOP);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
        backButton.setPressedColor(Color.lightGray);
        backButton.addActionListener(this);

        createButton = new MyButton("CREATE");
        createButton.setPreferredSize(new Dimension(100, 50));
        createButton.setBackground(Color.gray);
        createButton.setFont(new Font("MV Boli", Font.BOLD, 18));
        createButton.setHorizontalTextPosition(JButton.LEFT);
        createButton.setVerticalTextPosition(JButton.TOP);
        createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createButton.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
        createButton.setPressedColor(Color.darkGray);
        createButton.setForeground(Color.white);
        createButton.addActionListener(this);

        deleteButton = new MyButton("DELETE");
        deleteButton.setPreferredSize(new Dimension(100, 50));
        deleteButton.setBackground(Color.gray);
        deleteButton.setFont(new Font("MV Boli", Font.BOLD, 18));
        deleteButton.setHorizontalTextPosition(JButton.LEFT);
        deleteButton.setVerticalTextPosition(JButton.TOP);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
        deleteButton.setPressedColor(Color.darkGray);
        deleteButton.setForeground(Color.red);
        deleteButton.addActionListener(this);

        iconLabel = new JLabel("Click to change icon");
        iconLabel.setIcon(UserManager.currentUser.getIcon());
        iconLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
        iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        iconLabel.setHorizontalTextPosition(JLabel.CENTER);
        iconLabel.setVerticalTextPosition(JLabel.TOP);

        userBox = new JComboBox(UserManager.getUsers());
        userBox.setSelectedIndex(UserManager.getUserIndex());
        userBox.setFont(new Font("MV Boli", Font.BOLD, 20));
        userBox.setBackground(Color.darkGray);
        userBox.setForeground(Color.white);
        userBox.setFocusable(false);
        userBox.addActionListener(this);
        
        
        iconLabel.addMouseListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) { // when backbutton is pressed
            MyFrame.setScreen("1");
        }
        if (e.getSource() == helpButton) { // when help button is pressed

            String help = "<html>The User Page allows you to create, edit and switch between<br>user profiles."
                    + " Clicking on the icon image allows you to select a custom icon<br>for the current user."
                    + " Easily switch between users using the combobox or<br>create a new user by just entering a new userName";

            InfoFrame f = new InfoFrame(help, true);
            f.setTitle("Info");
        }

        if (e.getSource() == userBox) { //When user is selected from combo box
            JComboBox j = (JComboBox) e.getSource();
            UserManager.switchUser(j.getSelectedIndex());

            iconLabel.setIcon(UserManager.currentUser.getIcon());

        }

        if (e.getSource() == deleteButton) { // When delete button is pressed
            if (UserManager.getUsers().length <= 1) {
                return;
               
            }

            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user", null,
                    JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                int index = userBox.getSelectedIndex();
                userBox.setSelectedIndex(0);
                userBox.removeItemAt(index);
                UserManager.deleteUser(index);
                iconLabel.setIcon(UserManager.currentUser.getIcon());
            }

        }
        
        if (e.getSource() == createButton){ //when create button is pressed
          String name = JOptionPane.showInputDialog(this, "Enter User Name", "Create User", JOptionPane.PLAIN_MESSAGE);
          if (name == null){
              return;
          }
          
          UserManager.createUser(name);
          userBox.addItem(name);
          userBox.setSelectedIndex(userBox.getItemCount()-1);
          UserManager.switchUser(userBox.getItemCount()-1);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) { //When the Icon is clicked on
        if (e.getSource() == iconLabel) {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Image files (*.GIF,*.PNG,*.JPG, *.JPEG)", "GIF", "PNG", "JPG", "JPEG"));

            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                UserManager.updateIcon(file.getAbsolutePath());

                iconLabel.setIcon(UserManager.currentUser.getIcon());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
