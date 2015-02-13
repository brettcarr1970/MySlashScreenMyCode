package GoodSplash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

/**
 *
 * @author brett
 */
public class SplashScreen extends JWindow {

    private static JProgressBar progressbar;
    private static int count = 1;
    private static int TIMER_PAUSE = 100;
    private static int PROGRAM_MAX = 100;
    private static Timer progressBarTimer;
    private static JPanel panel;
    private static SplashScreen splashScreen = new SplashScreen();

    public SplashScreen() {
        createSplash();
        
    }

    private void createSplash() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel splashImage = new JLabel(new ImageIcon(getClass().getResource("splash.png")));
        panel.add(splashImage);

        progressbar = new JProgressBar();
        progressbar.setBounds(100, WIDTH, WIDTH, 33);
        progressbar.setMaximum(PROGRAM_MAX);
        progressbar.setForeground(Color.red);
        progressbar.setStringPainted(true);
        progressbar.setBorder(BorderFactory.createLineBorder(Color.red));
        panel.add(progressbar, BorderLayout.SOUTH);

        this.setContentPane(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        startProgressBar();
    }

    private void startProgressBar() {
        progressBarTimer = new Timer(TIMER_PAUSE, new ActionListenerImpl());
        progressBarTimer.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

    }

    class ActionListenerImpl implements ActionListener {

        public ActionListenerImpl() {
        }

        public void actionPerformed(ActionEvent arg0) {
            // progressbar.setValue(count);
            if (count >= 0 && count <= 20) {
                progressbar.setValue(count);
                progressbar.setString(count + "%" + " " + "I have you now!");
            }
            if (count >= 20 && count <= 40) {
                progressbar.setValue(count);
                progressbar.setString(count + "%" + " " + "Just going through your Personal Files");
            }
            if (count >= 40 && count <= 60) {
                progressbar.setValue(count);
                progressbar.setString(count + "%" + " " + "Now Im Going To Royally Fuck You");
            }
            if (count >= 60 && count <= 80) {
                progressbar.setValue(count);
                progressbar.setString(count + "%" + " " + "I like your stuff like credit card numbers, Do you?");
            }
            if (count >= 80 && count <= 90) {
                progressbar.setValue(count);
                progressbar.setString(count + "%" + " " + "Thanks For Your Money You Gaping Anus");
            }
            if (count >= 91 && count <= 100) {
                progressbar.setValue(count);
                progressbar.setString(count + "%" + " " + "Eat A Bag Of Dicks Sunny");
            }

            if (PROGRAM_MAX == count) {
                progressBarTimer.stop();
                //System.exit(0);
                splashScreen.dispose();
                TvConvertInterface.notmain(null);
               // new TvConvertInterface(null, rootPaneCheckingEnabled).setVisible(true);
                
                
                
            }
            count++;
        }
    }

}
