
/**
 * Manages the sounds of the program
 * 
 * @author Adam Libresco 
 * @version 10/23/14
 */
import javax.sound.sampled.*;
import java.io.File;

public class SoundManager
{
    Clip clip;
    
    public SoundManager(String file)
    {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void playSound()
    {
        clip.start();
    }
    
    public void stopSound()
    {
        clip.stop();
    }
}
