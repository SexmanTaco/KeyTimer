
/**
 * Write a description of class KeyTimer here.
 * 
 * @author Adam Libresco 
 * @version 10/23/14
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


public class KeyTimer extends JFrame implements KeyListener
{
    JPanel p;
    JLabel label;
    static boolean startKeyPressed = false;
    static boolean startKeyRelease = false;
    static boolean endKey = false;
    static boolean soundActive = false;
    
    static final int[] frequencies = {200, 440, 13000};
    
    public static void main(String[] args)
    {
       long startTime;
       KeyTimer kt = new KeyTimer("");
       kt.label.setText("Please hold down the A key, then wait for a sound to touch the L key.");
       int timesToRun = 0;
       
       String name = JOptionPane.showInputDialog("What is your name?");
       
       boolean validInput = false;
       do
       {
           boolean validInt = false;
           while (!validInt)
           {
               try
               {
                   String strTimesToRun = JOptionPane.showInputDialog("How many tests would you like to do?");
                   if (strTimesToRun == null)
                       System.exit(0);
                   else
                       timesToRun = Integer.parseInt(strTimesToRun);
                   validInt = true;
               } catch (NumberFormatException e) {
                   JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive integer.");
               }
           }
           if (timesToRun <= 0)
               JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive integer.");
           else
               validInput = true;
       }while (!validInput);
   
       DataManager dm = new DataManager(timesToRun);
       
       int timesPressed = 0;
       while (timesPressed < timesToRun)
       {
           int frequency = frequencies[(int)(Math.random() * 3)];
           soundActive = false;
           sleepTime(1);
           if (startKeyPressed)
           {
               kt.label.setText("The key is being pressed");
               boolean release = false;
               int random = (int) (Math.random() * 3000 + 1000);
               for (int i = 0; i < random; i++)
               {
                   sleepTime(1);
                   if (!startKeyPressed)
                   {
                       i += random;
                       release = true;
                   }
               }
               if (!release)
               {
                   SoundManager sound = new SoundManager("Sound Files/" + frequency + ".wav");
                   sound.playSound();
                   soundActive = true;
                   startTime = System.currentTimeMillis();
                   //kt.label.setText("Press the L key");
                   while (!endKey)
                   {
                       sleepTime(1);
                   }
                   sound.stopSound();
                   endKey = false;
                   dm.addDataPoint((int) (System.currentTimeMillis() - startTime), frequency);
                   timesPressed ++;
               }
           }
           else
           {
               kt.label.setText("Please hold down the A key, then wait for a sound to touch the L key.");
           }
       }
       
       dm.saveToFile(name);
       kt.label.setText("The program is now done running.");
       sleepTime(2000);
       kt.setVisible(false);
    }


    public KeyTimer(String s) 
    {
        super(s);
        p = new JPanel();
        label = new JLabel();
        p.add(label);
        add(p);
        addKeyListener(this);
        setSize(500, 250);
        setVisible(true);
    }
    

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) 
    {

    }
    
    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_A)
            startKeyPressed = true;
        if (soundActive && e.getKeyCode() == KeyEvent.VK_L)
            endKey = true;
    }
    
    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_A)
            startKeyPressed = false;
    }
    
    public static void sleepTime(long n)
    {
        try{
               Thread.sleep(n);
           } catch(InterruptedException e){
               
           }
    }
}
