package captureEasy.UI;
import java.awt.*;
import javax.swing.*;

public class CircularProgressTest {
  
  public static void main(String... args) {
    EventQueue.invokeLater(() -> {
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      JPanel p = new JPanel();
	   // p.add(new JCircularProgressBar().makeUI());
      f.getContentPane().add(p);
      f.setSize(320, 240);
      f.setLocationRelativeTo(null);
      f.setVisible(true);
    });
  }


}