
package snakegame;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;


/**
 *
 * @author Elbob
 */
public class Game_Panel extends JPanel implements ActionListener,KeyListener{
    //
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts;
    int res;
    int applesEaten,Save_Best_Score,bestScore,appleX,appleY;
    char direction;
    boolean running;
    Timer timer;
    Random random;
    private BufferedImage Img_down,Img_up,Img_right,Img_left,Img_apple,Img_apple2;
    boolean Right=false,Left=false,Up=false,Down=false;
    AudioInputStream audio_eat,audio_gameover;
    Clip clip_eat,clip_gameover;
    //
    public Game_Panel(){
        timer = new Timer(75, this);
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);
        this.setFocusable(true);
        importImg();
        importAudio();
        startGame();
    }
    
    public void startGame(){
        newApple();
        direction = 'R';
        bodyParts = 4;
        applesEaten = 0;
        running = true;
        timer.start();
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);
        if(running){
            //UNITS
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g2.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g2.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            //apple
            if(applesEaten != 0){
                res = applesEaten % 5;
                if(res == 0){
                    g2.drawImage(Img_apple2,appleX,appleY,UNIT_SIZE,UNIT_SIZE, null);
                }else{
                    g2.drawImage(Img_apple,appleX,appleY,UNIT_SIZE,UNIT_SIZE, null);
                }
            }else{
                    g2.drawImage(Img_apple,appleX,appleY,UNIT_SIZE,UNIT_SIZE, null);
            }
            
            for(int i = 0; i < bodyParts;i++){
                 //hade
            if(i == 0 && Right){
                g2.drawImage(Img_right,x[i],y[i], UNIT_SIZE, UNIT_SIZE, null);
            }else if(i == 0 && Left){
                g2.drawImage(Img_left,x[i],y[i], UNIT_SIZE, UNIT_SIZE, null);
            }else if(i == 0 && Up){
                g2.drawImage(Img_up,x[i],y[i], UNIT_SIZE, UNIT_SIZE, null);
            }else if(i == 0 && Down){
                g2.drawImage(Img_down,x[i],y[i], UNIT_SIZE, UNIT_SIZE, null);
            }else if(i!=0){
                //bodyParts
                g2.setColor(new Color(150,104,236));
                g2.fillOval(x[i], y[i], UNIT_SIZE-2,UNIT_SIZE-2);
                }
            }
            //Score text
            g2.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g2.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics fm = getFontMetrics(g2.getFont());
            g2.drawString("Score: " + applesEaten,(SCREEN_WIDTH - fm.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
        }else{
                        
            import_B_S();
            
            //Best Score
            g2.setColor(Color.GREEN);
            g2.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics fm = getFontMetrics(g2.getFont());
            g2.drawString("Best Score: " + bestScore,(SCREEN_WIDTH - fm.stringWidth("Best Score: " + applesEaten))/2,g.getFont().getSize()*5);
            
            //Score text
            g2.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g2.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics fm2 = getFontMetrics(g2.getFont());
            g2.drawString("Score: " + applesEaten,(SCREEN_WIDTH - fm2.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
            
            //Game Over text
            g2.setColor(Color.red);
            g2.setFont(new Font("Ink Free",Font.BOLD,75));
            FontMetrics fm3 = getFontMetrics(g2.getFont());
            g2.drawString("Game Over",(SCREEN_WIDTH - fm3.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
            
            //restart
            g2.setFont(new Font("serif",Font.BOLD,20));
            g2.drawString("Press Enter to Restart",(SCREEN_WIDTH - fm3.stringWidth("Game Over"))/1,400);
            clip_gameover.setMicrosecondPosition(0);
            clip_gameover.start();
        }
    }
    private void import_B_S() {
    try {
        File B_Score = new File("BestScore.txt");
        int Reader;
        // التحقق من وجود الملف
        if (!B_Score.exists()) {
            PrintWriter pw = new PrintWriter(B_Score);
            pw.print(0); // قيمة افتراضية لأفضل نتيجة
            pw.close();
        }
        
        FileReader f = new FileReader(B_Score);
        BufferedReader b = new BufferedReader(f);
        String B = b.readLine();
        Reader = Integer.parseInt(B);
        bestScore = Reader;
        b.close();
        
        // حفظ أفضل نتيجة فقط إذا كانت أعلى من النتيجة الحالية
        if (bestScore < applesEaten) {
            Save_Best_Score = applesEaten;
            PrintWriter pw = new PrintWriter(B_Score);
            pw.print(Save_Best_Score);
            pw.close();
            
            FileReader fr = new FileReader(B_Score);
            BufferedReader br = new BufferedReader(fr);
            String B_S = br.readLine();
            Reader = Integer.parseInt(B_S);
            bestScore = Reader;
            br.close();
        }
    } catch (Exception ex) {
    Logger.getLogger(Game_Panel.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    private void importImg(){
        
        InputStream up = getClass().getResourceAsStream("/res/up.png");
        InputStream down = getClass().getResourceAsStream("/res/down.png");
        InputStream right = getClass().getResourceAsStream("/res/right.png");
        InputStream left = getClass().getResourceAsStream("/res/left.png");
        InputStream apple = getClass().getResourceAsStream("/res/apple.png");
        InputStream apple2 = getClass().getResourceAsStream("/res/apple2.png");
        
		try {
			Img_up = ImageIO.read(up);
                        Img_down = ImageIO.read(down);
                        Img_right = ImageIO.read(right);
                        Img_left = ImageIO.read(left);
                        Img_apple = ImageIO.read(apple);
                        Img_apple2 = ImageIO.read(apple2);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
    private void importAudio(){
        try{
            InputStream eat = getClass().getResourceAsStream("/audio/eat.wav");
            audio_eat = AudioSystem.getAudioInputStream(eat);
            clip_eat = AudioSystem.getClip();
            clip_eat.open(audio_eat);
            
            InputStream gameover = getClass().getResourceAsStream("/audio/gameover.wav");
            audio_gameover = AudioSystem.getAudioInputStream(gameover);
            clip_gameover = AudioSystem.getClip();
            clip_gameover.open(audio_gameover);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    float volume = 0.7f;
    FloatControl fc = (FloatControl)clip_eat.getControl(FloatControl.Type.MASTER_GAIN);
    float range = fc.getMaximum() - fc.getMinimum();
    float gain = (range * volume) + fc.getMinimum();
    fc.setValue(gain);
    
    
    FloatControl fc2 = (FloatControl)clip_gameover.getControl(FloatControl.Type.MASTER_GAIN);
    float range2 = fc2.getMaximum() - fc2.getMinimum();
    float gain2 = (range2 * volume) + fc2.getMinimum();
    fc2.setValue(gain2);
    }
    
    
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    
    public void move(){
        
        for(int i = bodyParts; i > 0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U':
                if(y[0] < 0){
                    y[0] = SCREEN_HEIGHT - UNIT_SIZE;
                }else{
                    y[0] = y[0] - UNIT_SIZE;
                }
                break;
            case 'D':
                if(y[0] > SCREEN_HEIGHT){
                    y[0] = 0;
                }else{
                    y[0] = y[0] + UNIT_SIZE;
                }
                break;
            case 'L':
                if (x[0] == 0) {
                    x[0] = SCREEN_WIDTH - UNIT_SIZE;
                } else {
                    x[0] = x[0] - UNIT_SIZE;
                }
                break;
            case 'R':
                if (x[0] == SCREEN_WIDTH - UNIT_SIZE) {
                    x[0] = 0;
                } else {
                    x[0] = x[0] + UNIT_SIZE;
                }
                Right = true;
                break;
        }
    }
    
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            if(applesEaten != 0){
                if(res == 0){
                    applesEaten+=2;
                }else{
                    applesEaten++;
                }
            }else{
                applesEaten++;
            }
            newApple();
            clip_eat.setMicrosecondPosition(0);
            clip_eat.start();
        }
    }
    
    public void checkCollisions(){
        //check if head collides with body
        for(int i = bodyParts; i > 0 ; i--){
            if((x[0] == x[i]) && (y[0]) == y[i]){
                running = false;
                x[0] = x[0] + UNIT_SIZE;
                y[0] = y[0] + UNIT_SIZE;
                Right = true;
                
            }
        }
        
        //check if head touches left boredr
        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH - UNIT_SIZE;
        }
        //check if head touches right border
        if (x[0] > SCREEN_WIDTH - UNIT_SIZE) {
            x[0] = 0;
        }
        //check if head touches top border
        if(y[0] < 0){
            y[0] = SCREEN_HEIGHT - UNIT_SIZE;
        }
        //check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT){
            y[0] = 0;
        }
        //running = false
        
        if(!running){
            timer.stop();
        }
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                    direction = 'L';
                    Left = true;
                    if(!Right){
                        Left = true;
 
                    }else{
                        Left = false;
                        Right = true;
                    }
                    Up = false;
                    Down = false;
                         break;
            case KeyEvent.VK_RIGHT:
                    direction = 'R';
                    Right = true;
                    if(!Left){
                        Right = true;
                    }else{
                        Right = false;
                        Left = true;
                    }
                    Up = false;
                    Down = false;
                         break;
            case KeyEvent.VK_UP:
                    direction = 'U';
                    Up = true;
                    if(!Down){
                        Up = true;
                    }else{
                        Up = false;
                        Down = true;
                    }
                    Left = false;
                    Right = false;
                         break;
            case KeyEvent.VK_DOWN:
                    direction = 'D';
                    Down = true;
                    if(!Up){
                        Down = true;
                    }else{
                        Up = true;
                        Down = false;
                    }
                    Left = false;
                    Right = false;
                         break;
            case KeyEvent.VK_ENTER:
                    startGame();
                         break;
        }
            
    }

    @Override
    public void keyReleased(KeyEvent e){}
    
    @Override
    public void keyTyped(KeyEvent e){}
    
}
