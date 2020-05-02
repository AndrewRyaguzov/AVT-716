package SubClasses;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Music{
    public Clip clip;
    public Music(String Path){
        try {
            File soundFile = new File(Path); 
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            
            clip = AudioSystem.getClip();
            
            clip.open(ais);
            
            clip.setFramePosition(0);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
        } 
    }
    
    public void setVolume(int Volume){
        FloatControl vc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float diff = vc.getMaximum() - vc.getMinimum();
        vc.setValue( vc.getMinimum() + (diff / 100 * Volume) );
    }
}