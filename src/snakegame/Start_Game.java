package snakegame;


/**
 *
 * @author Elbob
 */

import Tools.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import static javax.swing.WindowConstants.*;


public class Start_Game extends JFrame{
    //
    JPanel home;
    JLabel title;
    MyButton button_start,button_out,button_about;
    private BufferedImage Img_start,Img_sound,Img_silence;
    AudioInputStream audio ;
    Clip clip;
    JToggleButton b_audio;
    //
    public Start_Game(){
        
        setTitle("Snake");
        try {
            setIconImage(ImageIO.read(this.getClass().getResource("/Tools/images/love.png")));
         } catch (Exception ex) {
             JOptionPane.showMessageDialog(rootPane, "icon app not found");
         }
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setSize(600, 600);
            setLayout(null);
            setLocationRelativeTo(null);
            setVisible(true);
            
            importImg();
            importAudio();
            
            home = new JPanel();
            home.setSize(getWidth(), getHeight());
            home.setLayout(null);
            home.setBackground(Color.decode("#000000"));
            add(home);
            
            title = new JLabel("Snake");
            title.setForeground(Color.GREEN);
            title.setFont(new Font("Ink Free",Font.BOLD,50));
            title.setBounds(200, 10, 200, 50);
            title.setHorizontalAlignment(JLabel.CENTER);
            home.add(title);
            
            ImageIcon icon_start = new ImageIcon(Img_start);
            
            button_start = new MyButton("Start");
            button_start.setIcon(icon_start);
            button_start.setBounds(200, 170, 200, 50);
            button_start.setToolTipText("Play!");
            button_start.setRadius(20);
            button_start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Snake_Game();
                clip.close();
            }
            });
            home.add(button_start,BorderLayout.CENTER);
            
            
            button_about = new MyButton("about");
            button_about.setBounds(200, 240, 200, 50);
            button_about.setToolTipText("Information From Game");
            button_about.setRadius(20);
            button_about.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new About_Game();
                clip.close();
            }
            });
            home.add(button_about,BorderLayout.CENTER);
            
            button_out = new MyButton("Exit");
            button_out.setBounds(200, 310, 200, 50);
            button_out.setToolTipText("Exit in Game");
            button_out.setRadius(20);
            button_out.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            });
            home.add(button_out,BorderLayout.CENTER);
            
            ImageIcon icon_audio = new ImageIcon(Img_sound);
            
            b_audio = new JToggleButton();
            b_audio.setIcon(icon_audio);
            b_audio.setFocusable(false);
            b_audio.setBounds(10, 500, 50, 50);
            b_audio.setToolTipText("Mute!");
            b_audio.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BooleanControl bc = (BooleanControl)clip.getControl(BooleanControl.Type.MUTE);
                
                if(b_audio.isSelected()){
                    bc.setValue(true);
                    ImageIcon icon_silnece = new ImageIcon(Img_silence);
                    b_audio.setIcon(icon_silnece);
                    
                }else{
                    bc.setValue(false);
                    ImageIcon icon_sound = new ImageIcon(Img_sound);
                    b_audio.setIcon(icon_sound);
                    
                }
                
            }
            });
            home.add(b_audio,BorderLayout.CENTER);
            
    }
    private void importImg(){
        InputStream start = getClass().getResourceAsStream("/res/down.png");
        InputStream sound = getClass().getResourceAsStream("/res/sound.png");
        InputStream silence = getClass().getResourceAsStream("/res/silence.png");
		try {
                        Img_start = ImageIO.read(start);
                        Img_sound = ImageIO.read(sound);
                        Img_silence = ImageIO.read(silence);
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
    }
    private void importAudio(){
        try{
            InputStream file = getClass().getResourceAsStream("/audio/play.wav");
            audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    float volume = 0.6f;
    FloatControl fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
    float range = fc.getMaximum() - fc.getMinimum();
    float gain = (range * volume) + fc.getMinimum();
    fc.setValue(gain);
    
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void main(String[] args) {
        new Start_Game();
    }
}
