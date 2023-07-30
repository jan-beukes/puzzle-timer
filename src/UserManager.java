
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class UserManager { //Manages the user system

    private static ArrayList<User> userList;
    public static User currentUser;
    private static final String PATH = System.getProperty("user.home") + "/PuzzleTimer";
    private static File usersDB;
    private static File userFile = new File(PATH + "/user.txt");

    private static Connection conn;

    public static void loadUsers() { //Loads the users from the database
        userList = new ArrayList<User>();
        usersDB = new File(PATH + "/UsersDB.accdb");

        // checks if the Puzzle Timer folder exists and if it does not creates it
            File theDir = new File(PATH);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
        
        if (!usersDB.exists()) { //creates the users database if it does not exist
            try {
                DatabaseBuilder.create(Database.FileFormat.V2016, usersDB);
                conn = DriverManager.getConnection("jdbc:ucanaccess://" + usersDB.getAbsolutePath());
                Statement st = conn.createStatement();
                st.execute("CREATE TABLE tblUsers (UserID integer,UserName text, "
                        + "TimesFile text, IconFile text)");

                st.execute("INSERT INTO tblUsers(UserID, UserName, TimesFile, IconFile) VALUES (1, \"User1\", \"/User1Times.csv\", \"/UserIcon.png\")");
            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(null, "There was an Error creating the Database", "ERROR",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {

        }

        try { //connect to DB and loads users in List

            conn = DriverManager.getConnection("jdbc:ucanaccess://" + usersDB.getAbsolutePath());
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tblUsers");

            while (rs.next()) {
                User user = new User(rs.getString(2),rs.getString(3), rs.getString(4));
                userList.add(user);
            }

            if (userFile.exists()) { //sets current User to the previosly active user
                BufferedReader br = new BufferedReader(new FileReader(userFile));
                int cU = Integer.valueOf(br.readLine());
                currentUser = userList.get(cU);
            } else {
                currentUser = userList.get(0);
            }

        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error connecting to the Database", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    public static boolean checkIfNew() { // checks if the user is new by reading a txt file
        boolean isNew = true;
        BufferedReader br;
        try {
            userFile.createNewFile();
            br = new BufferedReader(new FileReader(userFile));

            if (br.readLine() != null) {
                isNew = false;
            }

            if (isNew) {
           
                PrintWriter writer = new PrintWriter(new FileWriter(userFile));
                writer.print(0);
                writer.close();
            }

            br.close();
        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "File user.txt could not be found", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return isNew;

    }
    
    public static String[] getUsers(){ //returns String array of the users
     String[] temp = new String[userList.size()];
     
        for (int i = 0; i < userList.size(); i++) {
            temp[i] = userList.get(i).getUserName();
        }
     return temp;
    }
    
    public static int getUserIndex(){
    return userList.indexOf(currentUser);
    }

    public static void switchUser(int index) { //changes current user and stores it
        currentUser = userList.get(index);
        Stats.loadTimes();
        Stats.updateLabels();
        
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(userFile));
            writer.print(index);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public static void createUser(String name) { //creates a new user and saves it into the Database
        User user = new User(name, "/" + name + "Times.csv", "/UserIcon.png");
        userList.add(user);
       
        try {
             user.getTimeFile().createNewFile();
             
            Statement st = conn.createStatement();
            st.execute("INSERT INTO tblUsers(UserID, UserName, TimesFile, IconFile) VALUES (" + userList.size() + ",\"" + name + "\", \"/" + name + "Times.csv\", \"/UserIcon.png\")");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deleteUser(int index) { //deletes the current user and removes them from the database
        userList.get(index).getTimeFile().delete();
        userList.remove(index);
        switchUser(0);
        
        Statement st;
        try {
            st = conn.createStatement();
            st.execute("DELETE FROM tblUsers WHERE UserID = " + (index + 1));
            st.execute("UPDATE tblUsers set UserID = UserID - 1 WHERE UserID > " + (index + 1));
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void updateIcon(String path) { //updates the user's icon
        currentUser.setIcon(path);

        try {
            Statement st = conn.createStatement();
            System.out.println(path + " " + currentUser.getUserName());
            st.execute("UPDATE tblUsers SET IconFile = \"" + path + "\" WHERE UserName = \"" + currentUser.getUserName() + "\"");
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
