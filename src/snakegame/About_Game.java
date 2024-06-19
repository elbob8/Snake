package snakegame;


/**
 *
 * @author Elbob
 */

import Tools.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.imageio.*;
import javax.swing.*;
import static javax.swing.WindowConstants.*;


public class About_Game extends JFrame implements ActionListener{
    //
    JPanel home;
    JLabel title;
    MyButton button_back,button_out;
    Random random = new Random();
    Timer timer = new Timer(100, this);
    //
    public About_Game(){
        
        
        setTitle("Snake");
        try {
            setIconImage(ImageIO.read(this.getClass().getResource("/Tools/images/love.png")));
         } catch (Exception ex) {
             JOptionPane.showMessageDialog(rootPane, ex);
         }
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setSize(600, 600);
            setLayout(null);
            setLocationRelativeTo(null);
            setVisible(true);
            
            timer.start();
            
            home = new JPanel();
            home.setSize(getWidth(), getHeight());
            home.setLayout(null);
            home.setBackground(Color.decode("#000000"));
            add(home);
            
            title = new JLabel("About");
            title.setForeground(Color.GREEN);
            title.setFont(new Font("Ink Free",Font.BOLD,50));
            title.setBounds(200, 10, 200, 50);
            title.setHorizontalAlignment(JLabel.CENTER);
            home.add(title);
            

            JPanel draw = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g2.setFont(new Font("Tahoma", Font.BOLD, 20));
                g2.drawString("Developed by @SaidSabrey", 30, 30);
                g2.drawString("' The Hands of the 8 write and do the impossible '", 60, 60);
                g2.drawString("' Enjoy this game and we hope to provide ", 90, 90);
                g2.drawString(" the best in the future '", 120, 120);
                g2.drawString("' CopyRight@2024 Octopus8.com '", 150, 150);
            } 
            };
            
            draw.setBounds(10, 100, getWidth(), 300);
            home.add(draw);
            
            button_back = new MyButton("back");
            button_back.setBounds(90, 380, 200, 50);
            button_back.setToolTipText("Back to Home");
            button_back.setRadius(50);
            button_back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Start_Game();
                
            }
            });
            home.add(button_back,BorderLayout.CENTER);
            
            button_out = new MyButton("Exit");
            button_out.setBounds(300, 380, 200, 50);
            button_out.setToolTipText("Exit in Game");
            button_out.setRadius(50);
            button_out.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            });
            home.add(button_out,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}