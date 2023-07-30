
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Stats { // Handles all the statistics (Times, Averages, personal bests etc)
    // and loads/saves data

    // uses static variables and methods since instances of this class
    // are not created and all the others can access the stats
    private static ArrayList<Integer> timesList;
    private static int average, ao5, pb, prev, totalSolves;
    private static String avgStr, ao5Str, pbStr, prevStr;

    // used to save stats to files on users PC
    private static String path = System.getProperty("user.home") + File.separator + "PuzzleTimer";
    private static File timeFile;

    public static void loadTimes() { // loads the times from a csv file that is created on the users PC
        timesList = new ArrayList<>();
        timeFile = UserManager.currentUser.getTimeFile();

        BufferedReader reader = null;
        
        try{

            // Creates a csv file if it doesn't exists
            timeFile.createNewFile();
            reader = new BufferedReader(new FileReader(timeFile));
            String line = reader.readLine();
            // reads the lines of the file and adds each value into the timesList ArrayList
            while (line != null) {
                String[] row = line.split(",");

                for (int i = 0; i < row.length; i++) {
                    timesList.add(Integer.parseInt(row[i]));
                }

                line = reader.readLine();
            }
            calcStats();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Time file could not be found", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(null, "Invalid value in Times.csv " + ne.getMessage() + " ", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } finally {

            try {
                reader.close();
            } catch (IOException e) {

            }
        }
    }

    public static void addTime(int time) { // takes in a time and adds/saves it to the csv file and the ArrayList
        PrintWriter writer = null;
        try {
            // appends the time to the end and adds a comma
            writer = new PrintWriter(new BufferedWriter(new FileWriter(timeFile, true)));
            writer.print(Integer.toString(time));
            writer.print(",");
            timesList.add(time);
            if (timesList.size() % 20 == 0) {
                writer.print("\n");
            }
            calcStats();
            updateLabels();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Time file could not be found", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
        } finally {

            writer.close();

        }

    }

    private static void calcStats() { // Calculates the average, average of 5, PB, previous solve and total solves

        // only does calculations if there is 1 or more solves
        if (timesList.size() > 0) {

            totalSolves = timesList.size();
            prev = timesList.get(timesList.size() - 1);

            int sum = 0, ao5Sum = 0, ao5MaxIndex = timesList.size() - 5, ao5MinIndex = timesList.size() - 5;
            pb = timesList.get(0);
            // calculates the sum and sets the pb
            for (int i = 0; i < timesList.size(); i++) {
                sum += timesList.get(i);
                if (timesList.get(i) < pb) {
                    pb = timesList.get(i);
                }
            }
            average = sum / timesList.size();

            // if there are more than 5 solves the Average of 5 is calculated here
            if (timesList.size() >= 5) {
                for (int i = timesList.size() - 5; i < timesList.size(); i++) {
                    if (timesList.get(i) > timesList.get(ao5MaxIndex)) {
                        ao5MaxIndex = i;
                    } else if (timesList.get(i) < timesList.get(ao5MinIndex)) {
                        ao5MinIndex = i;
                    }
                    ao5Sum += timesList.get(i);
                }
                ao5Sum = ao5Sum - timesList.get(ao5MaxIndex) - timesList.get(ao5MinIndex);
                ao5 = ao5Sum / 3;
            } else {
                // if there are less than 5 solves it just gets the average of all the solves
                for (int i = 0; i < timesList.size(); i++) {
                    ao5Sum += timesList.get(i);
                }
                ao5 = ao5Sum / timesList.size();
            }
            
        } else {
            average = 0;
            ao5 = 0;
            pb = 0;
            prev = 0;
            totalSolves = 0;
        }

    }

    public static int getTotalSolves() { // returns total solves
        return totalSolves;
    }

    public static String getAvgStr() { // returns Average as a String in the proper time format
        int min = average / 60000 % 60;
        int sec = average / 1000 % 60;
        int mil = Math.round(average / 10 % 100);

        String millisecondsStr = String.format("%02d", mil);
        String secondsStr = String.format("%02d", sec);
        String minutesStr = String.format("%d", min);

        avgStr = minutesStr + ":" + secondsStr + "." + millisecondsStr;

        return avgStr;
    }

    public static String getAO5Str() { // returns Average of 5 as a String in the proper time format
        int min = ao5 / 60000 % 60;
        int sec = ao5 / 1000 % 60;
        int mil = Math.round(ao5 / 10 % 100);

        String millisecondsStr = String.format("%02d", mil);
        String secondsStr = String.format("%02d", sec);
        String minutesStr = String.format("%d", min);

        ao5Str = minutesStr + ":" + secondsStr + "." + millisecondsStr;

        return ao5Str;
    }

    public static String getPbStr() { // returns PB as a String in the proper time format
        int min = pb / 60000 % 60;
        int sec = pb / 1000 % 60;
        int mil = Math.round(pb / 10 % 100);

        String millisecondsStr = String.format("%02d", mil);
        String secondsStr = String.format("%02d", sec);
        String minutesStr = String.format("%d", min);

        pbStr = minutesStr + ":" + secondsStr + "." + millisecondsStr;

        return pbStr;
    }

    public static String getPrevStr() { // returns Previous solve as a String in the proper time format
        int min = prev / 60000 % 60;
        int sec = prev / 1000 % 60;
        int mil = Math.round(prev / 10 % 100);

        String millisecondsStr = String.format("%02d", mil);
        String secondsStr = String.format("%02d", sec);
        String minutesStr = String.format("%d", min);

        prevStr = minutesStr + ":" + secondsStr + "." + millisecondsStr;

        return prevStr;
    }

    public static int getAverage() { // returns average
        return average;
    }

    public static int getTime(int index) { // takes in an index and return the
        // time at that index in the ArrayList
        return timesList.get(index);

    }

    public static int getPb() { // returns pb
        return pb;
    }

    public static ArrayList<Integer> getPbPositions() { // returns an ArrayList of all the
        // solves that were the users new PB
        ArrayList<Integer> pbPos = new ArrayList<>();
        int pb;
        if (totalSolves > 0) {
            pbPos.add(1);
            pb = timesList.get(0);

            for (int i = 1; i < totalSolves; i++) {
                if (timesList.get(i) < pb) {
                    pbPos.add(i + 1);
                    pb = timesList.get(i);
                }

            }

        }
        return pbPos;
    }

    public static void updateLabels() { // Updates the labels on the timer screen that display the stats
        
        TimerScreen.setAvgLabel(getAvgStr());
        TimerScreen.setAo5Label(getAO5Str());
        TimerScreen.setPbLabel(getPbStr());
        TimerScreen.setSolvesLabel(Integer.toString(totalSolves));
        TimerScreen.setPrevLabel(getPrevStr());

    }

    public static void delete() { // Delete the times in the file and reset all the Stats
        timeFile.delete();
        timesList.clear();
        calcStats();
        updateLabels();
        try {
            timeFile.createNewFile();
        } catch (IOException e) {

        }
    }

}
