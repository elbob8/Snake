
package snakegame;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Elbob
 */
public class Snake_Game extends JFrame{
    
        public Snake_Game(){
        
        this.add(new Game_Panel());
        this.setTitle("Snake");
        try {
            setIconImage(ImageIO.read(this.getClass().getResource("/Tools/images/love.png")));
            } catch (Exception ex) {
             JOptionPane.showMessageDialog(rootPane, "icon app not found");
            }
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
