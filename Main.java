import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Eli Woodard
 */
public class Main {
    private static final int TIMER_DELAY = 1000;

    private JFrame frame;
    private JTabbedPane tabs;
    private JPanel timerTab, countdownTab;
    private JLabel timerLabel, countdownLabel;
    private JButton startButton, stopButton, setButton;
    private JTextField countdownField;
    private Timer timer;
    private TimerTask timerTask;
    private int timerCount;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
            }
        });
    }

    /**
     * Creates a new Timer Program with a JFrame containing a JTabbedPane with a Timer tab and a Countdown tab.
     * The Timer tab allows the user to start and stop a timer, and displays the time in "HH:MM:SS" format.
     * The Countdown tab allows the user to set a countdown timer in "HH:MM:SS" format, and displays the remaining time in the same format.
     */
    public Main() {
        frame = new JFrame("Timer Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timerTab = new JPanel(new BorderLayout());
        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });
        JPanel timerButtonsPanel = new JPanel();
        timerButtonsPanel.add(startButton);
        timerButtonsPanel.add(stopButton);
        timerTab.add(timerLabel, BorderLayout.CENTER);
        timerTab.add(timerButtonsPanel, BorderLayout.SOUTH);

        countdownTab = new JPanel(new BorderLayout());
        countdownLabel = new JLabel("00:00:00");
        countdownLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countdownField = new JTextField("00:00:00");
        countdownField.setHorizontalAlignment(SwingConstants.CENTER);
        setButton = new JButton("Set");
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCountdown();
            }
        });
        JPanel countdownButtonsPanel = new JPanel();
        countdownButtonsPanel.add(countdownField);
        countdownButtonsPanel.add(setButton);
        countdownTab.add(countdownLabel, BorderLayout.CENTER);
        countdownTab.add(countdownButtonsPanel, BorderLayout.SOUTH);

        tabs = new JTabbedPane();
        tabs.addTab("Timer", timerTab);
        tabs.addTab("Countdown", countdownTab);

        frame.add(tabs);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Starts the timer in the Timer tab.
     * If a timer is already running, it cancels the previous timer before starting a new one.
     * The timer counts up from 00:00:00 in "HH:MM:SS" format, updating the timerLabel every second.
     */
    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timerCount = 0;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timerCount++;
                int hours = timerCount / 3600;
                int minutes = (timerCount % 3600) / 60;
                int seconds = timerCount % 60;
                String hoursStr = String.format("%02d", hours);
                String minutesStr = String.format("%02d", minutes);
                String secondsStr = String.format("%02d", seconds);
                String timeStr = hoursStr + ":" + minutesStr + ":" + secondsStr;
                timerLabel.setText(timeStr);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, TIMER_DELAY);
    }

    /**
     * Stops the timer in the Timer tab.
     * If a timer is currently running, it cancels the timer and resets the timerCount and timerLabel to 00:00:00.
     */
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timerTask.cancel();
            timer = null;
            timerTask = null;
        }
        timerCount = 0;
        timerLabel.setText("00:00:00");
    }

    /**
     * Sets the countdown timer in the Countdown tab based on the input time in "HH:MM:SS" format.
     * If the input is invalid or the total time is less than or equal to 0, it displays an error message using a JOptionPane.
     * If a countdown timer is already running, it cancels the previous timer before setting the new one.
     * The countdown timer counts down from the input time in "HH:MM:SS" format, updating the countdownLabel every second.
     * When the countdown timer reaches 00:00:00, it stops the timer and displays a message using a JOptionPane.
     */
    private void setCountdown() {
        String inputTime = countdownField.getText();
        String[] parts = inputTime.split(":");
        if (parts.length != 3) {
            JOptionPane.showMessageDialog(frame, "Invalid time format. Please enter in HH:MM:SS format.");
            return;
        }
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        int totalTimeSeconds = hours * 3600 + minutes * 60 + seconds;
        if (totalTimeSeconds <= 0) {
            JOptionPane.showMessageDialog(frame, "Countdown time must be greater than 0.");
            return;
        }
        if (timer != null) {
            timer.cancel();
            timerTask.cancel();
            timer = null;
            timerTask = null;
        }
        timerCount = totalTimeSeconds;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timerCount--;
                if (timerCount < 0) {
                    stopTimer();
                    JOptionPane.showMessageDialog(frame, "Countdown complete!");
                } else {
                    int hours = timerCount / 3600;
                    int minutes = (timerCount % 3600) / 60;
                    int seconds = timerCount % 60;
                    String hoursStr = String.format("%02d", hours);
                    String minutesStr = String.format("%02d", minutes);
                    String secondsStr = String.format("%02d", seconds);
                    String timeStr = hoursStr + ":" + minutesStr + ":" + secondsStr;
                    countdownLabel.setText(timeStr);
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, TIMER_DELAY);
    }
    
}    
