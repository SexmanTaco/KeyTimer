
/**
 * Stores and returns the data
 * 
 * @author Adam Libresco 
 * @version 10/23/14
 */
import java.io.PrintWriter;
import java.io.File;

public class DataManager
{
    private int[] mSoundData;
    private int[] mTimeData;
    private int mEntries = 0;
    
    public DataManager(int entries)
    {
        mSoundData = new int[entries];
        mTimeData = new int[entries];
    }
    
    public void addDataPoint(int time, int frequency)
    {
        mSoundData[mEntries] = frequency;
        mTimeData[mEntries] = time;
        mEntries ++;
    }
    
    public void saveToFile(String name)
    {
        name = "Data/" + name;
        int count = 0;
        boolean done = false;
        while (!done)
        {
            if (new File(name).isFile())
            {
                count ++;
                name += count;
            }
            else
                done = true;
        }
        
        try
        {
            PrintWriter writer = new PrintWriter(name, "UTF-8");
            writer.println("Frequency(Hz)\tTime(ms)");
            for (int i = 0; i < mEntries; i++)
            {
                writer.println(mSoundData[i] + "\t\t" + mTimeData[i]);
            }
            writer.close();
        }catch (Exception e) {
            //this will never happen
        }
    }
}
