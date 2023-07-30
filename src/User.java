
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class User { //User Object class
    
    private String userName;
    private File timeFile;
    private ImageIcon icon;
    private static final String PATH = System.getProperty("user.home") + "/PuzzleTimer";

    public User(String userName, String timeFilePath, String iconPath) {
        this.userName = userName;
        this.timeFile = new File(PATH + timeFilePath);
        if (iconPath.charAt(0) == '/') {
            try {
                this.icon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(iconPath)));
            } catch (IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            this.icon = new ImageIcon(iconPath);
        }

    }

    public String getUserName() {
        return userName;
    }

    public File getTimeFile() {
        return timeFile;
    }
    
    public ImageIcon getIcon() {
        Image image = icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        return scaledIcon;
    }

    public void setIcon(String path){
     icon = new ImageIcon(path);   
        
    }
}
