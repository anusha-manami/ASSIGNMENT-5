/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FilePlayer;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Anusha
 */
public class FilePlayer {
    
    public void play(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }

    }
    private static final CountDownLatch startLatch = new CountDownLatch(1);

    private static void playNotes(String... notes) {
        try {
            startLatch.await(); // Wait for the start signal

            for (String note : notes) {
                playSingleNote(note);
                Thread.sleep(500); // Adjust the delay between notes as needed
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } 

    private static void playSingleNote(String note) {
        try {
            String filePath = "C:\\Users\\Anusha\\Documents\\NetBeansProjects\\sounds\\sounds\\src\\songs\\Sounds\\"+ note + ".wav"; // Replace with the actual file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.drain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void playTwinkleTwinkle() {
        playNotes("do", "do", "sol", "sol", "la", "la", "sol", "fa", "fa", "mi", "mi", "re", "re", "do",
                "sol", "sol", "fa", "fa", "mi", "mi", "re", "sol", "sol", "fa", "fa", "mi", "mi", "re",
                "do", "do", "sol", "sol", "la", "la", "sol", "fa", "fa", "mi", "mi", "re", "re", "do");
    }

    public static void main(String[] args) {
        // TODO code application logic here
        FilePlayer fp = new FilePlayer();
//        for (int i = 0; i < 100; i++) {
//            fp.play("src/songs/Sounds/do.wav");
//        }

        Thread thread1 = new Thread(() -> playNotes("do", "mi", "sol", "si", "do-octave"));
        Thread thread2 = new Thread(() -> playNotes("re", "fa", "la", "do-octave"));
        Thread twinkleThread = new Thread(() -> playTwinkleTwinkle());

        thread1.start();
        thread2.start();
        twinkleThread.start();

        // Start the threads simultaneously
        startLatch.countDown();

        try {
            // Wait for both threads to finish
            thread1.join();
            thread2.join();
            twinkleThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
